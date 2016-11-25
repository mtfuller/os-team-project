package com.kennesaw.OS_Module;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by Margaret on 9/20/2016.
 */

// This class holds all PCBs and sorts when necessary

public class Kernel {
    
    ArrayList<PCB> pcbQueue;
    MemoryMapping pageFaultQueue;
    MemoryMapping ioQueue;
    int pcbQueuePointer;
    
    public Kernel() {
        pcbQueue = new ArrayList<>();
        pageFaultQueue = new MemoryMapping();
        ioQueue = new MemoryMapping();
        pcbQueuePointer = 0;
    }
    
    public synchronized void sortPriority() {
        for (int j = 1; j < pcbQueue.size(); j++) {
            PCB key = pcbQueue.get(j);
            int i = j - 1;
            while ((i > -1) && (pcbQueue.get(i).getPriority() > key.getPriority())) {
                pcbQueue.set((i + 1), pcbQueue.get(i));
                i--;
            }
            pcbQueue.set((i + 1), key);
        }
    }
    
    public synchronized void sortSJF() {
        for (int j = 1; j < pcbQueue.size(); j++) {
            PCB key = pcbQueue.get(j);
            int i = j - 1;
            while ((i > -1) && (pcbQueue.get(i).getJobSize() > key.getJobSize())) {
                pcbQueue.set((i + 1), pcbQueue.get(i));
                i--;
            }
            pcbQueue.set((i + 1), key);
        }
    }
    
    public synchronized PCB getPCB(int index) {
        return pcbQueue.get(index);
    }
    
    public synchronized PCB getNextPCB() {
        return pcbQueue.get(pcbQueuePointer);
    }
    
    public synchronized void addPCB(int index, PCB newPCB) {
        pcbQueue.add(index, newPCB);
    }
    
    public synchronized void addToPageFaultQueue(PCB pcb, Integer integer) {
        pageFaultQueue.addPageToPCB(pcb, integer);
    }
    
    public synchronized PCB getJobFromPageFaultQueue() {
        return pageFaultQueue.getNextPCB();
    }

    public synchronized ArrayList<Integer> getPagesFromPageFaultQueue(PCB pcb) {
        return pageFaultQueue.getPagesForPCB(pcb);
    }
    
    public synchronized void removeFromPageFaultQueue(PCB pcb) {
        pageFaultQueue.removeFromQueue(pcb);
    }
    
    public synchronized void addToioQueueQueue(PCB pcb, Integer integer) {
        ioQueue.addPageToPCB(pcb, integer);
    }
    
    public synchronized PCB getJobFromioQueueQueue() {
        return ioQueue.getNextPCB();
    }

    public synchronized ArrayList<Integer> getPagesFromIoQueue(PCB pcb) {
        return ioQueue.getPagesForPCB(pcb);
    }
    
    public synchronized void removeFromioQueueQueue(PCB pcb) {
        ioQueue.removeFromQueue(pcb);
    }
    
    public synchronized boolean hasPageFaultJobs() {
        return !pageFaultQueue.isEmpty();
    }
    
    public synchronized boolean hasIOJobs() {
        return !ioQueue.isEmpty();
    }
    
    public synchronized int getQueueSize() {
        return pcbQueue.size();
    }
    
    public int getPageFaultSize() {
        return pageFaultQueue.size();
    }
    
    public int getIOSize() {
        return ioQueue.size();
    }
    
}
