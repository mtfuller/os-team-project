package com.kennesaw.cpumodule;

import com.kennesaw.OS_Module.Kernel;
import com.kennesaw.OS_Module.MemoryMapping;
import com.kennesaw.OS_Module.PCB;
import com.kennesaw.OS_Module.PageTable;
import memory.Page;
import memory.Ram;

public class DmaChannel extends Thread{
    private boolean isSystemRunning;
    private Ram memory;
    private Kernel kernel;
    
    public DmaChannel(Ram mem, Kernel kern) {
        memory = mem;
        kernel = kern;
        isSystemRunning = true;
        this.setName("DMA");
    }

    @Override
    public void run() {
        while (isSystemRunning) {
            // Check for any PCB in I/O request queue
            synchronized (kernel) {
                if (kernel.hasIOJobs()) {
                    // Get PCB
                    MemoryMapping memoryMapping = kernel.getJobFromioQueueQueue();
                    PCB ioPCB = memoryMapping.getPcbReference();
                    synchronized (ioPCB) {
                        // Determine the Cache-Frame that needs to be brought in
                        LogicalAddress logicalAddress = new LogicalAddress();
                        int logicalPage = memoryMapping.getDestIndex();
                        logicalAddress.setPageNumber(logicalPage);

                        // Look through the PCB's page table to find out where the Page is in RAM
                        PageTable pageTable = ioPCB.getPageTable();
                        int ramAddr = pageTable.getPage(logicalPage);

                        // Get the page from RAM and load it into the PCB's cache.
                        Cache pcbCache = ioPCB.getState().getCache();
                        for (byte b = 0; b < Page.PAGE_SIZE; b++) {
                            long data = memory.readRam(ramAddr, b);
                            logicalAddress.setPageOffset(b);
                            pcbCache.writeCache(logicalAddress, data);
                        }

                        // Set both the Cache's validBitTable to true and dirtyBitTable to false for this Page
                        pcbCache.setValidPage(logicalPage, true);
                        pcbCache.setDirtyPage(logicalPage, false);
                    }

                    // Restart the instruction by modifying the CpuState's PC, set PCB to ready, and remove io request
                    //ioPCB.getState().decrementPc();
                    memoryMapping.getPcbReference().setStatus(PCB.READY_STATE);
                    kernel.removeFromioQueueQueue(memoryMapping);
                }
            }
        }
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