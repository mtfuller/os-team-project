package memory;


import com.kennesaw.OS_Module.PCB;

public class Disk {

    Page[] newDisk;
    int jobsOnDisk;
    int nextFreePage;

    public Disk(int diskLength) {
        newDisk = new Page[diskLength];
        nextFreePage = 0;
    }

//    public void writeDisk(int address, Page page) {
//        newDisk[address] = page;
//        occupiedPages++;
//    }
    
    public void writeDisk(int pageNum, int index, long instr) {
        newDisk[pageNum].writeToPage(index, instr);
        if (newDisk[pageNum].isPageFull()) {
            incNextFreePage();
        }
    }

    public Page readDisk(int address) {
        return newDisk[address];
    }
    
    public int getNextFreePage() {
        return nextFreePage;
    }
    
    public void incNextFreePage() {
        nextFreePage++;
    }

    public int getJobSize(PCB pcb) {
        return pcb.getDiskAddressEnd() - pcb.getDiskAddressBegin();
    }

    public int getJobsOnDisk() {
        return jobsOnDisk;
    }

    public void addedJobToDisk() {
        jobsOnDisk++;
    }

    public void removedJobFromDisk() {
        jobsOnDisk--;
    }

    public String toString() {
        String toReturn = "";

        for (int i = 0; i < newDisk.length; i++) {
            toReturn += ("Line # " + (i + 1) + " - " + readDisk(i) + "\n");
        }
        return toReturn;
    }



}