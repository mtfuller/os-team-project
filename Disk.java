/**
 * Created by Margaret on 9/16/2016.
 */
public class Disk {
    
    String newDisk;
    
    public Disk() {
        this.newDisk  = "";
    }
    
    public void writeLineToDisk(String input) {
        newDisk += input;
    }
    
    public String getJobChunk(PCB pcb){
        return newDisk.substring((pcb.getDiskAddressBegin() * 8), (pcb.getDiskAddressEnd() * 8));
    }
    
    int getOccupiedDiskSpace() {
        return newDisk.length() / 8;
    }
    
    // Returns the number of words that can be held on Disk
    public int spaceAvailable() {
        return 2048 - getOccupiedDiskSpace();
    }
}
