package com.kennesaw.OS_Module;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by Margaret on 9/20/2016.
 */

// This class holds all PCBs and sorts when necessary

public class Kernel {
    
    ArrayList<PCB> pcbQueue;
    LinkedList<PCB> pageFaultQueue;
    LinkedList<PCB> ioQueue;
    
    public Kernel() {
        pcbQueue = new ArrayList<>();
        pageFaultQueue = new LinkedList<>();
        ioQueue = new LinkedList<>();
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
    
    public void addPCB(int index, PCB newPCB) {
        pcbQueue.add(index, newPCB);
    }
    
    public void addToPageFaultQueue(PCB pcb) {
        if (!pageFaultQueue.contains(pcb)) pageFaultQueue.addLast(pcb);
    }
    
    public PCB getJobFromPageFaultQueue() {
        return pageFaultQueue.getFirst();
    }
    
    public void removeFromPageFaultQueue(PCB pcb) {
        if (pageFaultQueue.contains(pcb)) pageFaultQueue.remove(pcb);
    }
    
    public void addToioQueueQueue(PCB pcb) {
        if (!ioQueue.contains(pcb)) ioQueue.addLast(pcb);
    }
    
    public PCB getJobFromioQueueQueue() {
        return ioQueue.getFirst();
    }
    
    public void removeFromioQueueQueue(PCB pcb) {
        if (ioQueue.contains(pcb)) ioQueue.remove(pcb);
    }
    
    public boolean hasPageFaultJobs() {
        return !pageFaultQueue.isEmpty();
    }
    
    public boolean hasIOJobs() {
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