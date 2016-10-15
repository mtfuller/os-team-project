package com.kennesaw.OS_Module;

/**
 * Created by Margaret on 9/20/2016.
 */

import com.kennesaw.cpumodule.CPU;
import com.kennesaw.cpumodule.DmaChannel;
import memory.Ram;

import java.util.ArrayList;

public class ShortTermScheduler {
    
    Ram simRAM;
    Kernel simKernel;
    ArrayList<CPU> cpuBank = new ArrayList<>();
    
    public ShortTermScheduler(Ram ram, Kernel kernel, int numCPUs) {
        simRAM = ram;
        simKernel = kernel;
        for (int i = 0; i < numCPUs; i++) {
            cpuBank.add(new CPU(new DmaChannel(simRAM)));
            cpuBank.get(i).start();
        }
    }
    
    public int findCPU() {
        int indexFinder = 0;
        boolean searching = true;
        if (cpuBank.size() == 1) {
            return indexFinder;
        } else {
            while (searching) {
                System.out.println(indexFinder % cpuBank.size());
                if (cpuBank.get(indexFinder % cpuBank.size()).isRunningProcess()) {
                    indexFinder++;
                } else {
                    searching = false;
                }
            }
        }
        return (indexFinder % cpuBank.size());
    }
    
    public void runSTS() {
        for (int i = 0; i < simRAM.getJobsOnRam(); i++) {
            int cpuIndex = findCPU();
            System.out.println("In STS: " + i + " .... " + cpuIndex);
            if (simKernel.getPCB(i).getStatus() == "Waiting") {
                cpuBank.get(cpuIndex).runPCB(simKernel.getPCB(i));
                simKernel.getPCB(i).setStatus(4);
            } else {
                cpuBank.get(cpuIndex).runPCB(simKernel.getPCB(i + simRAM.getJobsOnRam()));
                simKernel.getPCB(i).setStatus(4);
            }
        }
        simRAM.resetJobCount();
    }
    
}


