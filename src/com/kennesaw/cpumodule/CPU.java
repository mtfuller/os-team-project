package com.kennesaw.cpumodule;

public class CPU {

    private State cpuState;
    private ALU alu;
    private DmaChannel dmaChannel;

    public CPU() {
    }

    public void runProcess() {
        // Get instruction from memory
        int instr = fetch();

        // Decode the instruction
        decode(instr);

        // Execute the instruction
    }

    private int fetch() {
        return 0;
    }

    private void decode(int instructionBin) {

    }

    private void executeArithmetic(byte s1, byte s2, byte dr) {

    }

    private void executeConditionBranch(byte br, byte dr, int addr) {

    }

    private void executeUnconditionalJump(int addr) {

    }

    private void executeIO(byte r1, byte r2, int addr) {

    }
}
