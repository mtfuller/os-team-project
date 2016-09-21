package com.kennesaw.cpumodule;

public class State {
    public static final byte NUM_OF_REGISTERS = 16;

    private int pc;
    private Instruction instruction;
    private int reg[];

    public State() {
        pc = 0;
        instruction = new Instruction();
        reg = new int[NUM_OF_REGISTERS];
        for (int i = 0; i < NUM_OF_REGISTERS; i++) reg[i] = 0;
    }

    public int getPc() {
        return pc;
    }

    public void setPc(int pc) {
        this.pc = pc;
    }

    public Instruction getInstruction() {
        return instruction;
    }

    public void setInstruction(long instructionBin) {
        instruction.decodeInstruction(instructionBin);
    }

    public int getReg(byte i) {
        return reg[i];
    }

    public void setReg(byte i, int val) {
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
