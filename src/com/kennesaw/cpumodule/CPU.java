package com.kennesaw.cpumodule;

import com.kennesaw.analysis.Analysis;
import com.kennesaw.osmodule.PCB;
import com.kennesaw.util.DebuggableThread;

public class CPU extends DebuggableThread{
    private int cpuId;
    private CpuState cpuState;
    private Cache cache;
    private MMU mmu;
    private LogicalAddress logicalAddress;
    private PCB currentPCB;
    
    private volatile boolean isRunningProcess;
    private boolean isSpinning;
    private boolean isRunning;
    private boolean isProcessStarted;
    private boolean isProcessComplete;
    private boolean cpuIsInterupted;
    
    private final boolean CACHE_ONLY = false;
    
    
    public CPU(int id, MMU mem, boolean isDebug) {
        cpuId = id;
        cpuState = new CpuState();
        cache = new Cache();
        mmu = mem;
        logicalAddress = new LogicalAddress();
        isRunningProcess = false;
        isRunning = true;
        isSpinning = false;
        isProcessStarted = false;
        isProcessComplete = false;
        cpuIsInterupted = false;
        this.setName("CPU "+cpuId);
        this.setModuleName(this.getName());
        this.setDebugMode(isDebug);
    }
    
    @Override
    public void run() {
        while(isRunning) {
            if (isRunningProcess) {
                synchronized (this) {
//                    logMessage("Running Job #" + currentPCB.getJobID());
                    if (!CACHE_ONLY) initializeCPU();
                    runProcess();
                    isRunningProcess = false;
                }
            }
        }
//        logMessage("CPU is finished!!!");
    }
    
    public synchronized boolean isRunningProcess() {
        return isRunningProcess;
    }
    
    public synchronized void endCPU() {
        isRunning = false;
    }
    
    public synchronized void runPCB(PCB pcb) {
//        logMessage("Setting pcb to PCB #"+pcb.getJobID());
        while (isSpinning);
        currentPCB = pcb;
        synchronized (currentPCB) {
            cpuState = currentPCB.getState();
            cache = cpuState.getCache();
            isRunningProcess = true;
        }
    }
    
    private void initializeCPU() {
        synchronized (currentPCB) {
            CpuState pcbState = currentPCB.getState();
            cpuState.setPc(pcbState.getPc());
            cpuState.setBase_addr(0);
            for (byte i = 0; i < 15; i++) {
                cpuState.setReg(i, pcbState.getReg(i));
            }
            int addr = currentPCB.getRAMAddressBegin();
            int size = currentPCB.getJobSize();
            double used = (double) size / cache.getCacheSize();
            Analysis.recordCPU((currentPCB.getJobID() - 1), cpuId, used);
        }
    }
    
    private void runProcess() {
        // Initial Settings
        isSpinning = true;
        int ioInstructs = 0;
        int jobId = currentPCB.getJobID() - 1;
        
        // Main Loop
        while(isSpinning) {
            // Get instruction from com.kennesaw.memory
            long instr = fetch(cpuState.getPc());

            // Increment the PC
            cpuState.incrementPc();

            // Decode the instruction
            decode(instr);
            Instruction instruction = cpuState.getInstruction();

            // Execute the instruction
            synchronized (mmu) {
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
            }

            if (cpuIsInterupted) {
                currentPCB.setStatus(PCB.WAITING_STATE);
                cpuState.decrementPc();
                currentPCB = null;
                cpuState = null;
                cache = null;
                cpuIsInterupted = false;
//                logMessage("CPU is Interrupted...");
                return;
            }
        }
//        logMessage("Finished Process #"+(currentPCB.getJobID()));
//        Analysis.recordCompleteTime(currentPCB.getJobID()-1);
//        Analysis.recordIO(jobId, ioInstructs);
    }
    
    private void exportOutput() {
//        logMessage("Exporting output for Job #"+currentPCB.getJobID());
        mmu.writeCacheToRAM(currentPCB);
    }
    
    private long fetch(int addr) {
        logicalAddress.convertFromRawAddress(addr);
        handleInterupt(logicalAddress);
        return mmu.readCache(logicalAddress, cache);
    }
    
    private void decode(long instructionBin) {
        if (cpuIsInterupted) return;
        cpuState.setInstruction(instructionBin);
    }
    
    private void executeArithmetic(byte opcode, byte s1, byte s2, byte dr) {
        int acc;
        if (cpuIsInterupted) return;
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
    
    private void executeConditionBranch(byte opcode, byte br, byte dr, int addr) {
        int acc, wordAddr;
        if (cpuIsInterupted) return;
        switch (opcode) {
            case 0x02:
                wordAddr = (int) cpuState.getReg(dr) / 4;
                logicalAddress.convertFromRawAddress(wordAddr);
                handleInterupt(logicalAddress);
                if (cpuIsInterupted) return;
                mmu.writeCache(logicalAddress, cpuState.getReg(br), cache);
                break;
            case 0x03:
                wordAddr = (int) cpuState.getReg(br) / 4;
                logicalAddress.convertFromRawAddress(wordAddr);
                handleInterupt(logicalAddress);
                if (cpuIsInterupted) return;
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
        if (cpuIsInterupted) return;
        switch (opcode) {
            case 0x12:
                isSpinning = false;
                exportOutput();
                currentPCB.setStatus(PCB.ENDED_STATE);
                break;
            case 0x14:
                cpuState.setPc(addr);
                break;
        }
    }
    
    private void executeIO(byte opcode, byte r1, byte r2, int addr) {
        int wordAddr;
        if (cpuIsInterupted) return;
        switch (opcode) {
            case 0x00:
                if (addr == 0) wordAddr = (int) cpuState.getReg(r2) / 4;
                else wordAddr = addr / 4;
                logicalAddress.convertFromRawAddress(wordAddr);
                handleInterupt(logicalAddress);
                if (cpuIsInterupted) return;
                cpuState.setReg(r1, mmu.readCache(logicalAddress,cache));
                break;
            case 0x01:
                if (r1 == r2) wordAddr = addr / 4;
                else wordAddr = (int) cpuState.getReg(r2) / 4;
                logicalAddress.convertFromRawAddress(wordAddr);
                handleInterupt(logicalAddress);
                if (cpuIsInterupted) return;
                mmu.writeCache(logicalAddress, cpuState.getReg(r1), cache);
                break;
        }
    }

    private void handleInterupt(LogicalAddress l) {
        if (mmu.checkForInterrupt(l, cache, currentPCB)) {
            isSpinning = false;
            cpuIsInterupted = true;
        }
    }
    
    @Override
    public String toString() {
        return "\nCPU:" +
                "\n\n" +
                cpuState.toString();
    }
}