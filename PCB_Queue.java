import java.util.ArrayList;

/**
 * Created by Margaret on 9/16/2016.
 */
public class PCB_Queue {
    
    ArrayList<PCB> pcbs;
    
    public PCB_Queue() {
        this.pcbs = new ArrayList<PCB>();
    }
    
    public PCB getPCB(int index) {
        return pcbs.get(index);
    }
    
    public void addPCB(int index, PCB newPCB) {
        pcbs.add(index, newPCB);
    }
    
    public int getQueueSize() {
        return pcbs.size();
    }
    
}
