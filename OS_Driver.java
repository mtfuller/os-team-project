/**
 * Created by Margaret on 9/16/2016.
 */

public class OS_Driver {
    
    Disk simDisk = new Disk(2048);
    Ram simRAM = new Ram(1024);
    Kernel simKernel = new Kernel();
    Loader simLoader;
    DmaChannel simDMA = new DmaChannel(simRAM);
    CPU simCPU = new CPU(simDMA);
    
    // Loader populates Disk with instructions from ProgramFile.txt
    public void runDriver() throws Exception {
        try {
            simLoader = new Loader(simDisk, simKernel);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Initialize Long-term and Short-term schedulers
        LongTermScheduler simLTS = new LongTermScheduler(simDisk, simRAM);
        ShortTermScheduler simSTS = new ShortTermScheduler(simRAM, simCPU, simKernel);
        
        // While there are jobs on the Disk, schedule those jobs and send them to the CPU
        while (simDisk.getJobsOnDisk() > 0) {
            simLTS.runLTS(simKernel);
            simSTS.runSTS();
        }
    }
}