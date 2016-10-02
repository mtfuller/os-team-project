import java.util.ArrayList;

/**
 * Created by Margaret on 9/20/2016.
 */

// This class will sort the PCBs based on sorting scheme
// Work in progress
public class Kernel {
    
    ArrayList<PCB> pcbQueue;
    ArrayList<PCB> readyQueue;
    
    public Kernel() {
        pcbQueue = new ArrayList<>();
        readyQueue = new ArrayList<>();
    }
    
    public PCB getPCB(int index) {
        return readyQueue.get(index);
    }
    
    public void addPCB(int index, PCB newPCB) {
        readyQueue.add(index, newPCB);
    }
    
    public int getQueueSize() {
        return readyQueue.size();
    }
    
//    public void sortPCBs(String algo) {
//        // Move PCBs from first queue into this queue, sorted
//        readyQueue = simPCBQueue;
//        System.out.println(readyQueue);
//    }
//
    public ArrayList<PCB> accessReadyQueue() {
        return readyQueue;
    }
    
}
