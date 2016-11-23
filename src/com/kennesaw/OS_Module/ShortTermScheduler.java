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
    PageManager pageManager;
    DmaChannel dmaChannel;
    ArrayList<CPU> cpuBank = new ArrayList<>();
    private int cpuIndexPtr = 0;
    
    public ShortTermScheduler(Ram ram, Kernel kernel, PageManager pageMan, int numCPUs) {
        simRAM = ram;
        simKernel = kernel;
        dmaChannel = new DmaChannel(simRAM, simKernel);
        dmaChannel.start();
        pageManager = pageMan;
        pageManager.start();
        simMMU = new MMU(simKernel,simRAM);
        for (int i = 0; i < numCPUs; i++) {
            cpuBank.add(new CPU(i, simMMU));
            cpuBank.get(i).start();
        }
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
        while (simKernel.getQueueSize() > 0) {
            PCB nextJob = simKernel.getNextPCB();
            System.out.println(nextJob.getStatus());
            cpuIndex = findCPU();
            while (cpuBank.get(cpuIndex).isRunningProcess());
            if (nextJob.getStatus() == "Ready") {
                // Analysis.recordWaitTime(nextJob.getJobID()-1);
                cpuBank.get(cpuIndex).runPCB(nextJob);
                simKernel.getPCB(simKernel.pcbQueuePointer).setStatus(PCB.RUNNING_STATE);
            } else if (nextJob.getStatus() == "Waiting") {
                if (!simKernel.ioQueue.contains(nextJob) && !simKernel.pageFaultQueue.contains(nextJob))
                    nextJob.setStatus(PCB.READY_STATE);
            } else if (nextJob.getStatus() == "Ended") {
                pageManager.cleanPageTable(nextJob);
                simKernel.pcbQueue.remove(nextJob);
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
