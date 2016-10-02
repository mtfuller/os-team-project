/**
 * Created by Margaret on 9/26/2016.
 */
public class Ram {
    
    long[] newRAM;
    int RAMlength;
    int occupiedRAM;
    
    public Ram(int ramSpace) {
        RAMlength = ramSpace;
        newRAM = new long[1024];
        occupiedRAM = 0;
    }
    
    public int getRAMlength() {
        return RAMlength;
    }
    
    public void writeToRAM(long input, int index) {
        newRAM[index] = input;
    }
    
    public long getRAMLine(int index) {
        return newRAM[index];
    }
    
    public void setOccupiedRAM(int lastElement) {
        occupiedRAM = lastElement;
    }
    
    public int getOccupiedRAM() {
        return occupiedRAM;
    }
    
    public int getFreeRAMSpace() {
        return 1024-getOccupiedRAM();
    }
    
    public boolean isEnoughSpace(int pcbLength){
        return (getFreeRAMSpace() >= pcbLength);
    }
    
}
