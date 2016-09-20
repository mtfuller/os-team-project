package com.kennesaw.cpumodule;

import java.util.Arrays;

public class State {
    public static final byte NUM_OF_REGISTERS = 16;

    private int pc;
    private Instruction instruction;
    private long reg[];

    public State() {
        pc = 0;
        instruction = new Instruction();
        reg = new long[NUM_OF_REGISTERS];
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
        for (byte i = 0; i < NUM_OF_REGISTERS; i++) retStr.concat("\n\tR" + i + ": " + getReg(i));
        return retStr;
    }

}
