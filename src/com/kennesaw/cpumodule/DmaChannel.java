package com.kennesaw.cpumodule;

import com.kennesaw.OS_Module.Kernel;
import memory.Ram;

public class DmaChannel extends Thread{
    private Ram memory;
    private Kernel kernel;
    
    public DmaChannel(Ram mem, Kernel kern) {
        memory = mem;
        kernel = kern;
    }

    @Override
    public void run() {
        System.out.println("RUN DMA CHANNEL!!!");
    }

    //    private int effectiveAddr(int addr) {
//        return addr / 4;
//    }
//
//    public long readCache(int addr) {
//        addr = effectiveAddr(addr);
//        return cache[addr];
//    }
//
//    public void writeCache(int addr, long instr) {
//        addr = effectiveAddr(addr);
//        cache[addr] = instr;
//    }
//
//    public synchronized long readRAM(int addr) {
//        addr = effectiveAddr(addr);
//        long val = memory.readRam(addr);
//        return val;
//    }
//
//    public synchronized void writeRAM(int addr, long val) {
//        addr = effectiveAddr(addr);
//        memory.writeRam(addr, val);
//    }
}