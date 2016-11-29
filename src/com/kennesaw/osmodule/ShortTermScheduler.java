package com.kennesaw.osmodule;

/**
 * Created by Margaret on 9/20/2016.
 */

import java.util.ArrayList;

import com.kennesaw.analysis.Analysis;
import com.kennesaw.cpumodule.CPU;
import com.kennesaw.cpumodule.DmaChannel;
import com.kennesaw.cpumodule.MMU;
import com.kennesaw.memory.Ram;
import com.kennesaw.util.DebuggableModule;

public class ShortTermScheduler extends DebuggableModule {
    MMU simMMU;
    Ram simRAM;
    Kernel simKernel;
    PageManager pageManager;
    DmaChannel dmaChannel;
    ArrayList<CPU> cpuBank = new ArrayList<>();
    private int cpuIndexPtr = 0;
    
    public ShortTermScheduler(Ram ram, Kernel kernel, PageManager pageMan, int numCPUs, boolean isDebug) {
        simRAM = ram;
        simKernel = kernel;
        dmaChannel = new DmaChannel(simRAM, simKernel);
        dmaChannel.start();
        pageManager = pageMan;
        pageManager.start();
        simMMU = new MMU(simKernel, simRAM, isDebug);
        for (int i = 0; i < numCPUs; i++) {
            cpuBank.add(new CPU(i, simMMU, isDebug));
            cpuBank.get(i).start();
        }
        setModuleName("STS");
        setDebugMode(isDebug);
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
            if (nextJob != null) {
                cpuIndex = findCPU();
                while (cpuBank.get(cpuIndex).isRunningProcess()) ;
                synchronized (simKernel) {
                    if (nextJob.getStatus() == "Ready") {
                        cpuBank.get(cpuIndex).runPCB(nextJob);
                        Analysis.recordCPUSpinningStart(nextJob.getIndexInAnalysis());
                        nextJob.setStatus(PCB.RUNNING_STATE);
                    } else if (nextJob.getStatus() == "Waiting") {
                        if (!simKernel.ioQueue.contains(nextJob) && !simKernel.pageFaultQueue.contains(nextJob))
                            nextJob.setStatus(PCB.READY_STATE);
                    } else if (nextJob.getStatus() == "Ended") {
                        Analysis.recordRamSpace(nextJob.getIndexInAnalysis(), nextJob.getPageTable().getNumberOfPages());
                        Analysis.recordCompleteTime(nextJob.getIndexInAnalysis());
                        pageManager.cleanPageTable(nextJob);
                        simKernel.pcbQueue.remove(nextJob);
                    }
                }
            }
        }
    
        // Resets the number of jobs in RAM
        simRAM.resetJobCount();
    
        // Spin until all cpus have finished processing
        for (CPU cpu : cpuBank) {
            while (cpu.isRunningProcess());
        }

        // Ending PageManager and DMA Channel
        pageManager.endPageManager();
        dmaChannel.endDMA();
        try {
            pageManager.join();
            dmaChannel.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
