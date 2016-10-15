package com.kennesaw.cpumodule;

import memory.Ram;

import java.util.Arrays;

public class DmaChannel {
    public static final int CACHE_SIZE = 72;
    private Ram memory;
    private long cache[];
    
    public DmaChannel(Ram mem) {
        memory = mem;
        cache = new long[CACHE_SIZE];
        for (int i = 0; i < CACHE_SIZE; i++) cache[i] = 0L;
    }
    
    private int effectiveAddr(int addr) {
        return addr / 4;
    }
    
    public long readCache(int addr) {
        addr = effectiveAddr(addr);
        return cache[addr];
    }
    
    public void writeCache(int addr, long instr) {
        addr = effectiveAddr(addr);
        cache[addr] = instr;
    }
    
    public long readRAM(int addr) {
        aquireLock();
        addr = effectiveAddr(addr);
        long val = memory.readRam(addr);
        releaseLock();
        return val;
    }
    
    public void writeRAM(int addr, long val) {
        aquireLock();
        addr = effectiveAddr(addr);
        memory.writeRam(addr, val);
        releaseLock();
    }
    
    private void aquireLock() {
        while(memory.isLocked());
        memory.lock();
    }
    
    private void releaseLock() {
        memory.unlock();
    }
}