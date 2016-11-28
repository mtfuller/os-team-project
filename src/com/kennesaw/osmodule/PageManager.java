package com.kennesaw.osmodule;

import com.kennesaw.memory.Disk;
import com.kennesaw.memory.Ram;

import java.util.ArrayList;

/**
 * Created by Margaret on 11/16/2016.
 */
public class PageManager extends Thread {
    
    ArrayList<Integer> freeFramePool;
    Kernel simKernel;
    Ram simRam;
    Disk simDisk;
    private boolean isSystemRunning;
    
    public PageManager(Kernel kern, Ram mem, Disk disk) {
        simKernel = kern;
        simRam = mem;
        simDisk = disk;
        freeFramePool = new ArrayList<>();
        isSystemRunning = true;
        this.setName("Page Manager");
    }
    
    public synchronized void addPageToPool(int pageNum) {
        if (!freeFramePool.contains(pageNum))
        freeFramePool.add(pageNum);
    }
    
    public synchronized void removePageFromPool(int pageNum) {
        if (freeFramePool.contains(pageNum))
        freeFramePool.remove(Integer.valueOf(pageNum));
    }
    
    public synchronized void cleanPageTable(PCB pcb) {
        synchronized (pcb) {
            for (int i = 0; i < pcb.getPageTable().pageSpan.length; i++) {
                addPageToPool(pcb.getPageTable().pageSpan[i]);
                pcb.getPageTable().flipValid(i);
            }
        }
    }
    
    public int numOfFreePages() {
        return freeFramePool.size();
    }
    
    public boolean isPageAvailable(){
        return (freeFramePool.size() != 0);
    }
    
    public void run() {
        while (isSystemRunning) {
            if (simKernel.hasPageFaultJobs() && isPageAvailable()) {
                PCB currentPCB = simKernel.getJobFromPageFaultQueue();
                ArrayList<Integer> pageFaults = simKernel.getPagesFromPageFaultQueue(currentPCB);
                for (Integer logicalIndex : pageFaults) {
                    int diskIndex = currentPCB.getDiskAddressBegin() + logicalIndex;
                    Integer pageIndex = freeFramePool.get(0);
                    simRam.writeRam(pageIndex, simDisk.readDisk(diskIndex));
                    currentPCB.getPageTable().writePageTable(logicalIndex, pageIndex);
                    removePageFromPool(pageIndex);
                }
            }
        }
    }
    
    public String toString() {
        String toReturn = "Pages Available for Writing:\n";
        for (int i = 0; i < freeFramePool.size(); i++) {
            toReturn += freeFramePool.get(i);
            toReturn += "   ";
            if ((i+1) % 16 == 0) {
                toReturn += "\n";
            }
        }
        return toReturn;
    }

    public synchronized void endPageManager() {
        isSystemRunning = false;
    }
}
