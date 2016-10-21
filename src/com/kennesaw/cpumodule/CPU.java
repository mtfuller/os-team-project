package com.kennesaw.cpumodule;

import com.kennesaw.OS_Module.PCB;

public class CPU extends Thread{
    private CpuState cpuState;
    private DmaChannel dmaChannel;
    private PCB currentPCB;
    private volatile boolean mutexLock;
    private boolean cacheOnly;
    private volatile boolean isRunningProcess;
    private boolean isSpinning;
    private boolean isRunning;
    
    public CPU(DmaChannel dma) {
        cpuState = new CpuState();
        dmaChannel = dma;
        cacheOnly = false;
        isRunningProcess = false;
        isRunning = true;
        mutexLock = false;
        isSpinning = false;
    }

    public boolean isLocked() {
        return mutexLock;
    }

    public void lock() {
        mutexLock = true;
    }

    public void unlock() {
        mutexLock = false;
    }
    
    @Override
    public void run() {
        while(isRunning) {
            if (isRunningProcess) {
                System.out.println("DEBUG | CPU | Running Job #"+currentPCB.getJobID());
                if (!cacheOnly) initializeCPU();
                runProcess();
                System.out.println("DEBUG | CPU | Spun CPU with Process");
                if (!cacheOnly) exportOutput();
                System.out.println("DEBUG | CPU | Exported output");
                while (isLocked());
                lock();
                isRunningProcess = false;
                unlock();
                System.out.println("DEBUG | CPU | Set isRunningProcess to false");
            }
        }
    }
    
    public boolean isRunningProcess() {
        return isRunningProcess;
    }
    
    public void endCPU() {
        isRunning = false;
    }
    
    public void runCacheOnlyCPU() {
        cacheOnly = true;
        isRunningProcess = true;
    }
    
    public synchronized void runPCB(PCB pcb) {
        System.out.println("DEBUG | CPU | Setting pcb to PCB #"+pcb.getJobID());
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
        for (int i = 0; i < size; i++) {
            dmaChannel.writeCache(i*4, dmaChannel.readRAM((addr+i)*4));
        }
    }
    
    public void runProcess() {
        isSpinning = true;
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
                    executeConditionBranch(instruction.getOpcode(), instruction.getbReg(), instruction.getDestReg(),
                            instruction.getAddr());
                    break;
                case 2:
                    executeUnconditionalJump(instruction.getOpcode(), instruction.getAddr());
                    break;
                case 3:
                    executeIO(instruction.getOpcode(), instruction.getReg1(), instruction.getReg2(),
                            instruction.getAddr());
                    break;
            }
            if (cpuState.getBase_addr() > 72) {
                isSpinning = false;
                break;
            }
        }
    }
    
    private void exportOutput() {
//        int addr = currentPCB.getRAMAddressBegin();
//        int size = currentPCB.getJobSize();
//        for (int i = 0; i < size; i++) {
//            dmaChannel.writeRAM(i+addr, dmaChannel.readCache(i));
//        }
    }
    
    private synchronized long fetch(int addr) {
        return dmaChannel.readCache(addr*4);
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
                dmaChannel.writeCache(((int) cpuState.getReg(dr)), cpuState.getReg(br));
                break;
            case 0x03:
                cpuState.setReg(dr, dmaChannel.readCache((int) cpuState.getReg(br)));
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
        //int baseAddr = cpuState.getBase_addr() * 4;
        switch (opcode) {
            case 0x00:
                if (addr == 0) cpuState.setReg(r1, dmaChannel.readCache((int) cpuState.getReg(r2)));
                else cpuState.setReg(r1, dmaChannel.readCache(addr));
                break;
            case 0x01:
                if (r1 == r2) dmaChannel.writeCache(addr, cpuState.getReg(r1));
                else dmaChannel.writeCache((int) cpuState.getReg(r2), cpuState.getReg(r1));
                break;
        }
    }
    
    @Override
    public String toString() {
        return "\nCPU:" +
                "\n\n" +
                cpuState.toString();
    }
}