package com.kennesaw.osmodule;

/**
 * Created by Margaret on 9/16/2016.
 */

import javax.swing.*;
import java.awt.*;

import com.kennesaw.analysis.Analysis;
import com.kennesaw.memory.*;
import com.kennesaw.cpumodule.CPU;

public class OS_Driver {
    
    Disk simDisk = new Disk(512);
    Kernel simKernel = new Kernel();
    Ram simRAM = new Ram(256);
    PageManager pgMgr = new PageManager(simKernel, simRAM, simDisk);
    Loader simLoader;
    public String numberOfCPUs = "X";
    public String sortingMethod = "XXX";
    
    // Ask user how many CPUs should be created
    Frame frame = new Frame();
    
    Object[] cpuOptions = {"   1   ", "   2   ", "   4   "};
    double cpus = JOptionPane.showOptionDialog(frame,
            "How many CPUs should be created?",
            "OS Simulator",
            JOptionPane.YES_NO_CANCEL_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            cpuOptions,
            cpuOptions[0]);
    
    Object[] sortingOptions = {"FIFO", "Priority", "SJF"};
    int sorting = JOptionPane.showOptionDialog(frame,
            "How should the PCBs be sorted? ",
            "OS Simulator",
            JOptionPane.YES_NO_CANCEL_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            sortingOptions,
            sortingOptions[0]);
    
    // com.kennesaw.osmodule.Loader populates Disk with instructions from ProgramFile.txt
    public void runDriver() throws Exception {
        frame.dispose();
        
        // Call Loader to prepare Disk and Kernel
        try {
            simLoader = new Loader(simDisk, simKernel);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Create the CPUs
        if ((int)cpus == 0) {
            System.out.println("\nOne CPU was created.");
            numberOfCPUs = "1";
        } else {
            System.out.println("\n" + (int)(Math.pow(2, cpus)) + " CPUs were created.");
            numberOfCPUs = Integer.toString((int)(Math.pow(2, cpus)));
        }
        
        // Sorts the PCBs with the specified algorithm
        if (sorting == 1) {
            simKernel.sortPriority();
            sortingMethod = "Priority";
            System.out.print("PCBs were sorted based on their priority.\nJobs in order are: ");
            for (int i = 0; i < simKernel.pcbQueue.size(); i++) {
                System.out.print(simKernel.getPCB(i).getJobID()+ "   ");
                Analysis.recordNumOFJobs(simKernel.getPCB(i).getJobID());
                simKernel.getPCB(i).setIndexInAnalysis(i);
                Analysis.recordCreateTime(simKernel.getPCB(i).getIndexInAnalysis());
            }
            System.out.println();
        } else if (sorting == 2) {
            simKernel.sortSJF();
            sortingMethod = "SJF";
            System.out.print("\nPCBs were sorted based on their job length.\nJobs in order are: ");
            for (int i = 0; i < simKernel.pcbQueue.size(); i++) {
                System.out.print(simKernel.getPCB(i).getJobID()+ "   ");
                Analysis.recordNumOFJobs(simKernel.getPCB(i).getJobID());
                simKernel.getPCB(i).setIndexInAnalysis(i);
                Analysis.recordCreateTime(simKernel.getPCB(i).getIndexInAnalysis());
            }
            System.out.println();
        } else {
            sortingMethod = "FIFO";
            System.out.print("PCBs were left in FIFO order.\nJobs in order are: ");
            for (int i = 0; i < simKernel.pcbQueue.size(); i++) {
                System.out.print(simKernel.getPCB(i).getJobID()+ "   ");
                Analysis.recordNumOFJobs(simKernel.getPCB(i).getJobID());
                simKernel.getPCB(i).setIndexInAnalysis(i);
                Analysis.recordCreateTime(simKernel.getPCB(i).getIndexInAnalysis());
            }
            System.out.println();
        }
        
        simRAM.assignPageMgr(pgMgr);
        // Initialize Long-term and Short-term schedulers
        LongTermScheduler simLTS = new LongTermScheduler(simDisk, simRAM);
        ShortTermScheduler simSTS = new ShortTermScheduler(simRAM, simKernel, pgMgr, (int)(Math.pow(2, cpus)), true);
        // While there are jobs on the Disk, schedule those jobs and send them to the CPU
        simLTS.runLTS(simKernel);
        simSTS.runSTS();
        //Waits for all CPUs to finish executing
        for (CPU cpu : simSTS.cpuBank) {
            cpu.endCPU();
            cpu.join();
        }
    }
}