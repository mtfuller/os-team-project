package memory;

import java.util.Arrays;

public class Ram {

    Page newRAM[];
    int RAMlength;
    int occupiedRAM;
    int jobsOnRam;
    private boolean mutexLock;

    public Ram(int ramSpace) {
        RAMlength = ramSpace;
        newRAM = new Page[256];
        occupiedRAM = 0;
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

    public void writeRam(int address, Page page) {
        newRAM[address] = page;
    }

    public long readRam(int index, int addressPage) {

        return newRAM[index].readPage(addressPage);
    }

    public void setOccupiedRAM(int lastElement) {
        occupiedRAM = lastElement;
    }

    public int getOccupiedRAM() {
        return occupiedRAM;
    }

    public int getFreeRAMSpace() {
        return 256 - getOccupiedRAM();
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

        for (int i = 0; i < RAMlength; i++) {
            toReturn += ("Line # " + (i + 1) + " - " + readRam(i, i) + "\n");
        }
        return toReturn;
    }


}

