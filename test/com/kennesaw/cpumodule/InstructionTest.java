package com.kennesaw.cpumodule;

import org.junit.Test;
import static org.junit.Assert.*;

public class InstructionTest {
    @Test
    public void arithmeticFormatTest() throws Exception {
        Instruction instruction = new Instruction();

        // R Format Test
        int instr1 = 0x05070000;
        byte trueFormat = Byte.parseByte("00",2);
        byte trueOpcode = Byte.parseByte("000101",2);
        byte trueReg1 = Byte.parseByte("0000",2);
        byte trueReg2 = Byte.parseByte("0111",2);
        byte trueDest = Byte.parseByte("0000",2);

        instruction.decodeInstruction(instr1);
        assertTrue("getFormat() should have returned " + trueFormat + ", but returned " + instruction.getFormat(),
                instruction.getFormat()==trueFormat);
        assertTrue("getOpcode() should have returned " + trueOpcode + ", but returned " + instruction.getOpcode(),
                instruction.getOpcode()==trueOpcode);
        assertTrue("getReg1() should have returned " + trueReg1 + ", but returned " + instruction.getReg1(),
                instruction.getReg1()==trueReg1);
        assertTrue("getReg2() should have returned " + trueReg2 + ", but returned " + instruction.getReg2(),
                instruction.getReg2()==trueReg2);
        assertTrue("getDestReg() should have returned " + trueDest + ", but returned " + instruction.getAddr(),
                instruction.getDestReg()==trueDest);
    }

    @Test
    public void immediateFormatTest() throws Exception {
        Instruction instruction = new Instruction();

        int instr2 = 0x5681003C;
        byte trueFormat = Byte.parseByte("01",2);
        byte trueOpcode = Byte.parseByte("010110",2);
        byte trueBreg = Byte.parseByte("1000",2);
        byte trueDreg = Byte.parseByte("0001",2);
        int trueAddr = Integer.parseInt("0000000000111100",2);

        instruction.decodeInstruction(instr2);
        assertTrue("getFormat() should have returned " + trueFormat + ", but returned " + instruction.getFormat(),
                instruction.getFormat()==trueFormat);
        assertTrue("getOpcode() should have returned " + trueOpcode + ", but returned " + instruction.getOpcode(),
                instruction.getOpcode()==trueOpcode);
        assertTrue("getReg1() should have returned " + trueBreg + ", but returned " + instruction.getReg1(),
                instruction.getbReg()==trueBreg);
        assertTrue("getReg2() should have returned " + trueDreg + ", but returned " + instruction.getReg2(),
                instruction.getDestReg()==trueDreg);
        assertTrue("getAddr() should have returned " + trueAddr + ", but returned " + instruction.getAddr(),
                instruction.getAddr()==trueAddr);
    }

    @Test
    public void jumpFormatTest() throws Exception {
        Instruction instruction = new Instruction();

        int instr2 = 0x94000852;
        byte trueFormat = Byte.parseByte("10",2);
        byte trueOpcode = Byte.parseByte("010100",2);
        int trueAddr = Integer.parseInt("000000000000100001010010",2);

        instruction.decodeInstruction(instr2);
        assertTrue("getFormat() should have returned " + trueFormat + ", but returned " + instruction.getFormat(),
                instruction.getFormat()==trueFormat);
        assertTrue("getOpcode() should have returned " + trueOpcode + ", but returned " + instruction.getOpcode(),
                instruction.getOpcode()==trueOpcode);
        assertTrue("getAddr() should have returned " + trueAddr + ", but returned " + instruction.getAddr(),
                instruction.getAddr()==trueAddr);
    }

    @Test
    public void inputOutputFormatTest() throws Exception {
        Instruction instruction = new Instruction();

        int instr2 = -1068498852;
        byte trueFormat = Byte.parseByte("11",2);
        byte trueOpcode = Byte.parseByte("000000",2);
        byte trueReg1 = Byte.parseByte("0101",2);
        byte trueReg2 = Byte.parseByte("0000",2);
        int trueAddr = Integer.parseInt("0000000001011100",2);

        System.out.println(instr2);

        instruction.decodeInstruction(instr2);
        assertTrue("getFormat() should have returned " + trueFormat + ", but returned " + instruction.getFormat(),
                instruction.getFormat()==trueFormat);
        assertTrue("getOpcode() should have returned " + trueOpcode + ", but returned " + instruction.getOpcode(),
                instruction.getOpcode()==trueOpcode);
        assertTrue("getReg1() should have returned " + trueReg1 + ", but returned " + instruction.getReg1(),
                instruction.getReg1()==trueReg1);
        assertTrue("getReg2() should have returned " + trueReg2 + ", but returned " + instruction.getReg2(),
                instruction.getReg2()==trueReg2);
        assertTrue("getAddr() should have returned " + trueAddr + ", but returned " + instruction.getAddr(),
                instruction.getAddr()==trueAddr);
    }
}