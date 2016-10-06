import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Margaret on 9/16/2016.
 */
public class OS_Driver {
    
    Disk simDisk = new Disk(2048);
    Ram simRAM = new Ram(1024);
    Kernel simKernel = new Kernel();
    Loader simLoader;
    State simState = new State();
    DmaChannel simDMA = new DmaChannel(simRAM);
    CPU simCPU = new CPU(simState, simDMA);
    ShortTermScheduler simSTS = new ShortTermScheduler(simRAM, simCPU);
    
    public void runDriver() throws Exception {
        try {
            simLoader = new Loader(simDisk, simKernel);
        } catch (Exception e) {
            e.printStackTrace();
        }
    
//        ArrayList<PCB> pcbsBefore = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            Random ran = new Random();
//            int x = ran.nextInt(6) + 5;
//            pcbsBefore.add(new PCB(i + 1, (x + 20), x, (x + 555)));
//            System.out.println(pcbsBefore.get(i).getJobID());
//            System.out.println(pcbsBefore.get(i).getPriority());
//        }
        
    
        LongTermScheduler simLTS = new LongTermScheduler(simDisk, simRAM);
        simLTS.runLTS(simKernel);
        System.out.println(simRAM.toString());
        simLTS.runLTS(simKernel);
        System.out.println(simRAM.toString());
    
    
    
        // These lines print out some info about each PCB. Lines can be deleted when necessary
//        for (int i = 0; i < simKernel.getQueueSize(); i++) {
//            System.out.println("Job #" + simKernel.getPCB(i).getJobID());
//            System.out.println("Priority: " + simKernel.getPCB(i).getPriority());
//            System.out.println("Instruction Lines: " + simKernel.getPCB(i).getJobSize());
//            System.out.println("Disk address: " + simKernel.getPCB(i).getDiskAddressBegin() + "-" + simKernel.getPCB(i).getDiskAddressEnd());
//            System.out.println("RAM Address: " + simKernel.getPCB(i).getRAMAddressBegin() + "-" + simKernel.getPCB(i).getRAMAddressEnd());
//            System.out.println("RAM Base Address: " + simKernel.getPCB(i).getBaseAddress());
//            System.out.println("Status: " + simKernel.getPCB(i).getStatus());
//            System.out.println("===========");
//        }

//        simCPU.runProcess();
    
        }
    }