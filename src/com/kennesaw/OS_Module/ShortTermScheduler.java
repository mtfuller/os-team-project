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

    private void aquireLock(int index) {
        CPU temp = cpuBank.get(index);
        while(temp.isLocked());
        temp.lock();
    }

    private void releaseLock(int index) {
        cpuBank.get(index).unlock();
    }
    
    public int findCPU() {
        int index = 0;
        boolean openCpuFound = false;
        while (!openCpuFound) {
            aquireLock(index);
            openCpuFound = (!cpuBank.get(index).isRunningProcess());
            releaseLock(index);
            if (openCpuFound) {
                break;
            } else {
                ++index;
                index = index % cpuBank.size();
            }
        }
        return index;
    }
    
    public void runSTS() {
        int cpuIndex = 0;
        //CPU tempCPU = cpuBank.get(cpuIndex);
        for (int i = 0; i < simRAM.getJobsOnRam(); i++) {
            System.out.println("DEBUG | STS | Working Job #"+(i+1));
            System.out.println("DEBUG | STS | WATCH (tempCPU.isLocked): "+cpuBank.get(cpuIndex).isLocked());
            while (cpuBank.get(cpuIndex).isRunningProcess());
            System.out.println("DEBUG | STS | CPU is ready for a job");
            aquireLock(cpuIndex);
            System.out.println("DEBUG | STS | Aquired lock for CPU");
            if (simKernel.getPCB(i).getStatus() == "Waiting") {
                System.out.println("DEBUG | STS | Found \'Waiting\' Job");
                cpuBank.get(cpuIndex).runPCB(simKernel.getPCB(i));
                System.out.println("DEBUG | STS | Running Job on CPU");
                simKernel.getPCB(i).setStatus(4);
            } else {
                cpuBank.get(cpuIndex).runPCB(simKernel.getPCB(i + simRAM.getJobsOnRam()));
                simKernel.getPCB(i).setStatus(4);
            }
            releaseLock(cpuIndex);
            System.out.println("DEBUG | STS | Released lock on CPU");
        }
        simRAM.resetJobCount();
    }
    
}


