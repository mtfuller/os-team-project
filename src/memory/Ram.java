package memory;

/**
 * Created by willw on 9/27/2016.
 */
public class Ram {
    long[] ramArray;
    int spaceR;
    int count;
    public Ram(){
        ramArray = new long[1024];
        spaceR = 1024;
        count = 0;
    }
    public long readRam(int address){
        return ramArray[address];


    }
    public void writeRam(int address, long value){

        ramArray[address] = value;
        spaceR--;
        count++;

    }
    public int getRamSpaceAvaliable(){
        return spaceR;
    }
    public int getAmountInRam(){
        return count;
    }
}
