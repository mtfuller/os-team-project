package com.kennesaw.OS_Module;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by Margaret on 9/20/2016.
 */

// This class holds all PCBs and sorts when necessary

public class Kernel {
    
    ArrayList<PCB> pcbQueue;
    LinkedList<MemoryMapping> pageFaultQueue;
    LinkedList<MemoryMapping> ioQueue;
    int pcbQueuePointer;
    
    public Kernel() {
        pcbQueue = new ArrayList<>();
        pageFaultQueue = new LinkedList<>();
        ioQueue = new LinkedList<>();
        pcbQueuePointer = 0;
    }
    
    public void sortPriority() {
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
    
    public void sortSJF() {
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
    
    public PCB getPCB(int index) {
        return pcbQueue.get(index);
    }
    
    public PCB getNextPCB() {
        return pcbQueue.get(pcbQueuePointer);
    }
    
    public void addPCB(int index, PCB newPCB) {
        pcbQueue.add(index, newPCB);
    }
    
    public synchronized void addToPageFaultQueue(MemoryMapping memMap) {
        if (!pageFaultQueue.contains(memMap)) pageFaultQueue.addLast(memMap);
        //memMap.getPcbReference().setStatus(2);
    }
    
    public synchronized MemoryMapping getJobFromPageFaultQueue() {
        return pageFaultQueue.getFirst();
    }
    
    public synchronized void removeFromPageFaultQueue(MemoryMapping memoryMapping) {
        if (pageFaultQueue.contains(memoryMapping)) pageFaultQueue.remove(memoryMapping);
        //memoryMapping.getPcbReference().setStatus(1);
    }
    
    public synchronized void addToioQueueQueue(MemoryMapping memMap) {
        if (!ioQueue.contains(memMap)) ioQueue.addLast(memMap);
        //memMap.getPcbReference().setStatus(2);
    }
    
    public synchronized MemoryMapping getJobFromioQueueQueue() {
        return ioQueue.getFirst();
    }
    
    public synchronized void removeFromioQueueQueue(MemoryMapping memoryMapping) {
        if (ioQueue.contains(memoryMapping)) ioQueue.remove(memoryMapping);
        //memoryMapping.getPcbReference().setStatus(1);
    }
    
    public synchronized boolean hasPageFaultJobs() {
        return !pageFaultQueue.isEmpty();
    }
    
    public synchronized boolean hasIOJobs() {
        return !ioQueue.isEmpty();
    }
    
    public int getQueueSize() {
        return pcbQueue.size();
    }
    
    public int getPageFaultSize() {
        return pageFaultQueue.size();
    }
    
    public int getIOSize() {
        return ioQueue.size();
    }
    
}
