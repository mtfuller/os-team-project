import java.util.Arrays;
    
public class Disk {
    
    long[] newDisk;
    int occupiedSpace;
    int jobsOnDisk;
    
    public Disk(int diskLength) {
        newDisk  = new long[diskLength];
        occupiedSpace = 0;
    }
    
    public void writeDisk(int address, long value) {
        newDisk[address] = value;
    }
    
    public long readDisk(int address){
        return newDisk[address];
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
        return Arrays.toString(newDisk);
    }
    
}
