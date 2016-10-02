/**
 * Created by Margaret on 9/20/2016.
 */

public class LongTermScheduler {
    
    int lineCounter;
    int pcbCounter;
    Kernel simKernel;
    Disk simDisk;
    Ram simRAM;
    
    //Scheduler takes a Disk and RAM as parameters
    public LongTermScheduler(Disk thisDisk, Ram thisRAM) {
        lineCounter = 0;
        pcbCounter = 0;
        simKernel = new Kernel();
        simDisk = thisDisk;
        simRAM = thisRAM;
    }
    
    public void runLTS(Kernel simKernel) {
        simRAM.setOccupiedRAM(0);
        for (pcbCounter = 0; pcbCounter < simKernel.getQueueSize(); pcbCounter++) {
            lineCounter = 0;
            simKernel.getPCB(pcbCounter).setRAMAddressBegin(simRAM.getOccupiedRAM() + lineCounter);
            // Only PCBs with a "New" status are written to RAM. Otherwise, they've been written previously.
            if (simDisk.getJobSize(simKernel.getPCB(pcbCounter)) <= simRAM.getFreeRAMSpace() && (simKernel.getPCB(pcbCounter)).getStatus() == "New") {
                while (lineCounter < simDisk.getJobSize(simKernel.getPCB(pcbCounter))) {
                    long jobSlice = simDisk.getJobChunk(simKernel.getPCB(pcbCounter))[lineCounter];
                    simRAM.writeToRAM(jobSlice, lineCounter);
                    lineCounter++;
                }
                simKernel.getPCB(pcbCounter).setRAMAddressEnd(simRAM.getOccupiedRAM() + lineCounter);
                simKernel.getPCB(pcbCounter).setStatus(2); // Set PCB's status to "Waiting"
                simRAM.setOccupiedRAM(simRAM.getOccupiedRAM() + lineCounter + 1);
            }
        }
    }
}













