package com.kennesaw.cpumodule;

import java.util.Arrays;

public class DmaChannel {
    private int pseudoRAM[];

    public DmaChannel() {
        pseudoRAM = new int[1024];
    }

    public int readRAM(int addr) {
        return pseudoRAM[addr];
    }

    public void writeRAM(int addr, int val) {
        pseudoRAM[addr] = val;
    }

    @Override
    public String toString() {
        return "DmaChannel{" +
                "pseudoRAM=" + Arrays.toString(pseudoRAM) +
                '}';
    }
}
