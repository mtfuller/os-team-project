/**
 * Created by willw on 9/27/2016.
 */
public class Disk {
    int[] diskArray;
    int spaceD;
    int count;
    public Disk(){
        diskArray = new int[2048];
        spaceD = 2048;

    }
    public int readDisk(int address){
        return diskArray[address];

    }
    public void writeDisk(int address, int value){
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
