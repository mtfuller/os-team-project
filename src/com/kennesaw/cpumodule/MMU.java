package com.kennesaw.cpumodule;

import com.kennesaw.OS_Module.Kernel;
import com.kennesaw.OS_Module.PCB;
import com.kennesaw.OS_Module.PageTable;
import memory.Page;
import memory.Ram;

/**
 * Created by Thomas on 11/13/2016.
 */
public class MMU {
    private Kernel kernel;
    private Ram ram;

    public MMU(Kernel kern, Ram mem) {
        kernel = kern;
        ram = mem;
    }

    public boolean checkForInterrupt(LogicalAddress logicalAddress, Cache cache, PCB pcb) {
        boolean isInterrupt = false;
        int pageNumber = logicalAddress.getPageNumber();
        if (!cache.isPageValid(logicalAddress)) {
            // IF PAGE IS NOT IN CACHE
            if (pcb.getPageTable().getValid(pageNumber)) {
                // IF PAGE IS NOT IN MEMORY

                // PAGE FAULT
                // ADD PROCESS TO KERNEL'S PAGE FAULT QUEUE
            } else {
                // I/O Request

                // ADD PROCESS TO KERNEL'S I/O QUEUE
                kernel.addToioQueueQueue(pcb);
            }
            pcb.setStatus(PCB.WAITING_STATE);
            isInterrupt = true;
        }
        return isInterrupt;
    }
    public long readCache(LogicalAddress logicalAddress, Cache cache) {
        return cache.readCache(logicalAddress);
    }

    public void writeCache(LogicalAddress logicalAddress, long data, Cache cache) {
        cache.writeCache(logicalAddress, data);
    }
    public void writeCacheToRAM(PCB pcb) {
        LogicalAddress logicalAddress = new LogicalAddress();
        Cache cache = pcb.getState().getCache();
        PageTable pageTable = pcb.getPageTable();
        // Go through each MODIFIED/DIRTY page of PCB's cache and load it into the corresponding FRAME NUMBER
        for (int i = 0; i < Cache.CACHE_SIZE; i++) {
            if (cache.isPageModified(i) && cache.isPageValid(i)) {
                int ramAddr = pageTable.getPage(i);
                logicalAddress.setPageNumber(i);
                for (byte b = 0; b < Page.PAGE_SIZE; b++) {
                    logicalAddress.setPageOffset(b);
                    ram.writeRam(ramAddr, b, readCache(logicalAddress, cache));
                }
            }
        }
    }
}
