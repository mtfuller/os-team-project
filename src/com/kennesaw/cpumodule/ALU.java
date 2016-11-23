package com.kennesaw.cpumodule;

public final class ALU {
    public static int add(int r1, int r2){
        return r1 + r2;
    }
    
    public static int sub(int r1, int r2) {
        return r1 - r2;
    }
    
    public static int mult(int r1, int r2) {
        return r1 * r2;
    }
    
    public static int div(int r1, int r2) {
        return r1 / r2;
    }
    
    public static int and(int r1, int r2) {
        return r1 & r2;
    }
    
    public static int or(int r1, int r2) {
        return r1 | r2;
    }
    
    public static boolean equals(int r1, int r2) {
        return r1 == r2;
    }
    
    public static boolean lessThan(int r1, int r2) {
        return r1 < r2;
    }
    
    public static boolean greaterThan(int r1, int r2) {
        return r1 > r2;
    }
    
    public static boolean isBranchEqualTo(int bReg, int dReg) {
        return bReg == dReg;
    }
    
    public static boolean isBranchInnequalTo(int bReg, int dReg) {
        return bReg != dReg;
    }
    
    public static boolean isBranchZero(int bReg) {
        return bReg == 0;
    }
    
    public static boolean isBranchNotZero(int bReg) {
        return bReg != 0;
    }
    
    public static boolean isBranchPositive(int bReg) {
        return bReg > 0;
    }
    
    public static boolean isBranchNegative(int bReg) {
        return bReg < 0;
    }
}