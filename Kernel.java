import java.util.ArrayList;

/**
 * Created by Margaret on 9/20/2016.
 */

// This class holds all PCBs and sorts when necessary
    
public class Kernel {
    
    ArrayList<PCB> pcbQueue;
    ArrayList<PCB> sortedReadyQueue;
    
    public Kernel() {
        pcbQueue = new ArrayList<>();
        sortedReadyQueue = new ArrayList<>();
    }
    
    public PCB getPCB(int index) {
        return sortedReadyQueue.get(index);
    }
    
    public void addPCB(int index, PCB newPCB) {
        sortedReadyQueue.add(index, newPCB);
    }
    
    public int getQueueSize() {
        return sortedReadyQueue.size();
    }

    public ArrayList<PCB> accessReadyQueue() {
        return sortedReadyQueue;
    }
    
}
