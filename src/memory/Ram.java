package memory;

import com.kennesaw.OS_Module.PageManager;

import java.util.Arrays;

public class Ram {
    
    Page newRAM[];
    int RAMlength;
    PageManager pageManager;
    int jobsOnRam;
    private boolean mutexLock;
    
    public Ram(int ramSpace, PageManager pagemgr) {
        RAMlength = ramSpace;
        newRAM = new Page[RAMlength];
        pageManager = pagemgr;
        for (int i = 0; i < RAMlength; i++) {
            newRAM[i] = new Page();
            pageManager.addPageToPool(i);
        }
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
        pageManager.removePageFromPool(address);
    }
    
    public long readRam(int index, int addressPage) {
        return newRAM[index].readPage(addressPage);
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