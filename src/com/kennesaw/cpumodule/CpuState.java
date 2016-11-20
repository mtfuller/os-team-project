package com.kennesaw.cpumodule;

public class CpuState {
    public static final byte NUM_OF_REGISTERS = 16;
    
    private int pc;
    
    public int getBase_addr() {
        return base_Addr;
    }
    
    public void setBase_addr(int base_Addr) {
        this.base_Addr = base_Addr;
    }
    
    private int base_Addr;
    private Instruction instruction;
    private long reg[];
    
    public CpuState() {
        pc = 0;
        instruction = new Instruction();
        reg = new long[NUM_OF_REGISTERS];
        for (int i = 0; i < NUM_OF_REGISTERS; i++) reg[i] = 0;
    }
    
    public int getPc() {
        return pc;
    }
    
    public void setPc(int pc) {
        this.pc = pc;
    }
    
    public void incrementPc() {
        this.pc++;
    }
    
    public Instruction getInstruction() {
        return instruction;
    }
    
    public void setInstruction(long instructionBin) {
        instruction.decodeInstruction(instructionBin);
    }
    
    public long getReg(byte i) {
        return reg[i];
    }
    
    public void setReg(byte i, long val) {
        this.reg[i] = val;
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