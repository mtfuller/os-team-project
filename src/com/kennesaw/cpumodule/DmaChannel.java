package com.kennesaw.cpumodule;

import memory.Ram;

import java.util.Arrays;

public class DmaChannel {
    private long pseudoRAM[];
    private Ram memory;

    public DmaChannel(Ram mem) {
        memory = mem;
    }

    private int effectiveAddr(int addr) {
        return addr / 4;
    }

    public long readRAM(int addr) {
        addr = effectiveAddr(addr);
        return memory.readRam(addr);
    }

    public void writeRAM(int addr, long val) {
        addr = effectiveAddr(addr);
        memory.writeRam(addr, val);
    }

    @Override
    public String toString() {
        return "DmaChannel{" +
                "pseudoRAM=" + Arrays.toString(pseudoRAM) +
                '}';
    }
}
