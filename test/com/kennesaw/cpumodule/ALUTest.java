package com.kennesaw.cpumodule;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Thomas on 9/20/2016.
 */
public class ALUTest {
    @Test
    public void arithmeticTest() throws Exception {
        int r1 = 152;
        int r2 = 2932;
        int dCorrect = 3084;
        int d = ALU.add(r1, r2);
        assertTrue("add() should return "+dCorrect+", but instead returned"+d, d == dCorrect);

        r1 = 28492;
        r2 = 3192;
        dCorrect = 25300;
        d = ALU.sub(r1, r2);
        assertTrue("sub() should return "+dCorrect+", but instead returned"+d, d == dCorrect);

        r1 = 243;
        r2 = 21029;
        dCorrect = 5110047;
        d = ALU.mult(r1, r2);
        assertTrue("mult() should return "+dCorrect+", but instead returned"+d, d == dCorrect);

        r1 = 262433;
        r2 = 23432;
        dCorrect = 11;
        d = ALU.div(r1, r2);
        assertTrue("div() should return "+dCorrect+", but instead returned"+d, d == dCorrect);
    }

    @Test
    public void logicTest() throws Exception {
        int r1 = 21981;
        int r2 = 2931;
        int dCorrect = 337;
        int d = ALU.and(r1, r2);
        assertTrue("and() should return "+dCorrect+", but instead returned"+d, d == dCorrect);

        r1 = 32903;
        r2 = 253;
        dCorrect = 33023;
        d = ALU.or(r1, r2);
        assertTrue("or() should return "+dCorrect+", but instead returned"+d, d == dCorrect);
    }

    @Test
    public void branchConditionsTest() throws Exception {
        int bReg = 0;
        int dReg = 0;
        boolean isCorrect = ALU.isBranchEqualTo(bReg, dReg);
        assertTrue("isBranchEqualTo() should not have returned "+isCorrect, isCorrect);

        bReg = 0;
        dReg = 1;
        isCorrect = ALU.isBranchInnequalTo(bReg, dReg);
        assertTrue("isBranchInnequalTo() should not have returned "+isCorrect, isCorrect);

        isCorrect = ALU.isBranchZero(bReg);
        assertTrue("isBranchZero() should not have returned "+isCorrect, isCorrect);

        bReg = -123;
        isCorrect = ALU.isBranchNegative(bReg);
        assertTrue("isBranchNegative() should not have returned "+isCorrect, isCorrect);

        isCorrect = ALU.isBranchNotZero(bReg);
        assertTrue("isBranchNotZero() should not have returned "+isCorrect, isCorrect);

        bReg = 123;
        isCorrect = ALU.isBranchPositive(bReg);
        assertTrue("isBranchPositive() should not have returned "+isCorrect, isCorrect);


    }
}