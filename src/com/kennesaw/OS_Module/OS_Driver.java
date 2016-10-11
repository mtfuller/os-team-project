package com.kennesaw.OS_Module;

/**
 * Created by Margaret on 9/16/2016.
 */

import memory.*;
import com.kennesaw.cpumodule.CPU;
import com.kennesaw.cpumodule.DmaChannel;

public class OS_Driver {
    
    Disk simDisk = new Disk(2048);
    Ram simRAM = new Ram(1024);
    Kernel simKernel = new Kernel();
    Loader simLoader;
    CPU simCPU = new CPU(simRAM);
    
    // com.kennesaw.OS_Module.Loader populates Disk with instructions from ProgramFile.txt
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