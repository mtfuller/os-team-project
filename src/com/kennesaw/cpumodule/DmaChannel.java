package com.kennesaw.cpumodule;

import java.util.Arrays;

public class DmaChannel {
    private long pseudoRAM[];

    public DmaChannel() {
        pseudoRAM = new long[1024];
    }

    private int effectiveAddr(int addr) {
        return addr / 4;
    }

    public long readRAM(int addr) {
        addr = effectiveAddr(addr);
        return pseudoRAM[addr];
    }

    public void writeRAM(int addr, long val) {
        addr = effectiveAddr(addr);
        pseudoRAM[addr] = val;
    }

    @Override
    public String toString() {
        return "DmaChannel{" +
                "pseudoRAM=" + Arrays.toString(pseudoRAM) +
                '}';
    }
}
