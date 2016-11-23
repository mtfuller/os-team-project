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
        this.setName("Page Manager");
    }
    
    public synchronized void addPageToPool(int pageNum) {
        freeFramePool.add(pageNum);
    }
    
    public synchronized void removePageFromPool(int pageNum) {
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
            synchronized (simKernel) {
                if (simKernel.hasPageFaultJobs() && isPageAvailable()) {
                    MemoryMapping memoryMapping = simKernel.getJobFromPageFaultQueue();
                    PCB currentPCB = memoryMapping.getPcbReference();
                    synchronized (currentPCB) {
                        int diskIndex = currentPCB.getDiskAddressBegin() + memoryMapping.getDestIndex();
                        Integer pageIndex = freeFramePool.get(0);
                        simRam.writeRam(pageIndex, simDisk.readDisk(diskIndex));
                        currentPCB.getPageTable().writePageTable(diskIndex, pageIndex);
                        simKernel.removeFromPageFaultQueue(memoryMapping);
                        removePageFromPool(pageIndex);
                    }
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
}
