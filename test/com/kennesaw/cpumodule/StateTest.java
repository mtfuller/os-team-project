package com.kennesaw.cpumodule;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Thomas on 9/20/2016.
 */
public class StateTest {
    @Test
    public void pcTest() {
        State state = new State();
        assertTrue("A new State's PC did not equal 0", state.getPc() == 0);

        int value = 23492;
        state.setPc(value);
        assertTrue("Could not set PC to "+value, state.getPc() == value);
    }

    @Test
    public void instructionTest() {
        State state = new State();
        Instruction ir = state.getInstruction();
        boolean isCorrect = (
                    ir.getAddr() == 0 &&
                    ir.getFormat() == 0 &&
                    ir.getbReg() == 0 &&
                    ir.getDestReg() == 0 &&
                    ir.getOpcode() == 0 &&
                    ir.getReg1() == 0 &&
                    ir.getReg2() == 0
            );
        assertTrue("A new State's IR was not initialized to 0", isCorrect);


        long instructionDec = 1326252272;
        state.setInstruction(instructionDec);
        System.out.println(state.toString());
        isCorrect = (
                ir.getAddr() == 240 &&
                ir.getFormat() == 1 &&
                ir.getbReg() == 0 &&
                ir.getDestReg() == 13 &&
                ir.getOpcode() == 15 &&
                ir.getReg1() == 0 &&
                ir.getReg2() == 0
        );
        assertTrue("The State's IR did not decode correctly.", isCorrect);
    }

    @Test
    public void registerTest() {
        State state = new State();
        byte index = 3;
        assertTrue("A new State's REG"+index+" did not equal 0", state.getReg(index) == 0);

        int value = 1432532;
        index = 5;
        state.setReg(index, value);
        assertTrue("Could not set REG"+index+" to "+value, state.getReg(index) == value);
    }
}