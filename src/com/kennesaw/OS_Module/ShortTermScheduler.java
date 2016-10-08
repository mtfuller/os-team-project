package com.kennesaw.OS_Module;

/**
 * Created by Margaret on 9/20/2016.
 */

import com.kennesaw.cpumodule.CPU;
import memory.Ram;

public class ShortTermScheduler {
    
    Ram simRAM;
    CPU simCPU;
    Kernel simKernel;
    
    public ShortTermScheduler(Ram ram, CPU cpu, Kernel kernel) {
        simCPU = cpu;
        simRAM = ram;
        simKernel = kernel;
    }
    
    public void runSTS() {
        for (int i = 0; i < simRAM.getJobsOnRam(); i++) {
            if (simKernel.getPCB(i).getStatus() == "Waiting") {
                simCPU.runPCB(simKernel.getPCB(i));
                simKernel.getPCB(i).setStatus(4);
            } else {
                simCPU.runPCB(simKernel.getPCB(i + simRAM.getJobsOnRam()));
                simKernel.getPCB(i).setStatus(4);
            }
        }
        simRAM.resetJobCount();
    }
    
}


