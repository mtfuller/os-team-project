package com.kennesaw.OS_Module;

/**
 * Created by Margaret on 9/20/2016.
 */

import java.util.ArrayList;

import com.kennesaw.Analyzer.Analysis;
import com.kennesaw.cpumodule.CPU;
import com.kennesaw.cpumodule.DmaChannel;
import com.kennesaw.cpumodule.MMU;
import memory.Ram;

public class ShortTermScheduler {
    MMU simMMU;
    Ram simRAM;
    Kernel simKernel;
    ArrayList<CPU> cpuBank = new ArrayList<>();
    private int cpuIndexPtr = 0;
    
    public ShortTermScheduler(Ram ram, Kernel kernel, int numCPUs) {
        simRAM = ram;
        simKernel = kernel;
        simMMU = new MMU(simKernel,simRAM);
        for (int i = 0; i < numCPUs; i++) {
            cpuBank.add(new CPU(i, simMMU));
            cpuBank.get(i).start();
        }
//        for(int i = 0; i < 30; i++) {
//            double used = (double)simKernel.getPCB(i).getJobSize() / 1024.00;
//            Analysis.recordRamSpace(i, used);
//        }
    }
    
    
    public int findCPU() {
        boolean openCpuFound = false;
        
        // Spin until a free CPU is found in the CPU Bank
        while (!openCpuFound) {
            openCpuFound = (!cpuBank.get(cpuIndexPtr).isRunningProcess());
            if (openCpuFound) {
                break;
            } else {
                cpuIndexPtr = (++cpuIndexPtr) % cpuBank.size();
            }
        }
        return cpuIndexPtr;
    }
    
    public void runSTS() {
        int cpuIndex;
        
        // Continue to spin as long as the Kernel has ready jobs
        while (simKernel.getPCB(simKernel.pcbQueue.size()-1).getStatus() != "Ended") {
            PCB nextJob = simKernel.getNextPCB();
            System.out.println(nextJob.getStatus());
            cpuIndex = findCPU();
            while (cpuBank.get(cpuIndex).isRunningProcess());
            if (nextJob.getStatus() == "Ready") {
//                Analysis.recordWaitTime(nextJob.getJobID()-1);
                cpuBank.get(cpuIndex).runPCB(nextJob);
                simKernel.getPCB(simKernel.pcbQueuePointer).setStatus(4);
            }
        }
    
        // Resets the number of jobs in RAM
        simRAM.resetJobCount();
    
        // Spin until all cpus have finished processing
        for (CPU cpu : cpuBank) {
            while (cpu.isRunningProcess());
        }
    }
}