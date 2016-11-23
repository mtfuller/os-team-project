package memory;

import com.kennesaw.OS_Module.PageManager;

import java.util.Arrays;

public class Ram {
    
    Page newRAM[];
    int RAMlength;
    PageManager pageManager;
    int jobsOnRam;
    private boolean mutexLock;
    
    public Ram(int ramSpace) {
        RAMlength = ramSpace;
        newRAM = new Page[RAMlength];
        for (int i = 0; i < RAMlength; i++) {
            newRAM[i] = new Page();
        }
        jobsOnRam = 0;
        mutexLock = false;
    }
    
    public void assignPageMgr(PageManager pgmgr) {
        pageManager = pgmgr;
        for (int i = 0; i < RAMlength; i++) {
            pageManager.addPageToPool(new Integer(i));
        }
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
    
    public void writeRam(int address, Page pageData)
    {
        for (byte b = 0; b < Page.PAGE_SIZE; b++) {
            newRAM[address].writeToPage(b, pageData.readPage(b));
        }
        pageManager.removePageFromPool(address);
    }
    
    public long readRam(int index, int addressPage) {
        return newRAM[index].readPage(addressPage);
    }

    /*public void writeRam(int address, Page page) {
        newRAM[address] = page;
    }*/

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
