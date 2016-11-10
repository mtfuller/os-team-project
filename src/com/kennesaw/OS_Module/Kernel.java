package com.kennesaw.OS_Module;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by Margaret on 9/20/2016.
 */

// This class holds all PCBs and sorts when necessary
    
public class Kernel {

    LinkedList<PCB> readyQueue;
    ArrayList<PCB> pcbQueue;
    
    public Kernel() {
        pcbQueue = new ArrayList<>();
        readyQueue = new LinkedList<>();
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

    public void addToReadyQueue(PCB pcb) {
        if (!readyQueue.contains(pcb)) readyQueue.addLast(pcb);
    }

    public PCB getJobFromReadyQueue() {
        return readyQueue.getFirst();
    }

    public void removeFromReadyQueue(PCB pcb) {
        if (readyQueue.contains(pcb)) readyQueue.remove(pcb);
    }

    public boolean hasReadyJobs() {
        return !readyQueue.isEmpty();
    }

    public int getQueueSize() {
        return pcbQueue.size();
    }
}
