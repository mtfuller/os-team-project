package memory;

/**
 * Created by willw on 9/27/2016.
 */
public class Disk {
    long[] diskArray;
    int spaceD;
    int count;
    public Disk(){
        diskArray = new long[2048];
        spaceD = 2048;

    }
    public long readDisk(int address){
        return diskArray[address];

    }
    public void writeDisk(int address, long value){
        diskArray[address] = value;
        spaceD--;
        count++;
    }
    public int getDiskSpaceAvaliable(){
        return spaceD;
    }
    public int getAmountInDisk(){
        return count;
    }
}
