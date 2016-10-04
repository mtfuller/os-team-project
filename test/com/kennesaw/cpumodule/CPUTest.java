package com.kennesaw.cpumodule;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Thomas on 9/23/2016.
 */
public class CPUTest {
    @Test
    public void Job1Test() {
        // Initialize CPU dependencies
        DmaChannel dm = new DmaChannel();
        State state = new State();

        // Load Initial Job Block
        long[] arr = {
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
        for (int i = 0; i < arr.length; i++) {
            dm.writeRAM(i*4, arr[i]);
        }

        // Create a new CPU
        CPU cpu = new CPU(state, dm);

        // Run Job 1 through CPU
        cpu.runProcess();

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
            assertEquals("Incorrect Value at address: "+(i*4), correctArr[i], dm.readRAM(i*4));
        }
    }

    @Test
    public void Job2Test() {
        // Initialize CPU dependencies
        DmaChannel dm = new DmaChannel();
        State state = new State();

        // Load Initial Job Block
        long[] arr = {
            0xC0500070L,
            0x4B060000L,
            0x4B010000L,
            0x4B000000L,
            0x4F0A0070L,
            0x4F0D00F0L,
            0x4C0A0004L,
            0xC0BA0000L,
            0x42BD0000L,
            0x4C0D0004L,
            0x4C060001L,
            0x10658000L,
            0x56810018L,
            0x4B060000L,
            0x4F0900F0L,
            0x43900000L,
            0x4C060001L,
            0x4C090004L,
            0x43920000L,
            0x4C060001L,
            0x4C090004L,
            0x10028000L,
            0x55810060L,
            0x04020000L,
            0x10658000L,
            0x56810048L,
            0xC10000C0L,
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
        for (int i = 0; i < arr.length; i++) {
            dm.writeRAM(i*4, arr[i]);
        }

        // Create a new CPU
        CPU cpu = new CPU(state, dm);

        // Run Job 2 through CPU
        cpu.runProcess();

        // Check the output Job Block for Job 1
        long[] correctArr = {
                0xC0500070L,
                0x4B060000L,
                0x4B010000L,
                0x4B000000L,
                0x4F0A0070L,
                0x4F0D00F0L,
                0x4C0A0004L,
                0xC0BA0000L,
                0x42BD0000L,
                0x4C0D0004L,
                0x4C060001L,
                0x10658000L,
                0x56810018L,
                0x4B060000L,
                0x4F0900F0L,
                0x43900000L,
                0x4C060001L,
                0x4C090004L,
                0x43920000L,
                0x4C060001L,
                0x4C090004L,
                0x10028000L,
                0x55810060L,
                0x04020000L,
                0x10658000L,
                0x56810048L,
                0xC10000C0L,
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
                0x00000055L,// OUTPUT
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
                0x00000006L, // TEMP
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
            assertEquals("Incorrect Value at address: "+(i*4), correctArr[i], dm.readRAM(i*4));
        }
    }

    @Test
    public void Job3Test() {
        // Initialize CPU dependencies
        DmaChannel dm = new DmaChannel();
        State state = new State();

        // Load Initial Job Block
        long[] arr = {
                0xC0500060L,
                0x4B060000L,
                0x4B010000L,
                0x4B000000L,
                0x4F0A0060L,
                0x4F0D00E0L,
                0x4C0A0004L,
                0xC0BA0000L,
                0x42BD0000L,
                0x4C0D0004L,
                0x4C060001L,
                0x10658000L,
                0x56810018L,
                0x4B060000L,
                0x4F0900E0L,
                0x43970000L,
                0x05070000L,
                0x4C060001L,
                0x4C090004L,
                0x10658000L,
                0x5681003CL,
                0x08050000L,
                0xC10000B0L,
                0x92000000L,
                0x0000000AL,
                0x00000006L,
                0x0000002CL,
                0x00000045L,
                0x00000001L,
                0x00000009L,
                0x000000B0L,
                0x00000001L,
                0x00000007L,
                0x000000AAL,
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
        for (int i = 0; i < arr.length; i++) {
            dm.writeRAM(i*4, arr[i]);
        }

        // Create a new CPU
        CPU cpu = new CPU(state, dm);

        // Run Job 3 through CPU
        cpu.runProcess();

        // Check the output Job Block for Job 1
        long[] correctArr = {
                0xC0500060L,
                0x4B060000L,
                0x4B010000L,
                0x4B000000L,
                0x4F0A0060L,
                0x4F0D00E0L,
                0x4C0A0004L,
                0xC0BA0000L,
                0x42BD0000L,
                0x4C0D0004L,
                0x4C060001L,
                0x10658000L,
                0x56810018L,
                0x4B060000L,
                0x4F0900E0L,
                0x43970000L,
                0x05070000L,
                0x4C060001L,
                0x4C090004L,
                0x10658000L,
                0x5681003CL,
                0x08050000L,
                0xC10000B0L,
                0x92000000L,
                0x0000000AL,
                0x00000006L,
                0x0000002CL,
                0x00000045L,
                0x00000001L,
                0x00000009L,
                0x000000B0L,
                0x00000001L,
                0x00000007L,
                0x000000AAL,
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
                0x00000038L,// OUTPUT
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
                0x00000006L,// TEMP
                0x0000002CL,
                0x00000045L,
                0x00000001L,
                0x00000009L,
                0x000000B0L,
                0x00000001L,
                0x00000007L,
                0x000000AAL,
                0x00000055L,
                0x00000000L,
                0x00000000L
        };
        for (int i = 0; i < correctArr.length; i++) {
            assertEquals("Incorrect Value at address: "+(i*4), correctArr[i], dm.readRAM(i*4));
        }
    }

    @Test
    public void Job4Test() {
        // Initialize CPU dependencies
        DmaChannel dm = new DmaChannel();
        State state = new State();

        // Load Initial Job Block
        long[] arr = {
                0xC050004CL,
                0x4B060000L,
                0x4B000000L,
                0x4B010000L,
                0x4B020000L,
                0x4B030001L,
                0x4F07009CL,
                0xC1270000L,
                0x4C070004L,
                0x4C060001L,
                0x05320000L,
                0xC1070000L,
                0x4C070004L,
                0x4C060001L,
                0x04230000L,
                0x04300000L,
                0x10658000L,
                0x56810028L,
                0x92000000L,
                0x0000000BL,
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
        for (int i = 0; i < arr.length; i++) {
            dm.writeRAM(i*4, arr[i]);
        }

        // Create a new CPU
        CPU cpu = new CPU(state, dm);

        // Run Job 4 through CPU
        cpu.runProcess();

        // Check the output Job Block for Job 1
        long[] correctArr = {
                0xC050004CL,
                0x4B060000L,
                0x4B000000L,
                0x4B010000L,
                0x4B020000L,
                0x4B030001L,
                0x4F07009CL,
                0xC1270000L,
                0x4C070004L,
                0x4C060001L,
                0x05320000L,
                0xC1070000L,
                0x4C070004L,
                0x4C060001L,
                0x04230000L,
                0x04300000L,
                0x10658000L,
                0x56810028L,
                0x92000000L,
                0x0000000BL,//Input
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
                0x00000000L,// OUTPUT
                0x00000001L,
                0x00000002L,
                0x00000003L,
                0x00000005L,
                0x00000008L,
                0x0000000DL,
                0x00000015L,
                0x00000022L,
                0x00000037L,
                0x00000059L,
                0x00000000L,
                0x00000000L,// TEMP
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
        for (int i = 0; i < correctArr.length; i++) {
            assertEquals("Incorrect Value at address: "+(i*4), correctArr[i], dm.readRAM(i*4));
        }

    }
}