package memory;


import com.kennesaw.OS_Module.PCB;

public class Disk {

    Page[] newDisk;
    int occupiedSpace;
    int jobsOnDisk;

    public Disk(int diskLength) {
        newDisk = new Page[diskLength];
        occupiedSpace = 0;
    }

    public void writeDisk(int address, Page page) {

        newDisk[address] = page;
    }

    public long readDisk(int address, int addressPage) {
        return newDisk[address].readPage(addressPage);
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
            toReturn += ("Line # " + (i + 1) + " - " + readDisk(i,i) + "\n");
        }
        return toReturn;
    }



}