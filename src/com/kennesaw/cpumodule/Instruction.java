package com.kennesaw.cpumodule;

public class Instruction {

    private byte format;
    private byte opcode;
    private byte reg1;
    private byte reg2;
    private byte bReg;
    private byte destReg;
    private int addr;

    public Instruction() {
    }

    public void decodeInstruction(long instructionBin) {
        String binStr = Long.toBinaryString(instructionBin);
        while (binStr.length() < 64) binStr = "0" + binStr;
        binStr = binStr.substring(32);
        format = Byte.parseByte(binStr.substring(0,2), 2);
        opcode = Byte.parseByte(binStr.substring(2,8), 2);
        reg1 = Byte.parseByte(binStr.substring(8,12), 2);
        reg2 = Byte.parseByte(binStr.substring(12,16), 2);
        bReg = 0;
        destReg = 0;
        addr = 0;
        switch (format) {
            case 0:
                destReg = Byte.parseByte(binStr.substring(16,20), 2);
                break;
            case 1:
                bReg = Byte.parseByte(binStr.substring(8,12), 2);
                destReg = Byte.parseByte(binStr.substring(12,16), 2);
                addr = Integer.parseInt(binStr.substring(16,32), 2);
                reg1 = 0;
                reg2 = 0;
                break;
            case 2:
                addr = Integer.parseInt(binStr.substring(8,32), 2);
                reg1 = 0;
                reg2 = 0;
                break;
            case 3:
                addr = Integer.parseInt(binStr.substring(20,32), 2);
                break;
        }
    }

    public byte getFormat() {
        return format;
    }

    public byte getOpcode() {
        return opcode;
    }

    public byte getReg1() {
        return reg1;
    }

    public byte getReg2() {
        return reg2;
    }

    public byte getbReg() {
        return bReg;
    }

    public byte getDestReg() {
        return destReg;
    }

    public int getAddr() {
        return addr;
    }

    @Override
    public String toString() {
        return "Instruction:" +
                "\n\tFormat:   \t" + getFormat() +
                "\n\tOpcode:   \t" + getOpcode() +
                "\n\tReg1:     \t" + getReg1() +
                "\n\tReg2:     \t" + getReg2() +
                "\n\tB. Reg:   \t" + getbReg() +
                "\n\tDest. Reg:\t" + getDestReg() +
                "\n\tAddress:  \t" + getAddr();
    }
}
