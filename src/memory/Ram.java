package memory;

import java.util.Arrays;

public class Ram {
    
    long[] newRAM;
    int RAMlength;
    int occupiedRAM;
    int jobsOnRam;
    
    public Ram(int ramSpace) {
        RAMlength = ramSpace;
        newRAM = new long[1024];
        occupiedRAM = 0;
        jobsOnRam = 0;
    }
    
    public void writeRam(int address, long value) {
        newRAM[address] = value;
    }
    
    public long readRam(int index) {
        return newRAM[index];
    }
    
    public void setOccupiedRAM(int lastElement) {
        occupiedRAM = lastElement;
    }
    
    public int getOccupiedRAM() {
        return occupiedRAM;
    }
    
    public int getFreeRAMSpace() {
        return 1024 - getOccupiedRAM();
    }
    
    public int getJobsOnRam() {
        return jobsOnRam;
    }
    
    public void resetJobCount(){
        jobsOnRam = 0;
    }
    
    public void addedJobToRam() {
        jobsOnRam++;
    }
    
    public String toString() {
        String toReturn = "";
        
        for (int i = 0; i < RAMlength; i++) {
            toReturn += ("Line # " + (i + 1) + " - " + readRam(i) + "\n");
        }
        return toReturn;
    }
    
}