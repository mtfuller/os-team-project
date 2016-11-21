package com.kennesaw.OS_Module;

import memory.Disk;
import memory.Page;
import memory.Ram;

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
    }
    
    public void addPageToPool(int pageNum) {
        freeFramePool.add(pageNum);
    }
    
    public void removePageFromPool(int pageNum) {
        freeFramePool.remove(Integer.valueOf(pageNum));
    }
    
    public void cleanPageTable(PCB pcb) {
        for (int i = 0; i < pcb.getPageTable().pageSpan.length; i++) {
            addPageToPool(pcb.getPageTable().pageSpan[i]);
            pcb.getPageTable().flipValid(i);
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
                for (int i = 0; i < 4; i++) {
                    simRam.writeRam(freeFramePool.get(0), i, simDisk.readDisk(simKernel.getJobFromPageFaultQueue().getDiskAddressBegin()).readPage(i));
                }
                simKernel.removeFromPageFaultQueue(simKernel.getJobFromPageFaultQueue());
                removePageFromPool(freeFramePool.get(0));
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
}
