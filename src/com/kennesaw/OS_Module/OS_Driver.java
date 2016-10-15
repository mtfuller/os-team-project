package com.kennesaw.OS_Module;

/**
 * Created by Margaret on 9/16/2016.
 */

import memory.*;
import com.kennesaw.cpumodule.CPU;
import com.kennesaw.cpumodule.DmaChannel;

import javax.swing.*;
import java.awt.*;
import java.util.Scanner;

public class OS_Driver {
    
    Disk simDisk = new Disk(2048);
    Ram simRAM = new Ram(1024);
    Kernel simKernel = new Kernel();
    Loader simLoader;
    
    // Ask user how many CPUs should be created
    Frame frame = new Frame();
    
    Object[] cpuOptions = {"   1   ", "   2   ", "   4   ", "   8   "};
    double cpus = JOptionPane.showOptionDialog(frame,
            "How many CPUs should be created?",
            "OS Simulator",
            JOptionPane.YES_NO_CANCEL_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            cpuOptions,
            cpuOptions[0]);
//
    Object[] sortingOptions = {"FIFO", "Priority"};
    int sorting = JOptionPane.showOptionDialog(frame,
            "How should the PCBs be sorted? ",
            "OS Simulator",
            JOptionPane.YES_NO_CANCEL_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            sortingOptions,
            sortingOptions[0]);
    
    // com.kennesaw.OS_Module.Loader populates Disk with instructions from ProgramFile.txt
    public void runDriver() throws Exception {
        frame.dispose();
        try {
            simLoader = new Loader(simDisk, simKernel);
        } catch (Exception e) {
            e.printStackTrace();
        }
    
        if ((int)cpus == 0) {
            System.out.println("\nOne CPU was created.");
        } else {
            System.out.println("\n" + (int)(Math.pow(2, cpus)) + " CPUs were created.");
        }
        
        if (sorting == 1) {
            simKernel.sortPriority();
            System.out.println("PCBs were sorted based on their priority.\n");
//        } else if (sorting == 2) {
//            simKernel.sortSJF();
//            System.out.println("\nPCBs were sorted based on their job length.\n");
        } else {
            System.out.println("PCBs were left in FIFO order.\n");
        }
        
        // Initialize Long-term and Short-term schedulers
        LongTermScheduler simLTS = new LongTermScheduler(simDisk, simRAM);
        ShortTermScheduler simSTS = new ShortTermScheduler(simRAM, simKernel, (int)(Math.pow(2, cpus)));
        
        // While there are jobs on the Disk, schedule those jobs and send them to the CPU
        while (simDisk.getJobsOnDisk() > 0) {
            simLTS.runLTS(simKernel);
            simSTS.runSTS();
        }
    }
}