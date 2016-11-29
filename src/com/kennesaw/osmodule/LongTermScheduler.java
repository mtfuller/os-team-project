package com.kennesaw.osmodule;

import com.kennesaw.analysis.Analysis;
import com.kennesaw.memory.*;

/**
 * Created by Margaret on 9/20/2016.
 */

public class LongTermScheduler {
    
    int ramPageCounter;
    Disk simDisk;
    Ram simRAM;
    
    //Scheduler takes a Disk and RAM as parameters
    public LongTermScheduler(Disk thisDisk, Ram thisRAM) {
        ramPageCounter = 0;
        simDisk = thisDisk;
        simRAM = thisRAM;
    }
    
    public void runLTS(Kernel simKernel) {
        
        // Walk through each PCB in the Kernel
        for (int i = 0; i < simKernel.getQueueSize(); i++) {
            for (int j = 0; j < 4; j++) {
                //Write the first 4 pages of each PCB from Disk to Ram
                simRAM.writeRam(ramPageCounter, simDisk.readDisk(simKernel.getPCB(i).getDiskAddressBegin() + j));

                // Update the PCB's PageTable with the corresponding RAM page numbers
                // Each entry's valid/invalid bit is automatically set to "true" when written
                simKernel.getPCB(i).getPageTable().writePageTable(j, ramPageCounter);
                ramPageCounter++;
            }
            simKernel.getPCB(i).setStatus(1);
            Analysis.recordWaitTime(simKernel.getPCB(i).getJobID());
        }
    }
}