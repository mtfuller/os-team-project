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
        for (int i = 0; i < jobSize; i++) {
            System.out.println(dm.readCache(i*4));
        }

        // Create a new CPU
        cpu.start();

        cpu.runCacheOnlyCPU();

        try {
            Thread.sleep(1200);
            System.out.println("\nEnding CPU...\n\nFINAL MEMORY DUMP:");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

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
            assertEquals("Incorrect Value at address: "+(i*4), correctArr[i], dm.readCache(i*4));
        }

        System.out.println("CACHE: ");
        for (int i = 0; i < jobSize; i++) {
            System.out.println(dm.readCache(i*4));
        }
    }

    @Test
    public void Job2Test() {
        // Initialize CPU dependencies
        Ram simRam = new Ram(1024);
        DmaChannel dm = new DmaChannel(simRam);

        long[] jobCode = {
            0xC0500070,
            0x4B060000,
            0x4B010000,
            0x4B000000,
            0x4F0A0070,
            0x4F0D00F0,
            0x4C0A0004,
            0xC0BA0000,
            0x42BD0000,
            0x4C0D0004,
            0x4C060001,
            0x10658000,
            0x56810018,
            0x4B060000,
            0x4F0900F0,
            0x43900000,
            0x4C060001,
            0x4C090004,
            0x43920000,
            0x4C060001,
            0x4C090004,
            0x10028000,
            0x55810060,
            0x04020000,
            0x10658000,
            0x56810048,
            0xC10000C0,
            0x92000000,
            0x0000000A,
            0x00000006,
            0x0000002C,
            0x00000045,
            0x00000001,
            0x00000007,
            0x00000000,
            0x00000001,
            0x00000005,
            0x0000000A,
            0x00000055,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000
        };
        int jobSize = jobCode.length;

        CPU cpu = new CPU(dm);

        for (int i = 0; i < jobSize; i++) {
            dm.writeCache(i*4, jobCode[i]);
        }

        System.out.println("CACHE: ");
        for (int i = 0; i < jobSize; i++) {
            System.out.println(dm.readCache(i*4));
        }

        // Create a new CPU
        cpu.start();

        cpu.runCacheOnlyCPU();

        try {
            Thread.sleep(1200);
            System.out.println("\nEnding CPU...\n\nFINAL MEMORY DUMP:");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        cpu.endCPU();

        // Check the output Job Block for Job 1
        long[] correctArr = {
            0xC0500070,
            0x4B060000,
            0x4B010000,
            0x4B000000,
            0x4F0A0070,
            0x4F0D00F0,
            0x4C0A0004,
            0xC0BA0000,
            0x42BD0000,
            0x4C0D0004,
            0x4C060001,
            0x10658000,
            0x56810018,
            0x4B060000,
            0x4F0900F0,
            0x43900000,
            0x4C060001,
            0x4C090004,
            0x43920000,
            0x4C060001,
            0x4C090004,
            0x10028000,
            0x55810060,
            0x04020000,
            0x10658000,
            0x56810048,
            0xC10000C0,
            0x92000000,
            0x0000000A,
            0x00000006,
            0x0000002C,
            0x00000045,
            0x00000001,
            0x00000007,
            0x00000000,
            0x00000001,
            0x00000005,
            0x0000000A,
            0x00000055,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000055,// OUTPUT
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000006, // TEMP
            0x0000002C,
            0x00000045,
            0x00000001,
            0x00000007,
            0x00000000,
            0x00000001,
            0x00000005,
            0x0000000A,
            0x00000055,
            0x00000000,
            0x00000000
        };
        for (int i = 0; i < correctArr.length; i++) {
            assertEquals("Incorrect Value at address: "+(i*4), correctArr[i], dm.readCache(i*4));
        }

        System.out.println("CACHE: ");
        for (int i = 0; i < jobSize; i++) {
            System.out.println(dm.readCache(i*4));
        }
    }

    @Test
    public void Job3Test() {
        // Initialize CPU dependencies
        Ram simRam = new Ram(1024);
        DmaChannel dm = new DmaChannel(simRam);

        long[] jobCode = {
            0xC0500060,
            0x4B060000,
            0x4B010000,
            0x4B000000,
            0x4F0A0060,
            0x4F0D00E0,
            0x4C0A0004,
            0xC0BA0000,
            0x42BD0000,
            0x4C0D0004,
            0x4C060001,
            0x10658000,
            0x56810018,
            0x4B060000,
            0x4F0900E0,
            0x43970000,
            0x05070000,
            0x4C060001,
            0x4C090004,
            0x10658000,
            0x5681003C,
            0x08050000,
            0xC10000B0,
            0x92000000,
            0x0000000A,
            0x00000006,
            0x0000002C,
            0x00000045,
            0x00000001,
            0x00000009,
            0x000000B0,
            0x00000001,
            0x00000007,
            0x000000AA,
            0x00000055,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000
        };
        int jobSize = jobCode.length;

        CPU cpu = new CPU(dm);

        for (int i = 0; i < jobSize; i++) {
            dm.writeCache(i*4, jobCode[i]);
        }

        System.out.println("CACHE: ");
        for (int i = 0; i < jobSize; i++) {
            System.out.println(dm.readCache(i*4));
        }

        // Create a new CPU
        cpu.start();

        cpu.runCacheOnlyCPU();

        try {
            Thread.sleep(1200);
            System.out.println("\nEnding CPU...\n\nFINAL MEMORY DUMP:");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        cpu.endCPU();

        // Check the output Job Block for Job 1
        long[] correctArr = {
            0xC0500060,
            0x4B060000,
            0x4B010000,
            0x4B000000,
            0x4F0A0060,
            0x4F0D00E0,
            0x4C0A0004,
            0xC0BA0000,
            0x42BD0000,
            0x4C0D0004,
            0x4C060001,
            0x10658000,
            0x56810018,
            0x4B060000,
            0x4F0900E0,
            0x43970000,
            0x05070000,
            0x4C060001,
            0x4C090004,
            0x10658000,
            0x5681003C,
            0x08050000,
            0xC10000B0,
            0x92000000,
            0x0000000A,
            0x00000006,
            0x0000002C,
            0x00000045,
            0x00000001,
            0x00000009,
            0x000000B0,
            0x00000001,
            0x00000007,
            0x000000AA,
            0x00000055,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000038,// OUTPUT
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000006,// TEMP
            0x0000002C,
            0x00000045,
            0x00000001,
            0x00000009,
            0x000000B0,
            0x00000001,
            0x00000007,
            0x000000AA,
            0x00000055,
            0x00000000,
            0x00000000
        };
        for (int i = 0; i < correctArr.length; i++) {
            assertEquals("Incorrect Value at address: "+(i*4), correctArr[i], dm.readCache(i*4));
        }

        System.out.println("CACHE: ");
        for (int i = 0; i < jobSize; i++) {
            System.out.println(dm.readCache(i*4));
        }
    }

    @Test
    public void Job4Test() {
        // Initialize CPU dependencies
        Ram simRam = new Ram(1024);
        DmaChannel dm = new DmaChannel(simRam);

        long[] jobCode = {
            0xC050004C,
            0x4B060000,
            0x4B000000,
            0x4B010000,
            0x4B020000,
            0x4B030001,
            0x4F07009C,
            0xC1270000,
            0x4C070004,
            0x4C060001,
            0x05320000,
            0xC1070000,
            0x4C070004,
            0x4C060001,
            0x04230000,
            0x04300000,
            0x10658000,
            0x56810028,
            0x92000000,
            0x0000000B,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000
        };
        int jobSize = jobCode.length;

        CPU cpu = new CPU(dm);

        for (int i = 0; i < jobSize; i++) {
            dm.writeCache(i*4, jobCode[i]);
        }

        System.out.println("CACHE: ");
        for (int i = 0; i < jobSize; i++) {
            System.out.println(dm.readCache(i*4));
        }

        // Create a new CPU
        cpu.start();

        cpu.runCacheOnlyCPU();

        try {
            Thread.sleep(1200);
            System.out.println("\nEnding CPU...\n\nFINAL MEMORY DUMP:");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        cpu.endCPU();

        // Check the output Job Block for Job 1
        long[] correctArr = {
            0xC050004C,
            0x4B060000,
            0x4B000000,
            0x4B010000,
            0x4B020000,
            0x4B030001,
            0x4F07009C,
            0xC1270000,
            0x4C070004,
            0x4C060001,
            0x05320000,
            0xC1070000,
            0x4C070004,
            0x4C060001,
            0x04230000,
            0x04300000,
            0x10658000,
            0x56810028,
            0x92000000,
            0x0000000B,//Input
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,// OUTPUT
            0x00000001,
            0x00000002,
            0x00000003,
            0x00000005,
            0x00000008,
            0x0000000D,
            0x00000015,
            0x00000022,
            0x00000037,
            0x00000059,
            0x00000000,
            0x00000000,// TEMP
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000,
            0x00000000
        };
        for (int i = 0; i < correctArr.length; i++) {
            assertEquals("Incorrect Value at address: "+(i*4), correctArr[i], dm.readCache(i*4));
        }

        System.out.println("CACHE: ");
        for (int i = 0; i < jobSize; i++) {
            System.out.println(dm.readCache(i*4));
        }
    }
}