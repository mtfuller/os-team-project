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
        
        // These lines print out some info about each PCB. Lines can be deleted when necessary
        for (int i = 0; i < simMemory.simPCB.accessPCBQueue().getQueueSize(); i++) {
            System.out.println("Job #" + simMemory.simPCB.accessPCBQueue().getPCB(i).getJobID());
            System.out.println("Priority: " + simMemory.simPCB.accessPCBQueue().getPCB(i).getPriority());
            System.out.println("Instruction Lines: " + simMemory.simPCB.accessPCBQueue().getPCB(i).getJobSize());
            System.out.println("Disk address: " + simMemory.simPCB.accessPCBQueue().getPCB(i).getDiskAddressBegin() + "-" + simMemory.simPCB.accessPCBQueue().getPCB(i).getDiskAddressEnd());
            System.out.println("===========");
        }
    }
}
