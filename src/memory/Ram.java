package memory;

import java.util.Arrays;

public class Ram {
    
    Page newRAM[];
    int RAMlength;
    int freePages;
    int jobsOnRam;
    private boolean mutexLock;
    
    public Ram(int ramSpace) {
        RAMlength = ramSpace;
        newRAM = new Page[RAMlength];
        for (int i = 0; i < RAMlength; i++) {
            newRAM[i] = new Page();
        }
        freePages = ramSpace;
        jobsOnRam = 0;
        mutexLock = false;
    }
    
    public boolean isLocked() {
        return mutexLock;
    }
    
    public void lock() {
        mutexLock = true;
    }
    
    public void unlock() {
        mutexLock = false;
    }
    
    public void writeRam(int address, int pageOffset, long data) {
        newRAM[address].writeToPage(pageOffset, data);
    }
    
    public long readRam(int index, int addressPage) {
        return newRAM[index].readPage(addressPage);
    }
    
    public int getFreePages() {
        return freePages;
    }
    
    public int getJobsOnRam() {
        return jobsOnRam;
    }
    
    public void resetJobCount() {
        jobsOnRam = 0;
    }
    
    public void addedJobToRam() {
        jobsOnRam++;
    }
    
    public String toString() {
        String toReturn = "";
        
        for (int i = 0; i < newRAM.length; i++) {
            toReturn += ("Page # " + i + "-\n");
            for (int j = 0; j < 4; j++) {
                toReturn += (readRam(i, j) + "\n");
            }
            toReturn += ("\n");
        }
        return toReturn;
    }
    
    
}