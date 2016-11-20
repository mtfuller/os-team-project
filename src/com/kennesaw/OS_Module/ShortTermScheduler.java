package com.kennesaw.OS_Module;

/**
 * Created by Margaret on 9/20/2016.
 */

import java.util.ArrayList;

import com.kennesaw.Analyzer.Analysis;
import com.kennesaw.cpumodule.CPU;
import com.kennesaw.cpumodule.DmaChannel;
import memory.Ram;

public class ShortTermScheduler {
    Ram simRAM;
    Kernel simKernel;
    ArrayList<CPU> cpuBank = new ArrayList<>();
    private int cpuIndexPtr = 0;

    public ShortTermScheduler(Ram ram, Kernel kernel, int numCPUs) {
        simRAM = ram;
        simKernel = kernel;
//        for (int i = 0; i < numCPUs; i++) {
//            cpuBank.add(new CPU(new DmaChannel(simRAM), i));
//            cpuBank.get(i).start();
//        }
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
//        while (simKernel.hasReadyJobs()) {
//            PCB nextJob = simKernel.getJobFromReadyQueue();
//            cpuIndex = findCPU();
//            while (cpuBank.get(cpuIndex).isRunningProcess());
//            if (nextJob.getStatus() == "Waiting") {
//                Analysis.recordWaitTime(nextJob.getJobID()-1);
//                cpuBank.get(cpuIndex).runPCB(nextJob);
//                simKernel.removeFromReadyQueue(nextJob);
//            }
//        }

        // Resets the number of jobs in RAM
        simRAM.resetJobCount();

        // Spin until all cpus have finished processing
        for (CPU cpu : cpuBank) {
            while (cpu.isRunningProcess());
        }
    }
}