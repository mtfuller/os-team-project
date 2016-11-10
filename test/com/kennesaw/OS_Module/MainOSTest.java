package com.kennesaw.OS_Module;

import com.kennesaw.Analyzer.Analysis;
import com.kennesaw.cpumodule.CPU;
import com.kennesaw.cpumodule.DmaChannel;
import memory.Disk;
import memory.Ram;
import org.junit.Test;
import test.kennesaw.ramdump.JobSegementStructure;
import test.kennesaw.ramdump.JobSegment;

import static org.junit.Assert.*;

/**
 * Created by Thomas on 10/9/2016.
 */
public class MainOSTest {
    @Test
    public void osDriverTest() throws InterruptedException {
        Analysis.initializeDataTables();

        JobSegementStructure jobSegementStructure = new JobSegementStructure();

        Disk simDisk = new Disk(2048);
        Ram simRAM = new Ram(1024);
        Kernel simKernel = new Kernel();
        Loader simLoader;

        int pcbIndex = 0;

        try {
            simLoader = new Loader(simDisk, simKernel);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Initialize Long-term and Short-term schedulers
        LongTermScheduler simLTS = new LongTermScheduler(simDisk, simRAM);
        ShortTermScheduler simSTS = new ShortTermScheduler(simRAM, simKernel, 1);

        for (PCB e : simKernel.pcbQueue)
            System.out.print(e.getJobID() + ": (RAM: " + e.getRAMAddressBegin() + "), ");
        System.out.println();

        int pcbIndexOffset = 0;

        while (simDisk.getJobsOnDisk() > 0) {
            System.out.println("JOBS ON DISk: "+simDisk.getJobsOnDisk());
            simLTS.runLTS(simKernel);
            int numOfPcbs = simRAM.getJobsOnRam();

            for (int i = 0; i < numOfPcbs; i++) {
                PCB tempPCB = simKernel.getPCB(i+pcbIndexOffset);
                JobSegment correctData = jobSegementStructure.jobSegments[i+pcbIndexOffset];

                for (int j = 0; j < tempPCB.getJobSize(); j++) {
                    long correctValue = correctData.getRawData(j, true);
                    long actualValue = simRAM.readRam(j + tempPCB.getRAMAddressBegin());
                    assertEquals("Address at " + (j + tempPCB.getRAMAddressBegin()) + " on ram is: " +
                                    actualValue +
                                    ", but should be: " + correctValue,
                            correctValue, actualValue);
                }
            }

            simSTS.runSTS();
            for (CPU cpu : simSTS.cpuBank) {
                while (cpu.isRunningProcess());
            }

            for (int i = 0; i < numOfPcbs; i++) {
                PCB tempPCB = simKernel.getPCB(i+pcbIndexOffset);
                JobSegment correctData = jobSegementStructure.jobSegments[i+pcbIndexOffset];

                for (int j = 0; j < tempPCB.getJobSize(); j++) {
                    long correctValue = correctData.getRawData(j, false);
                    long actualValue = simRAM.readRam(j + tempPCB.getRAMAddressBegin());
                    assertEquals("Address at " + (j + tempPCB.getRAMAddressBegin()) + " on ram is: " +
                                    actualValue +
                                    ", but should be: " + correctValue,
                            correctValue, actualValue);
                }
            }

            pcbIndexOffset += numOfPcbs;
        }

        for (CPU cpu : simSTS.cpuBank) {
            cpu.endCPU();
        }

        for (CPU cpu : simSTS.cpuBank) {
            cpu.join();
        }
    }

    @Test
    public void osFifoTest() throws InterruptedException {

    }

    @Test
    public void osPriorityTest() {

    }

    @Test
    public void osSjfTest() {

    }
}