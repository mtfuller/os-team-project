import java.util.ArrayList;

/**
 * Created by Margaret on 9/16/2016.
 */
public class OS_Driver {
    
    Disk simDisk = new Disk(2048);
    Ram simRAM = new Ram(1024);
    Kernel simKernel = new Kernel();
    Loader simLoader;
    ShortTermScheduler simSTS = new ShortTermScheduler();
    State simState = new State();
    DmaChannel simDMA = new DmaChannel();
    CPU simCPU = new CPU(simState, simDMA);
    
    
    public OS_Driver() throws Exception {
        try {
            simLoader = new Loader(simDisk, simKernel);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        LongTermScheduler simLTS = new LongTermScheduler(simDisk, simRAM);
        simLTS.runLTS(simKernel);
        simLTS.runLTS(simKernel);
        
//        simCPU.runProcess();
    
        // These lines print out some info about each PCB. Lines can be deleted when necessary
//        for (int i = 0; i < simKernel.getQueueSize(); i++) {
//            System.out.println("Job #" + simKernel.getPCB(i).getJobID());
//            System.out.println("Priority: " + simKernel.getPCB(i).getPriority());
//            System.out.println("Instruction Lines: " + simKernel.getPCB(i).getJobSize());
//            System.out.println("Disk address: " + simKernel.getPCB(i).getDiskAddressBegin() + "-" + simKernel.getPCB(i).getDiskAddressEnd());
//            System.out.println("===========");
//        }
    }
}
    