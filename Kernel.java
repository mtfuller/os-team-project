/**
 * Created by Margaret on 9/20/2016.
 */

// This class holds the PCB queue and will sort based on sorting scheme
// Work in progress
public class Kernel {
    
    PCB_Queue pcbQueue;
    PCB_Queue readyQueue;
    
    public Kernel() {
        this.pcbQueue = new PCB_Queue();
        this.readyQueue = new PCB_Queue();
    }
    
    public void sortPCBs(String algo) {
        // Move PCBs from first queue into this queue, sorted
    }
    
    public PCB_Queue accessPCBQueue() {
        return pcbQueue;
    }
    
    public PCB_Queue accessReadyQueue() {
        return readyQueue;
    }
    
}
