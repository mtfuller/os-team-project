package com.kennesaw.cpumodule;

public class CPU {

    private State cpuState;
    private DmaChannel dmaChannel;

    public CPU() {
        cpuState = new State();
        dmaChannel = new DmaChannel();
    }

    public CPU(State state, DmaChannel dc) {
        cpuState = state;
        dmaChannel = dc;
    }

    public void runProcess() {
        while(cpuState.getPc() < 100) {
            // Get instruction from memory
            int instr = fetch(cpuState.getPc());

            // Decode the instruction
            decode(instr);
            Instruction instruction = cpuState.getInstruction();

            System.out.println(instruction.toString());
        /*
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
        }*/

            // Increment the PC
            cpuState.incrementPc();
        }
    }

    private int fetch(int addr) {
        return dmaChannel.readRAM(addr);
    }

    private void decode(int instructionBin) {
        cpuState.setInstruction(instructionBin);
    }

    private void executeArithmetic(byte opcode, byte s1, byte s2, byte dr) {
        int acc;
        switch (opcode) {
            case 0x04:
                cpuState.setReg(dr, cpuState.getReg(s1));
                break;
            case 0x05:
                acc = ALU.add(cpuState.getReg(s1),cpuState.getReg(s2));
                cpuState.setReg(State.ACCUMULATOR_REG, acc);
                cpuState.setReg(dr, acc);
                break;
            case 0x06:
                acc = ALU.sub(cpuState.getReg(s1),cpuState.getReg(s2));
                cpuState.setReg(State.ACCUMULATOR_REG, acc);
                cpuState.setReg(dr, acc);
                break;
            case 0x07:
                acc = ALU.mult(cpuState.getReg(s1),cpuState.getReg(s2));
                cpuState.setReg(State.ACCUMULATOR_REG, acc);
                cpuState.setReg(dr, acc);
                break;
            case 0x08:
                acc = ALU.div(cpuState.getReg(s1),cpuState.getReg(s2));
                cpuState.setReg(State.ACCUMULATOR_REG, acc);
                cpuState.setReg(dr, acc);
                break;
            case 0x09:
                acc = ALU.and(cpuState.getReg(s1),cpuState.getReg(s2));
                cpuState.setReg(State.ACCUMULATOR_REG, acc);
                cpuState.setReg(dr, acc);
                break;
            case 0x0A:
                acc = ALU.or(cpuState.getReg(s1),cpuState.getReg(s2));
                cpuState.setReg(State.ACCUMULATOR_REG, acc);
                cpuState.setReg(dr, acc);
                break;
            case 0x10:
                // ???
                break;
        }
    }

    private void executeConditionBranch(byte opcode, byte br, byte dr, int addr) {

    }

    private void executeUnconditionalJump(byte opcode, int addr) {

    }

    private void executeIO(byte opcode, byte r1, byte r2, int addr) {

    }
}
