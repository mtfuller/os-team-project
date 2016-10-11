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
        CpuState state = new CpuState();

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
        int size = arr.length;
        for (int j = 0; j < 4; j++) {
            for (int i = 0; i < size; i++) {
                simRam.writeRam(4*(i+(j*size)), arr[i]);
            }
        }

        for (int i = 0; i < 1024; i++) {
            System.out.println(simRam.readRam(i));
        }

        // Create a new CPU
        CPU cpu = new CPU(simRam);

        // Run Job 1 through CPU
        cpu.runProcess();
        System.out.println(dm.toString());

        // Check the output Job Block for Job 1
        int[] correctArr = {
                0xC050005C,
                0x4B060000,
                0x4B010000,
                0x4B000000,
                0x4F0A005C,
                0x4F0D00DC,
                0x4C0A0004,
                0xC0BA0000,
                0x42BD0000,
                0x4C0D0004,
                0x4C060001,
                0x10658000,
                0x56810018,
                0x4B060000,
                0x4F0900DC,
                0x43970000,
                0x05070000,
                0x4C060001,
                0x4C090004,
                0x10658000,
                0x5681003C,
                0xC10000AC,
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
                0x000000E4,
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
                0x00000000
        };
        for (int i = 0; i < correctArr.length; i++) {
            assertEquals("Incorrect Value at address: "+(i*4), correctArr[i], simRam.readRam(i*4));
        }
    }
}