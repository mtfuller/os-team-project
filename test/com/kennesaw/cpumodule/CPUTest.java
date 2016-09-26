package com.kennesaw.cpumodule;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Thomas on 9/23/2016.
 */
public class CPUTest {
    @Test
    public void Job1Test() {
        DmaChannel dm = new DmaChannel();

        int[] arr = {
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

        for (int i = 0; i < arr.length; i++) {
            dm.writeRAM(i*4, arr[i]);
        }
        State state = new State();

        CPU cpu = new CPU(state, dm);

        cpu.runProcess();
    }
}