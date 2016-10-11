package com.kennesaw.cpumodule;

import com.kennesaw.OS_Module.PCB;
import com.kennesaw.cpumodule.*;
import memory.Ram;

public class CPU extends Thread{
    
    private CpuState cpuState;
    private DmaChannel dmaChannel;
    private boolean isRunningProcess;
    private boolean isRunning;
    
    public CPU(Ram ram) {
        cpuState = new CpuState();
        isRunningProcess = false;
        isRunning = true;
        dmaChannel = new DmaChannel(ram);
        dmaChannel.start();
        try {
            dmaChannel.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while(isRunning) {
            if(isRunningProcess) {
                runProcess();
            }
        }
    }

    public void endCPU() {
        isRunning = false;
    }
    
    public void runPCB(PCB pcb) {
        initializeCPU(pcb);
        isRunningProcess = true;
    }
    
    private void initializeCPU(PCB pcb) {
        CpuState pcbState = pcb.getState();
        cpuState.setPc(pcbState.getPc());
        cpuState.setBase_addr(pcbState.getBase_addr());
        for(byte i = 0; i < 15; i++) {
            cpuState.setReg(i, pcbState.getReg(i));
        }
    }
    
    public void runProcess() {
        isRunningProcess = true;
        while(isRunningProcess) {
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
        }
    }
    
    private long fetch(int addr) {
        addr = addr + cpuState.getBase_addr();
        addr *= 4;
        dmaChannel.notify();
        dmaChannel.signalDMA('r',addr);
        long retLong = dmaChannel.getValue();
        try {
            dmaChannel.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return retLong;
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
                //cpuState.setReg(State.ACCUMULATOR_REG, acc);
                cpuState.setReg(dr, acc);
                break;
            case 0x06:
                acc = ALU.sub((int) cpuState.getReg(s1),(int) cpuState.getReg(s2));
                //cpuState.setReg(State.ACCUMULATOR_REG, acc);
                cpuState.setReg(dr, acc);
                break;
            case 0x07:
                acc = ALU.mult((int) cpuState.getReg(s1),(int) cpuState.getReg(s2));
                //cpuState.setReg(State.ACCUMULATOR_REG, acc);
                cpuState.setReg(dr, acc);
                break;
            case 0x08:
                acc = ALU.div((int) cpuState.getReg(s1),(int) cpuState.getReg(s2));
                //cpuState.setReg(State.ACCUMULATOR_REG, acc);
                cpuState.setReg(dr, acc);
                break;
            case 0x09:
                acc = ALU.and((int) cpuState.getReg(s1), (int) cpuState.getReg(s2));
                //cpuState.setReg(State.ACCUMULATOR_REG, acc);
                cpuState.setReg(dr, acc);
                break;
            case 0x0A:
                acc = ALU.or((int) cpuState.getReg(s1), (int) cpuState.getReg(s2));
                //cpuState.setReg(State.ACCUMULATOR_REG, acc);
                cpuState.setReg(dr, acc);
                break;
            case 0x10:
                if (ALU.lessThan((int) cpuState.getReg(s1), (int) cpuState.getReg(s2))) cpuState.setReg(dr, 1);
                else cpuState.setReg(dr, 0);
                break;
        }
    }
    
    private void executeConditionBranch(byte opcode, byte br, byte dr, int addr) {
        int acc;
        int baseAddr = cpuState.getBase_addr() * 4;
        dmaChannel.notify();
        switch (opcode) {
            case 0x02:
                dmaChannel.signalDMA('w',((int) cpuState.getReg(dr) + baseAddr),(cpuState.getReg(br)));
                break;
            case 0x03:
                dmaChannel.signalDMA('r',((int) cpuState.getReg(br) + baseAddr));
                long val = dmaChannel.getValue();
                cpuState.setReg(dr, val);
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
        try {
            dmaChannel.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    private void executeUnconditionalJump(byte opcode, int addr) {
        switch (opcode) {
            case 0x12:
                isRunningProcess = false;
                break;
            case 0x14:
                cpuState.setPc(addr);
                break;
        }
    }
    
    private void executeIO(byte opcode, byte r1, byte r2, int addr) {
        int baseAddr = cpuState.getBase_addr() * 4;
        dmaChannel.notify();
        switch (opcode) {
            case 0x00:
                long val;
                if (addr == 0) {
                    dmaChannel.signalDMA('r',((int) cpuState.getReg(r2) + baseAddr));
                    val = dmaChannel.getValue();
                    cpuState.setReg(r1, val);
                } else {
                    dmaChannel.signalDMA('r',(addr + baseAddr));
                    val = dmaChannel.getValue();
                    cpuState.setReg(r1, val);
                }
                break;
            case 0x01:
                if (r1 == r2) {
                    dmaChannel.signalDMA('w',(addr + baseAddr),(cpuState.getReg(r1)));
                }
                else {
                    dmaChannel.signalDMA('w',((int) cpuState.getReg(r2) + baseAddr),(cpuState.getReg(r1)));
                }
                break;
        }
        try {
            dmaChannel.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public String toString() {
        return "\nCPU:" +
                "\n\n" +
                cpuState.toString();
    }
}