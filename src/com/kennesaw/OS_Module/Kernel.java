package com.kennesaw.OS_Module;

import java.util.ArrayList;

/**
 * Created by Margaret on 9/20/2016.
 */

// This class holds all PCBs and sorts when necessary
    
public class Kernel {
    
    ArrayList<PCB> pcbQueue;
    
    public Kernel() {
        pcbQueue = new ArrayList<>();
    }
    
    public void sortPriority() {
        for (int j = 1; j < pcbQueue.size(); j++) {
            for (int q = 0; q < pcbQueue.size(); q++) {
            }
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
            for (int q = 0; q < pcbQueue.size(); q++) {
            }
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
    
    public int getQueueSize() {
        return pcbQueue.size();
    }

    public ArrayList<PCB> accessReadyQueue() {
        return pcbQueue;
    }
    
}
