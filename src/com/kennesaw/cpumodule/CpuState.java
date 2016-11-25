package com.kennesaw.cpumodule;

public class CpuState {
    public static final byte NUM_OF_REGISTERS = 16;

    private int pc;

    public int getBase_addr() {
        return base_Addr;
    }

    public synchronized void setBase_addr(int base_Addr) {
        this.base_Addr = base_Addr;
    }

    private int base_Addr;
    private Instruction instruction;
    private long reg[];
    private Cache cache;

    public CpuState() {
        pc = 0;
        instruction = new Instruction();
        reg = new long[NUM_OF_REGISTERS];
        for (int i = 0; i < NUM_OF_REGISTERS; i++) reg[i] = 0;
        cache = new Cache();
    }

    public synchronized int getPc() {
        return pc;
    }

    public synchronized void setPc(int pc) {
        this.pc = pc;
    }

    public synchronized void incrementPc() {
        this.pc++;
    }

    public synchronized void decrementPc() {
        this.pc--;
    }

    public synchronized Instruction getInstruction() {
        return instruction;
    }

    public synchronized void setInstruction(long instructionBin) {
        instruction.decodeInstruction(instructionBin);
    }

    public synchronized long getReg(byte i) {
        return reg[i];
    }

    public synchronized void setReg(byte i, long val) {
        this.reg[i] = val;
    }

    public synchronized Cache getCache() {
        return cache;
    }

    @Override
    public String toString() {
        String retStr = "Current State:" +
                "\n\tPC: " + pc +
                "\n\tIR: " + instruction.toString();
        for (byte i = 0; i < NUM_OF_REGISTERS; i++) retStr = retStr.concat("\n\tR" + i + ": \t" + getReg(i));
        return retStr;
    }
}