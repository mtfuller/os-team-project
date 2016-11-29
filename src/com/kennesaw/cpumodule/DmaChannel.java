package com.kennesaw.cpumodule;

import com.kennesaw.osmodule.Kernel;
import com.kennesaw.osmodule.PCB;
import com.kennesaw.osmodule.PageTable;
import com.kennesaw.memory.Page;
import com.kennesaw.memory.Ram;

import java.util.ArrayList;

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

    public void endDMA() {
        isSystemRunning = false;
    }

    @Override
    public void run() {
        while (isSystemRunning) {
            // Check for any PCB in I/O request queue
            if (kernel.hasIOJobs()) {
                // Get PCB
                PCB ioPCB = kernel.getJobFromioQueueQueue();
                // Determine the Cache-Frame that needs to be brought in
                LogicalAddress logicalAddress = new LogicalAddress();
                ArrayList<Integer> cacheFrames = kernel.getPagesFromIoQueue(ioPCB);
                for (Integer logicalPage : cacheFrames) {
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
            }
        }
    }
}