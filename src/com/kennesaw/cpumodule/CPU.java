package com.kennesaw.cpumodule;

import com.kennesaw.Analyzer.Analysis;
import com.kennesaw.OS_Module.PCB;
import memory.Page;
import sun.rmi.runtime.Log;

public class CPU extends Thread{
    private int cpuId;
    private CpuState cpuState;
    private Cache cache;
    private MMU mmu;
    private LogicalAddress logicalAddress;
    private PCB currentPCB;
    
    private volatile boolean isRunningProcess;
    private boolean isSpinning;
    private boolean isRunning;
    
    private final boolean CACHE_ONLY = false;
    private final boolean DEBUG_MODE = true;
    
    
    public CPU(int id, MMU mem) {
        cpuId = id;
        cpuState = new CpuState();
        cache = new Cache();
        mmu = mem;
        logicalAddress = new LogicalAddress();
        isRunningProcess = false;
        isRunning = true;
        isSpinning = false;
    }
    
    @Override
    public void run() {
        while(isRunning) {
            if (isRunningProcess) {
                logMessage("Running Job #"+currentPCB.getJobID());
                if (!CACHE_ONLY) initializeCPU();
                runProcess();
                if (!CACHE_ONLY) exportOutput();
                logMessage("Exported output");
                isRunningProcess = false;
                logMessage("Set isRunningProcess to false");
            }
        }
    }
    
    public boolean isRunningProcess() {
        return isRunningProcess;
    }
    
    public void endCPU() {
        isRunning = false;
    }
    
    public synchronized void runPCB(PCB pcb) {
        logMessage("Setting pcb to PCB #"+pcb.getJobID());
        while (isSpinning);
        currentPCB = pcb;
        isRunningProcess = true;
    }
    
    private void initializeCPU() {
        CpuState pcbState = currentPCB.getState();
        cpuState.setPc(pcbState.getPc());
        cpuState.setBase_addr(0);
        for(byte i = 0; i < 15; i++) {
            cpuState.setReg(i, pcbState.getReg(i));
        }
        int addr = currentPCB.getRAMAddressBegin();
        int size = currentPCB.getJobSize();
//        for (int i = 0; i < size; i++) {
//            dmaChannel.writeCache(i*4, dmaChannel.readRAM((addr+i)*4));
//        }
        double used = (double) size / cache.getCacheSize();
        Analysis.recordCPU((currentPCB.getJobID()-1), cpuId, used);
    }
    
    private void runProcess() {
        // Initial Settings
        isSpinning = true;
        int ioInstructs = 0;
        int jobId = currentPCB.getJobID() - 1;
        
        // Main Loop
        while(isSpinning) {
            // Get instruction from memory
            long instr = fetch(cpuState.getPc());
            
            // Increment the PC
            cpuState.incrementPc();
            
            // Decode the instruction
            decode(instr);
            Instruction instruction = cpuState.getInstruction();
            
            // Execute the instruction
            switch (instruction.getFormat()) {
                case 0:
                    executeArithmetic(instruction.getOpcode(), instruction.getReg1(), instruction.getReg2(),
                            instruction.getDestReg());
                    break;
                case 1:
                    if (instruction.getOpcode() == 0x02) ioInstructs++;
                    executeConditionBranch(instruction.getOpcode(), instruction.getbReg(), instruction.getDestReg(),
                            instruction.getAddr());
                    break;
                case 2:
                    executeUnconditionalJump(instruction.getOpcode(), instruction.getAddr());
                    break;
                case 3:
                    executeIO(instruction.getOpcode(), instruction.getReg1(), instruction.getReg2(),
                            instruction.getAddr());
                    ioInstructs++;
                    break;
            }
            if (cpuState.getBase_addr() > 72) {
                isSpinning = false;
                break;
            }
        }
        logMessage("Spun CPU with Process #"+(currentPCB.getJobID()+1));
        Analysis.recordCompleteTime(currentPCB.getJobID()-1);
        Analysis.recordIO(jobId, ioInstructs);
        currentPCB.setStatus(4);
    }
    
    private synchronized void exportOutput() {
        logMessage("Exporting output for Job #"+currentPCB.getJobID());
        int addr = currentPCB.getRAMAddressBegin();
        int size = currentPCB.getJobSize();
        mmu.writeCacheToRAM(currentPCB);
    }
    
    private synchronized long fetch(int addr) {
        logicalAddress.convertFromRawAddress(addr);
        return mmu.readCache(logicalAddress, cache);
    }
    
    private void decode(long instructionBin) {
        cpuState.setInstruction(instructionBin);
    }
    
    private void executeArithmetic(byte opcode, byte s1, byte s2, byte dr) {
        int acc;
        switch (opcode) {
            case 0x04:
                cpuState.setReg(s1, cpuState.getReg(s2));
                break;
            case 0x05:
                acc = ALU.add((int) cpuState.getReg(s1), (int) cpuState.getReg(s2));
                cpuState.setReg(dr, acc);
                break;
            case 0x06:
                acc = ALU.sub((int) cpuState.getReg(s1),(int) cpuState.getReg(s2));
                cpuState.setReg(dr, acc);
                break;
            case 0x07:
                acc = ALU.mult((int) cpuState.getReg(s1),(int) cpuState.getReg(s2));
                cpuState.setReg(dr, acc);
                break;
            case 0x08:
                acc = ALU.div((int) cpuState.getReg(s1),(int) cpuState.getReg(s2));
                cpuState.setReg(dr, acc);
                break;
            case 0x09:
                acc = ALU.and((int) cpuState.getReg(s1), (int) cpuState.getReg(s2));
                cpuState.setReg(dr, acc);
                break;
            case 0x0A:
                acc = ALU.or((int) cpuState.getReg(s1), (int) cpuState.getReg(s2));
                cpuState.setReg(dr, acc);
                break;
            case 0x10:
                if (ALU.lessThan((int) cpuState.getReg(s1), (int) cpuState.getReg(s2))) cpuState.setReg(dr, 1);
                else cpuState.setReg(dr, 0);
                break;
        }
    }
    
    private synchronized void executeConditionBranch(byte opcode, byte br, byte dr, int addr) {
        int acc;
        switch (opcode) {
            case 0x02:
                logicalAddress.convertFromRawAddress((int) cpuState.getReg(br));
                mmu.writeCache(logicalAddress, cpuState.getReg(br), cache);
                break;
            case 0x03:
                logicalAddress.convertFromRawAddress((int) cpuState.getReg(br));
                long data = mmu.readCache(logicalAddress, cache);
                cpuState.setReg(dr, data);
                break;
            case 0x0B:
                cpuState.setReg(dr, addr);
                break;
            case 0x0C:
                acc = ALU.add((int) cpuState.getReg(dr), addr);
                cpuState.setReg(dr, acc);
                break;
            case 0x0D:
                acc = ALU.mult((int) cpuState.getReg(dr), addr);
                cpuState.setReg(dr, acc);
                break;
            case 0x0E:
                acc = ALU.div((int) cpuState.getReg(dr), addr);
                cpuState.setReg(dr, acc);
                break;
            case 0x0F:
                cpuState.setReg(dr, addr);
                break;
            case 0x11:
                if (ALU.lessThan((int) cpuState.getReg(br), addr)) cpuState.setReg(dr, 1);
                else cpuState.setReg(dr, 0);
                break;
            case 0x15:
                if (ALU.isBranchEqualTo((int) cpuState.getReg(br), (int) cpuState.getReg(dr))) cpuState.setPc(addr/4);
                break;
            case 0x16:
                if (ALU.isBranchInnequalTo((int) cpuState.getReg(br), (int) cpuState.getReg(dr))) cpuState.setPc(addr/4);
                break;
            case 0x17:
                if (ALU.isBranchNotZero((int) cpuState.getReg(br))) cpuState.setPc(addr/4);
                break;
            case 0x18:
                break;
            case 0x19:
                break;
            case 0x1A:
                break;
        }
    }
    
    private void executeUnconditionalJump(byte opcode, int addr) {
        switch (opcode) {
            case 0x12:
                isSpinning = false;
                break;
            case 0x14:
                cpuState.setPc(addr);
                break;
        }
    }
    
    private synchronized void executeIO(byte opcode, byte r1, byte r2, int addr) {
        switch (opcode) {
            case 0x00:
                if (addr == 0) logicalAddress.convertFromRawAddress((int) cpuState.getReg(r2));
                else logicalAddress.convertFromRawAddress(addr);
                cpuState.setReg(r1, mmu.readCache(logicalAddress,cache));
                break;
            case 0x01:
                if (r1 == r2) logicalAddress.convertFromRawAddress(addr);
                else logicalAddress.convertFromRawAddress((int) cpuState.getReg(r2));
                mmu.writeCache(logicalAddress, cpuState.getReg(r1), cache);
                break;
        }
    }
    
    @Override
    public String toString() {
        return "\nCPU:" +
                "\n\n" +
                cpuState.toString();
    }
    
    private void logMessage(String message) {
        if (DEBUG_MODE) System.out.println("DEBUG | CPU "+cpuId+" | "+message);
    }
}