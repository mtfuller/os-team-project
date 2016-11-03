package com.kennesaw.OS_Module;

/**
 * Created by Margaret on 9/20/2016.
 */

import com.kennesaw.Analyzer.Analysis;
import com.kennesaw.cpumodule.CPU;
import com.kennesaw.cpumodule.DmaChannel;
import memory.Ram;

import java.util.ArrayList;

public class ShortTermScheduler {
    
    Ram simRAM;
    Kernel simKernel;
    ArrayList<CPU> cpuBank = new ArrayList<>();
    private int cpuIndexPtr = 0;
    
    public ShortTermScheduler(Ram ram, Kernel kernel, int numCPUs) {
        simRAM = ram;
        simKernel = kernel;
        for (int i = 0; i < numCPUs; i++) {
            cpuBank.add(new CPU(new DmaChannel(simRAM), i));
            cpuBank.get(i).start();
        }
        for(int i = 0; i < 30; i++) {
            double used = (double)simKernel.getPCB(i).getJobSize() / 1024.00;
            Analysis.recordRamSpace(i, used);
        }
    }
    
    public int findCPU() {
        boolean openCpuFound = false;
        while (!openCpuFound) {
            openCpuFound = (!cpuBank.get(cpuIndexPtr).isRunningProcess());
            if (openCpuFound) {
                break;
            } else {
                ++cpuIndexPtr;
                cpuIndexPtr = cpuIndexPtr % cpuBank.size();
            }
        }
        return cpuIndexPtr;
    }
    
    public void runSTS() {
        int cpuIndex = 0;
        while(simRAM.isLocked());
        simRAM.lock();
        int size = simRAM.getJobsOnRam();
        simRAM.unlock();
        for (int i = 0; i < simRAM.getJobsOnRam(); i++) {
            cpuIndex = findCPU();
            while (cpuBank.get(cpuIndex).isRunningProcess());
            if (simKernel.getPCB(i).getStatus() == "Waiting") {
                Analysis.recordWaitTime(simKernel.getPCB(i).getJobID()-1);
                cpuBank.get(cpuIndex).runPCB(simKernel.getPCB(i));
                Analysis.recordCompleteTime(simKernel.getPCB(i).getJobID()-1);
                simKernel.getPCB(i).setStatus(4);
            } else {
                while(simRAM.isLocked());
                simRAM.lock();
                Analysis.recordWaitTime(simKernel.getPCB(i + simRAM.getJobsOnRam()).getJobID()-1);
                cpuBank.get(cpuIndex).runPCB(simKernel.getPCB(i + simRAM.getJobsOnRam()));
                Analysis.recordCompleteTime(simKernel.getPCB(i + simRAM.getJobsOnRam()).getJobID()-1);
                simRAM.unlock();
                simKernel.getPCB(i).setStatus(4);
            }
        }
        while(simRAM.isLocked());
        simRAM.lock();
        simRAM.resetJobCount();
        simRAM.unlock();
        for (CPU cpu : cpuBank) {
            while (cpu.isRunningProcess());
        }
    }
    
}