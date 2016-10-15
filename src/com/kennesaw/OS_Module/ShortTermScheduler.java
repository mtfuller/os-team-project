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
        }
    }
    
    public void runSTS() {
        for (int i = 0; i < simRAM.getJobsOnRam(); i++) {
            if (simKernel.getPCB(i).getStatus() == "Waiting") {
                cpuBank.get(0).runPCB(simKernel.getPCB(i));
                simKernel.getPCB(i).setStatus(4);
//                System.out.println("// Job number: " + simKernel.getPCB(i).getJobID());
//                for (int j = simKernel.getPCB(i).getRAMAddressBegin(); j < simKernel.getPCB(i).getRAMAddressEnd(); j++) {
//                    String converted = Long.toHexString(simRAM.readRam(j)).toUpperCase();
//                    System.out.print("0x");
//                    for (int k = 8; k > converted.length(); k--) {
//                        System.out.print("0");
//                    }
//                    System.out.println(converted);
//                }
            } else {
                cpuBank.get(0).runPCB(simKernel.getPCB(i + simRAM.getJobsOnRam()));
                simKernel.getPCB(i).setStatus(4);
//                System.out.println("// Job number: " + simKernel.getPCB(i + simRAM.getJobsOnRam()).getJobID());
//                for (int j = simKernel.getPCB(i + simRAM.getJobsOnRam()).getRAMAddressBegin(); j < simKernel.getPCB(i + simRAM.getJobsOnRam()).getRAMAddressEnd(); j++) {
//                    String converted = Long.toHexString(simRAM.readRam(j)).toUpperCase();
//                    System.out.print("0x");
//                    for (int k = 8; k > converted.length(); k--) {
//                        System.out.print("0");
//                    }
//                    System.out.println(converted);
//                }
            }
        }
        simRAM.resetJobCount();
    }
    
}


