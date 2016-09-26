import java.util.ArrayList;

/**
 * Created by Margaret on 9/16/2016.
 */
public class MemorySystem {
    
    Disk simDisk;
    Kernel simPCB;
    
    public MemorySystem() {
        this.simDisk = new Disk(2048);
        this.simPCB = new Kernel();
    }
    
}
