package com.kennesaw.OS_Module;

import memory.*;

/**
 * Created by Margaret on 9/20/2016.
 */

public class LongTermScheduler {
    
    int lineCounter;
    int pcbCounter;
    int ramSpaceCounter;
    Disk simDisk;
    Ram simRAM;
    
    //Scheduler takes a Disk and RAM as parameters
    public LongTermScheduler(Disk thisDisk, Ram thisRAM) {
        lineCounter = 0;
        pcbCounter = 0;
        simDisk = thisDisk;
        simRAM = thisRAM;
    }
    
    public void runLTS(Kernel simKernel) {
        simRAM.setOccupiedRAM(0);
        ramSpaceCounter = 0;
        for (pcbCounter = 0; pcbCounter < simKernel.getQueueSize(); pcbCounter++) {
            lineCounter = 0;
            PCB currentPCB = simKernel.getPCB(pcbCounter);
            aquireLock();
            if (currentPCB.getJobSize() <= simRAM.getFreeRAMSpace() && currentPCB.getStatus() == "New") {
                int ramOffset = simRAM.getOccupiedRAM() + lineCounter;
                currentPCB.setRAMAddressBegin(ramOffset);
                currentPCB.setBaseAddress(ramOffset);
                currentPCB.getState().setBase_addr(currentPCB.getBaseAddress());
                while (lineCounter <= simDisk.getJobSize(currentPCB)) {
                    long jobSlice = simDisk.readDisk((currentPCB.getDiskAddressBegin() + lineCounter));
                    simRAM.writeRam(ramSpaceCounter, jobSlice);
                    lineCounter++;
                    ramSpaceCounter++;
                }
                lineCounter--;
                ramOffset = simRAM.getOccupiedRAM() + lineCounter;
                currentPCB.setRAMAddressEnd(ramOffset);
                simKernel.addToReadyQueue(currentPCB);
                currentPCB.setStatus(PCB.WAITING_STATE); // Set PCB's status to "Waiting"
                simDisk.removedJobFromDisk();
                simRAM.addedJobToRam();
                simRAM.setOccupiedRAM(ramOffset + 1);
            }
            releaseLock();
        }
    }

    private void aquireLock() {
        while(simRAM.isLocked());
        simRAM.lock();
    }

    private void releaseLock() {
        simRAM.unlock();
    }
}