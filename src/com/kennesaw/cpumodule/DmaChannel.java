package com.kennesaw.cpumodule;

import com.kennesaw.OS_Module.Kernel;
import com.kennesaw.OS_Module.PCB;
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
        // Check for any PCB in I/O request queue
        /*
        while (true) {
            // Get PCB
            PCB ioPCB = kernel.getPCB(0);

            // Determine the Cache-Frame that needs to be brought in
            LogicalAddress logicalAddress = new LogicalAddress();
            CpuState tempState = ioPCB.getState();
            logicalAddress.convertFromRawAddress(tempState.getPc());
            int logicalPage = logicalAddress.getPageNumber();

            // Look throught the PCB's page table to find out where the Page is in RAM

            // Get the page and load it into the PCB's cache.

            // Set both the Cache's validBitTable and dirtyBitTable to false for this Page

            // Restart the instruction by modifying the CpuState's PC

        }*/
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