package com.kennesaw.cpumodule;

import com.kennesaw.OS_Module.Kernel;
import com.kennesaw.OS_Module.PCB;
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
        if (!cache.isPageValid(logicalAddress)) {
            // IF PAGE IS NOT IN CACHE
            if (false) {
                // IF PAGE IS NOT IN MEMORY

                // PAGE FAULT
                // ADD PROCESS TO KERNEL'S PAGE FAULT QUEUE
            } else {
                // I/O Request
                // ADD PROCESS TO KERNEL'S I/O QUEUE
            }
        }
        return isInterrupt;
    }

    public long readCache(LogicalAddress logicalAddress, Cache cache) {
        if (!cache.isPageValid(logicalAddress)) {
            // IF PAGE IS NOT IN CACHE
        }
        return cache.readCache(logicalAddress);
    }

    public void writeCache(LogicalAddress logicalAddress, long data, Cache cache) {
        // WRITE LOGIC
        cache.writeCache(logicalAddress, data);
    }

    public void writeCacheToRAM(PCB pcb) {
        // Go through each MODIFIED/DIRTY page of PCB's cache and load it into the corresponding FRAME NUMBER
    }
}
