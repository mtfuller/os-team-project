/**
 * Created by willw on 9/27/2016.
 */
public class Ram {
    int[] ramArray;
    int spaceR;
    int count;
    public Ram(){
        ramArray = new int[1024];
        spaceR = 1024;
        count = 0;
    }
    public int readRam(int address){
        return ramArray[address];


    }
    public void writeRam(int address, int value){

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
