package com.kennesaw.memory;


import com.kennesaw.osmodule.PCB;

public class Disk {
    
    Page[] newDisk;
    int jobsOnDisk;
    int nextFreePage;
    
    public Disk(int diskLength) {
        newDisk = new Page[diskLength];
        for (int i = 0; i < diskLength; i++) {
            newDisk[i] = new Page();
        }
        nextFreePage = 0;
    }
    
    public synchronized void writeDisk(int pageNum, int index, long instr) {
        newDisk[pageNum].writeToPage(index, instr);
        if (newDisk[pageNum].isPageFull()) {
            incNextFreePage();
        }
    }
    
    public synchronized Page readDisk(int address) {
        return newDisk[address];
    }
    
    public synchronized int getNextFreePage() {
        return nextFreePage;
    }
    
    public synchronized void incNextFreePage() {
        nextFreePage++;
    }
    
    public int getJobSize(PCB pcb) {
        return pcb.getDiskAddressEnd() - pcb.getDiskAddressBegin();
    }
    
    public int getJobsOnDisk() {
        return jobsOnDisk;
    }
    
    public synchronized void addedJobToDisk() {
        jobsOnDisk++;
    }
    
    public void removedJobFromDisk() {
        jobsOnDisk--;
    }
    
    public String toString() {
        String toReturn = "";
        
        for (int i = 0; i < newDisk.length; i++) {
            toReturn += ("Page # " + i + "-\n");
            for (int j = 0; j < 4; j++) {
                toReturn += (readDisk(i).readPage(j) + "\n");
            }
            toReturn += ("\n");
        }
        return toReturn;
    }
}