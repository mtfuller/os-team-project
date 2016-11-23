package com.kennesaw.OS_Module;

import com.kennesaw.Analyzer.Analysis;
import com.kennesaw.cpumodule.CPU;
import com.kennesaw.cpumodule.DmaChannel;
import com.kennesaw.cpumodule.MMU;
import memory.Disk;
import memory.Page;
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
    public void osTest1() throws Exception {
        System.out.println("Beginning OS Test...");

        Analysis.initializeDataTables();

        // Create Disk
        Disk simDisk = new Disk(512);

        // Create Ram
        Ram simRam = new Ram(256);

        // Create Kernel
        Kernel simKernel = new Kernel();

        // Create DMA Channel
        DmaChannel dmaChannel = new DmaChannel(simRam, simKernel);
        dmaChannel.start();

        // Create Page Manager
        PageManager pageManager = new PageManager(simKernel, simRam, simDisk);
        pageManager.start();

        // Create Loader
        Loader simLoader = new Loader(simDisk, simKernel);

        simRam.assignPageMgr(pageManager);
        // Create Schedulers
        LongTermScheduler simLTS = new LongTermScheduler(simDisk, simRam);
        ShortTermScheduler simSTS = new ShortTermScheduler(simRam, simKernel, 1);

        simLTS.runLTS(simKernel);
        simSTS.runSTS();

        JobSegementStructure jobSegementStructure = new JobSegementStructure();
        System.out.println(jobSegementStructure.toString());

        System.out.println("OS Test was successful!!!");
    }

//    @Test
//    public void osFifoTest() throws InterruptedException {
//        System.out.println("BEGINNING FIFO TEST WITH 4 CPUs...");
//
//        Analysis.initializeDataTables();
//
//        JobSegementStructure jobSegementStructure = new JobSegementStructure();
//
//        Disk simDisk = new Disk(2048);
//        Ram simRAM = new Ram(1024);
//        Kernel simKernel = new Kernel();
//        Loader simLoader;
//
//        try {
//            simLoader = new Loader(simDisk, simKernel);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        // Initialize Long-term and Short-term schedulers
//        LongTermScheduler simLTS = new LongTermScheduler(simDisk, simRAM);
//        ShortTermScheduler simSTS = new ShortTermScheduler(simRAM, simKernel, 4);
//
//        int pcbIndexOffset = 0;
//        int[] pcbOrder = new int[JobSegementStructure.NUMBER_OF_JOBS];
//        for (int i = 0; i < JobSegementStructure.NUMBER_OF_JOBS; i++) {
//            pcbOrder[i] = simKernel.getPCB(i).getJobID() - 1;
//        }
//
//        while (simDisk.getJobsOnDisk() > 0) {
//            simLTS.runLTS(simKernel);
//            int numOfPcbs = simRAM.getJobsOnRam();
//
//            for (int i = 0; i < numOfPcbs; i++) {
//                PCB tempPCB = simKernel.getPCB(pcbOrder[i]+pcbIndexOffset);
//                JobSegment correctData = jobSegementStructure.jobSegments[pcbOrder[i]+pcbIndexOffset];
//
//                for (int j = 0; j < tempPCB.getJobSize(); j++) {
//                    long correctValue = correctData.getRawData(j, true);
//                    long actualValue = simRAM.readRam(j + tempPCB.getRAMAddressBegin());
//                    assertEquals("Address at " + (j + tempPCB.getRAMAddressBegin()) + " on ram is: " +
//                                    actualValue +
//                                    ", but should be: " + correctValue,
//                            correctValue, actualValue);
//                }
//            }
//
//            simSTS.runSTS();
//            for (CPU cpu : simSTS.cpuBank) {
//                while (cpu.isRunningProcess());
//            }
//
//            for (int i = 0; i < numOfPcbs; i++) {
//                PCB tempPCB = simKernel.getPCB(pcbOrder[i]+pcbIndexOffset);
//                JobSegment correctData = jobSegementStructure.jobSegments[pcbOrder[i]+pcbIndexOffset];
//
//                for (int j = 0; j < tempPCB.getJobSize(); j++) {
//                    long correctValue = correctData.getRawData(j, false);
//                    long actualValue = simRAM.readRam(j + tempPCB.getRAMAddressBegin());
//                    assertEquals("Address at " + (j + tempPCB.getRAMAddressBegin()) + " on ram is: " +
//                                    actualValue +
//                                    ", but should be: " + correctValue,
//                            correctValue, actualValue);
//                }
//            }
//
//            pcbIndexOffset += numOfPcbs;
//        }
//
//        for (CPU cpu : simSTS.cpuBank) {
//            cpu.endCPU();
//            cpu.join();
//        }
//
//        System.out.println("FIFO TEST WAS SUCCESSFUL!!!");
//    }
//
//    @Test
//    public void osPriorityTest() throws InterruptedException {
//        System.out.println("BEGINNING PRIORITY TEST WITH 4 CPUs...");
//
//        Analysis.initializeDataTables();
//
//        JobSegementStructure jobSegementStructure = new JobSegementStructure();
//
//        Disk simDisk = new Disk(2048);
//        Ram simRAM = new Ram(1024);
//        Kernel simKernel = new Kernel();
//        Loader simLoader;
//
//        try {
//            simLoader = new Loader(simDisk, simKernel);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        // Initialize Long-term and Short-term schedulers
//        LongTermScheduler simLTS = new LongTermScheduler(simDisk, simRAM);
//        ShortTermScheduler simSTS = new ShortTermScheduler(simRAM, simKernel, 4);
//
//        int pcbIndexOffset = 0;
//
//        simKernel.sortPriority();
//
//        int[] pcbOrder = new int[JobSegementStructure.NUMBER_OF_JOBS];
//        for (int i = 0; i < JobSegementStructure.NUMBER_OF_JOBS; i++) {
//            pcbOrder[i] = simKernel.getPCB(i).getJobID() - 1;
//        }
//
//        while (simDisk.getJobsOnDisk() > 0) {
//            simLTS.runLTS(simKernel);
//            int numOfPcbs = simRAM.getJobsOnRam();
//
//            for (int i = 0; i < numOfPcbs; i++) {
//                PCB tempPCB = simKernel.getPCB(i+pcbIndexOffset);
//                JobSegment correctData = jobSegementStructure.jobSegments[pcbOrder[i+pcbIndexOffset]];
//
//                for (int j = 0; j < tempPCB.getJobSize(); j++) {
//                    long correctValue = correctData.getRawData(j, true);
//                    long actualValue = simRAM.readRam(j + tempPCB.getRAMAddressBegin());
//                    assertEquals("Address at " + (j + tempPCB.getRAMAddressBegin()) + " on ram is: " +
//                                    actualValue +
//                                    ", but should be: " + correctValue,
//                            correctValue, actualValue);
//                }
//            }
//
//            simSTS.runSTS();
//            for (CPU cpu : simSTS.cpuBank) {
//                while (cpu.isRunningProcess());
//            }
//
//            for (int i = 0; i < numOfPcbs; i++) {
//                PCB tempPCB = simKernel.getPCB(i+pcbIndexOffset);
//                JobSegment correctData = jobSegementStructure.jobSegments[pcbOrder[i+pcbIndexOffset]];
//
//                for (int j = 0; j < tempPCB.getJobSize(); j++) {
//                    long correctValue = correctData.getRawData(j, false);
//                    long actualValue = simRAM.readRam(j + tempPCB.getRAMAddressBegin());
//                    assertEquals("Address at " + (j + tempPCB.getRAMAddressBegin()) + " on ram is: " +
//                                    actualValue +
//                                    ", but should be: " + correctValue,
//                            correctValue, actualValue);
//                }
//            }
//
//            pcbIndexOffset += numOfPcbs;
//        }
//
//        for (CPU cpu : simSTS.cpuBank) {
//            cpu.endCPU();
//            cpu.join();
//        }
//
//        System.out.println("FIFO PRIORITY WAS SUCCESSFUL!!!");
//    }
//
//    @Test
//    public void osSjfTest() throws InterruptedException {
//        System.out.println("BEGINNING SJF TEST WITH 4 CPUs...");
//
//        Analysis.initializeDataTables();
//
//        JobSegementStructure jobSegementStructure = new JobSegementStructure();
//
//        Disk simDisk = new Disk(2048);
//        Ram simRAM = new Ram(1024);
//        Kernel simKernel = new Kernel();
//        Loader simLoader;
//
//        try {
//            simLoader = new Loader(simDisk, simKernel);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        // Initialize Long-term and Short-term schedulers
//        LongTermScheduler simLTS = new LongTermScheduler(simDisk, simRAM);
//        ShortTermScheduler simSTS = new ShortTermScheduler(simRAM, simKernel, 4);
//
//        int pcbIndexOffset = 0;
//
//        simKernel.sortSJF();
//
//        int[] pcbOrder = new int[JobSegementStructure.NUMBER_OF_JOBS];
//        for (int i = 0; i < JobSegementStructure.NUMBER_OF_JOBS; i++) {
//            pcbOrder[i] = simKernel.getPCB(i).getJobID() - 1;
//        }
//
//        while (simDisk.getJobsOnDisk() > 0) {
//            simLTS.runLTS(simKernel);
//            int numOfPcbs = simRAM.getJobsOnRam();
//
//            for (int i = 0; i < numOfPcbs; i++) {
//                PCB tempPCB = simKernel.getPCB(i+pcbIndexOffset);
//                JobSegment correctData = jobSegementStructure.jobSegments[pcbOrder[i+pcbIndexOffset]];
//
//                for (int j = 0; j < tempPCB.getJobSize(); j++) {
//                    long correctValue = correctData.getRawData(j, true);
//                    long actualValue = simRAM.readRam(j + tempPCB.getRAMAddressBegin());
//                    assertEquals("Address at " + (j + tempPCB.getRAMAddressBegin()) + " on ram is: " +
//                                    actualValue +
//                                    ", but should be: " + correctValue,
//                            correctValue, actualValue);
//                }
//            }
//
//            simSTS.runSTS();
//            for (CPU cpu : simSTS.cpuBank) {
//                while (cpu.isRunningProcess());
//            }
//
//            for (int i = 0; i < numOfPcbs; i++) {
//                PCB tempPCB = simKernel.getPCB(i+pcbIndexOffset);
//                JobSegment correctData = jobSegementStructure.jobSegments[pcbOrder[i+pcbIndexOffset]];
//
//                for (int j = 0; j < tempPCB.getJobSize(); j++) {
//                    long correctValue = correctData.getRawData(j, false);
//                    long actualValue = simRAM.readRam(j + tempPCB.getRAMAddressBegin());
//                    assertEquals("Address at " + (j + tempPCB.getRAMAddressBegin()) + " on ram is: " +
//                                    actualValue +
//                                    ", but should be: " + correctValue,
//                            correctValue, actualValue);
//                }
//            }
//
//            pcbIndexOffset += numOfPcbs;
//        }
//
//        for (CPU cpu : simSTS.cpuBank) {
//            cpu.endCPU();
//            cpu.join();
//        }
//
//        System.out.println("FIFO SJF WAS SUCCESSFUL!!!");
//    }
}