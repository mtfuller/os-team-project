import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by Margaret on 9/16/2016.
 */
public class OS_Driver {
    
    MemorySystem simMemory = new MemorySystem();
    Loader newLoader;
    
    public OS_Driver() {
        
        try {
            newLoader = new Loader(simMemory.simDisk, simMemory.simPCB);
        } catch (Exception e) {
            e.printStackTrace();
        }
    
        System.out.println(simMemory.simDisk.getJobChunk(simMemory.simPCB.accessPCBQueue().getPCB(29)).length() / 8);
        System.out.println(simMemory.simDisk.spaceAvailable());
    
        String inst_line = "FFFFFFFF";
        Long bi = Long.parseLong(inst_line, 16);
        System.out.println(bi);
        
    }
}
