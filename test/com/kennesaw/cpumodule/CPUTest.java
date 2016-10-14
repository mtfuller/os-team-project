package com.kennesaw.cpumodule;
import memory.Ram;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Thomas on 9/23/2016.
 */
public class CPUTest {
    @Test
    public void Job1Test() {
        // Initialize CPU dependencies
        Ram simRam = new Ram(1024);
        DmaChannel dm = new DmaChannel(simRam);

        long[] jobCode = {
                0xC050005CL,
                0x4B060000L,
                0x4B010000L,
                0x4B000000L,
                0x4F0A005CL,
                0x4F0D00DCL,
                0x4C0A0004L,
                0xC0BA0000L,
                0x42BD0000L,
                0x4C0D0004L,
                0x4C060001L,
                0x10658000L,
                0x56810018L,
                0x4B060000L,
                0x4F0900DCL,
                0x43970000L,
                0x05070000L,
                0x4C060001L,
                0x4C090004L,
                0x10658000L,
                0x5681003CL,
                0xC10000ACL,
                0x92000000L,
                0x0000000AL,
                0x00000006L,
                0x0000002CL,
                0x00000045L,
                0x00000001L,
                0x00000007L,
                0x00000000L,
                0x00000001L,
                0x00000005L,
                0x0000000AL,
                0x00000055L,
                0x00000000L,
                0x00000000L,
                0x00000000L,
                0x00000000L,
                0x00000000L,
                0x00000000L,
                0x00000000L,
                0x00000000L,
                0x00000000L,
                0x00000000L,
                0x00000000L,
                0x00000000L,
                0x00000000L,
                0x00000000L,
                0x00000000L,
                0x00000000L,
                0x00000000L,
                0x00000000L,
                0x00000000L,
                0x00000000L,
                0x00000000L,
                0x00000000L,
                0x00000000L,
                0x00000000L,
                0x00000000L,
                0x00000000L,
                0x00000000L,
                0x00000000L,
                0x00000000L,
                0x00000000L,
                0x00000000L,
                0x00000000L,
                0x00000000L
        };
        int jobSize = jobCode.length;

        CPU cpu = new CPU(dm);

        for (int i = 0; i < jobSize; i++) {
            dm.writeCache(i*4, jobCode[i]);
        }

        System.out.println("CACHE: ");
        for (int i = 0; i < 0x17; i++) {
            System.out.println(dm.readCache(i));
        }

        System.out.println("\nRAM: ");
        for (int i = 0; i < 44; i++) {
            System.out.println(simRam.readRam(i));
        }

        // Create a new CPU
        cpu.start();

        cpu.startProcess();

        while(cpu.isRunningProcess());
        cpu.endCPU();

        // Check the output Job Block for Job 1
        long[] correctArr = {
                0xC050005CL,
                0x4B060000L,
                0x4B010000L,
                0x4B000000L,
                0x4F0A005CL,
                0x4F0D00DCL,
                0x4C0A0004L,
                0xC0BA0000L,
                0x42BD0000L,
                0x4C0D0004L,
                0x4C060001L,
                0x10658000L,
                0x56810018L,
                0x4B060000L,
                0x4F0900DCL,
                0x43970000L,
                0x05070000L,
                0x4C060001L,
                0x4C090004L,
                0x10658000L,
                0x5681003CL,
                0xC10000ACL,
                0x92000000L,
                0x0000000AL,
                0x00000006L,
                0x0000002CL,
                0x00000045L,
                0x00000001L,
                0x00000007L,
                0x00000000L,
                0x00000001L,
                0x00000005L,
                0x0000000AL,
                0x00000055L,
                0x00000000L,
                0x00000000L,
                0x00000000L,
                0x00000000L,
                0x00000000L,
                0x00000000L,
                0x00000000L,
                0x00000000L,
                0x00000000L,
                0x000000E4L,
                0x00000000L,
                0x00000000L,
                0x00000000L,
                0x00000000L,
                0x00000000L,
                0x00000000L,
                0x00000000L,
                0x00000000L,
                0x00000000L,
                0x00000000L,
                0x00000000L,
                0x00000006L,
                0x0000002CL,
                0x00000045L,
                0x00000001L,
                0x00000007L,
                0x00000000L,
                0x00000001L,
                0x00000005L,
                0x0000000AL,
                0x00000055L,
                0x00000000L,
                0x00000000L
        };
        for (int i = 0; i < correctArr.length; i++) {
            assertEquals("Incorrect Value at address: "+(i*4), correctArr[i], simRam.readRam(i));
        }
    }
}