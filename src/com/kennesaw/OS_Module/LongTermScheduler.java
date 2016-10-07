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
            // Only PCBs with a "New" status are written to RAM. Otherwise, they've been written previously.
            if ((simKernel.getPCB(pcbCounter).getJobSize() <= simRAM.getFreeRAMSpace()) && (simKernel.getPCB(pcbCounter).getStatus() == "New")) {
                simKernel.getPCB(pcbCounter).setRAMAddressBegin(simRAM.getOccupiedRAM() + lineCounter);
                simKernel.getPCB(pcbCounter).setBaseAddress(simRAM.getOccupiedRAM() + lineCounter);
                while (lineCounter < simDisk.getJobSize(simKernel.getPCB(pcbCounter))) {
                    long jobSlice = simDisk.readDisk((simKernel.getPCB(pcbCounter).getDiskAddressBegin() + lineCounter));
                    simRAM.writeRam(ramSpaceCounter, jobSlice);
                    lineCounter++;
                    ramSpaceCounter++;
                }
                simKernel.getPCB(pcbCounter).setRAMAddressEnd(simRAM.getOccupiedRAM() + lineCounter);
                simKernel.getPCB(pcbCounter).setStatus(2); // Set PCB's status to "Waiting"
                simDisk.removedJobFromDisk();
                simRAM.addedJobToRam();
                simRAM.setOccupiedRAM(simRAM.getOccupiedRAM() + lineCounter + 1);
            }
        }
    }
}