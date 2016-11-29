package com.kennesaw.cpumodule;

import com.kennesaw.osmodule.Kernel;
import com.kennesaw.osmodule.PCB;
import com.kennesaw.osmodule.PageTable;
import com.kennesaw.memory.Ram;
import com.kennesaw.util.DebuggableModule;

/**
 * Created by Thomas on 11/13/2016.
 */
public class MMU extends DebuggableModule {
    private Kernel kernel;
    private Ram ram;

    public MMU(Kernel kern, Ram mem, boolean isDebug) {
        setDebugMode(isDebug);
        setModuleName("MMU");
        kernel = kern;
        ram = mem;
    }

    public synchronized boolean checkForInterrupt(LogicalAddress logicalAddress, Cache cache, PCB pcb) {
        boolean isInterrupt = false;
        int pageNumber = logicalAddress.getPageNumber();
        try {
            if (!cache.isPageValid(logicalAddress)) {
                if (!pcb.getPageTable().getValid(pageNumber)) {
                    // PAGE FAULT
                    kernel.addToPageFaultQueue(pcb, logicalAddress.getPageNumber());
                } else {
                    // I/O Request
                    kernel.addToioQueueQueue(pcb, logicalAddress.getPageNumber());
                }
                pcb.setStatus(PCB.WAITING_STATE);
                isInterrupt = true;
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println(cache.toString());
            e.printStackTrace();
        }
        return isInterrupt;
    }

    public synchronized long readCache(LogicalAddress logicalAddress, Cache cache) {
        return cache.readCache(logicalAddress);
    }

    public synchronized void writeCache(LogicalAddress logicalAddress, long data, Cache cache) {
        cache.writeCache(logicalAddress, data);
    }

    public synchronized void writeCacheToRAM(PCB pcb) {
        LogicalAddress logicalAddress = new LogicalAddress();
        Cache cache = pcb.getState().getCache();
        PageTable pageTable = pcb.getPageTable();
        // Go through each MODIFIED/DIRTY page of PCB's cache and load it into the corresponding FRAME NUMBER
        for (int i = 0; i < Cache.CACHE_SIZE; i++) {
            if (cache.isPageModified(i) && cache.isPageValid(i)) {
                int ramAddr = pageTable.getPage(i);
                logicalAddress.setPageNumber(i);
                ram.writeRam(ramAddr, cache.readPage(i));
            }
        }
    }
}
