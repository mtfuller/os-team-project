import java.lang.reflect.Type;

/**
 * Created by Margaret on 9/16/2016.
 */

// Disk is a String object. But when jobs are scooped from Disk,
// each instruction line is converted to Long
    
public class Disk {
    
    String[] newDisk;
    int occupiedSpace;
    
    public Disk(int diskLength) {
        newDisk  = new String[diskLength];
        occupiedSpace = 0;
    }
    
    // Writes instruction line to Disk as a string
    public void writeLineToDisk(String input, int index) {
        newDisk[index] = input;
    }
    
    // Returns the job's instruction lines, as an array of Long elements
    public long[] getJobChunk(PCB pcb){
        int lengthOfJob = (pcb.getDiskAddressEnd() - pcb.getDiskAddressBegin());
        long[] jobChunk = new long[lengthOfJob];
        for (int i = 0; i < lengthOfJob; i++) {
            Long toHex = Long.parseLong(newDisk[i + pcb.getDiskAddressBegin()], 16);
            jobChunk[i] = toHex;
        }
        return jobChunk;
    }
    
    public int getJobSize(PCB pcb) {
        return pcb.getDiskAddressEnd() - pcb.getDiskAddressBegin();
    }
    
    public void setOccupiedDiskSpace(int lastElement) {
        occupiedSpace = lastElement;
    }
    
    int getOccupiedDiskSpace() {
        return occupiedSpace;
    }
}
