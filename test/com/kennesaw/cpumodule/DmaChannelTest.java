package com.kennesaw.cpumodule;

import memory.Ram;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Thomas on 10/15/2016.
 */
public class DmaChannelTest {
    @Test
    public void singleDmaTest() {
        Ram ram = new Ram(20);

        long[] ramContents = {
            1L,
            2L,
            3L,
            4L,
            5L,
            6L,
            7L,
            8L,
            9L,
            10L,
            11L,
            12L,
            13L,
            14L,
            15L,
            16L,
            17L,
            18L,
            19L,
            20L,
        };

        for (int i = 0; i < ramContents.length; i++) {
            ram.writeRam(i, ramContents[i]);
        }

        DmaChannel mainDma = new DmaChannel(ram);

        Thread t = new Thread() {
            public DmaChannel dma = mainDma;

            @Override
            public void run() {
                for (int i = 0; i < ramContents.length; i++) {
                    long expected = ramContents[i];
                    long actual = dma.readRAM(i*4);
                    assertEquals("After reading from RAM, we should have found "+expected+" at address "+(i*4)+", but instead" +
                            " we got "+actual, expected, actual);
                }

                for (int i = 0; i < ramContents.length; i++) {
                    long val = dma.readRAM(i*4);
                    val *= 2;
                    dma.writeRAM(i*4, val);
                }
            }
        };

        t.start();

        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long[] ramResults = {
                2L,
                4L,
                6L,
                8L,
                10L,
                12L,
                14L,
                16L,
                18L,
                20L,
                22L,
                24L,
                26L,
                28L,
                30L,
                32L,
                34L,
                36L,
                38L,
                40L,
        };

        Thread newThread = new Thread() {
            public DmaChannel dma = mainDma;

            @Override
            public void run() {
                for (int i = 0; i < ramResults.length; i++) {
                    long expected = ramResults[i];
                    long actual = dma.readRAM(i*4);
                    assertEquals("After multiplying every entry in RAM by 2, we should have found "+expected+" at address " +
                            (i*4)+", but instead we got "+actual, expected, actual);
                }
            }
        };

        newThread.start();

        try {
            newThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void multiDmaTest() {
        Ram ram = new Ram(20);

        long[] ramContents = {
                1L,
                2L,
                3L,
                4L,
                5L,
                6L,
                7L,
                8L,
                9L,
                10L,
                11L,
                12L,
                13L,
                14L,
                15L,
                16L,
                17L,
                18L,
                19L,
                20L,
        };

        for (int i = 0; i < ramContents.length; i++) {
            ram.writeRam(i, ramContents[i]);
        }

        //Create 1st DMA
        DmaChannel dma1 = new DmaChannel(ram);
        Thread t1 = new Thread() {
            private static final String NAME = "DMA THREAD 1";
            public DmaChannel dmaChannel = dma1;
            private final int BEGIN = 0;
            private final int SIZE = 5;

            // MULTIPLIES ALL RAM VALUES BY 2
            @Override
            public void run() {
                for (int i = 0; i < SIZE; i++) {
                    System.out.println(NAME+": BEGINNING OPERATION");
                    long val = dmaChannel.readRAM((i+BEGIN)*4);
                    System.out.println(NAME+": RETRIEVED VALUE FROM RAM: "+val+" @ ADDRESS: "+((i+BEGIN)*4));
                    val *= 2;
                    System.out.println(NAME+": WRITING THIS VALUE TO RAM: "+val+" @ ADDRESS: "+((i+BEGIN)*4));
                    dmaChannel.writeRAM((i+BEGIN)*4, val);
                }
            }
        };

        //Create 2nd DMA
        DmaChannel dma2 = new DmaChannel(ram);
        Thread t2 = new Thread() {
            private static final String NAME = "DMA THREAD 2";
            public DmaChannel dmaChannel = dma2;
            private final int BEGIN = 5;
            private final int SIZE = 5;

            // ADDS ALL RAM VALUES BY 7
            @Override
            public void run() {
                for (int i = 0; i < SIZE; i++) {
                    System.out.println(NAME+": BEGINNING OPERATION");
                    long val = dmaChannel.readRAM((i+BEGIN)*4);
                    System.out.println(NAME+": RETRIEVED VALUE FROM RAM: "+val+" @ ADDRESS: "+((i+BEGIN)*4));
                    val += 7;
                    System.out.println(NAME+": WRITING THIS VALUE TO RAM: "+val+" @ ADDRESS: "+((i+BEGIN)*4));
                    dmaChannel.writeRAM((i+BEGIN)*4, val);
                }
            }
        };

        //Create 3rd DMA
        DmaChannel dma3 = new DmaChannel(ram);
        Thread t3 = new Thread() {
            private static final String NAME = "DMA THREAD 3";
            public DmaChannel dmaChannel = dma3;
            private final int BEGIN = 10;
            private final int SIZE = 5;

            // MULTIPLIES ALL RAM VALUES BY 8
            @Override
            public void run() {
                for (int i = 0; i < SIZE; i++) {
                    System.out.println(NAME+": BEGINNING OPERATION");
                    long val = dmaChannel.readRAM((i+BEGIN)*4);
                    System.out.println(NAME+": RETRIEVED VALUE FROM RAM: "+val+" @ ADDRESS: "+((i+BEGIN)*4));
                    val *= 8;
                    System.out.println(NAME+": WRITING THIS VALUE TO RAM: "+val+" @ ADDRESS: "+((i+BEGIN)*4));
                    dmaChannel.writeRAM((i+BEGIN)*4, val);
                }
            }
        };

        //Create 4th DMA
        DmaChannel dma4 = new DmaChannel(ram);
        Thread t4 = new Thread() {
            private static final String NAME = "DMA THREAD 4";
            public DmaChannel dmaChannel = dma4;
            private final int BEGIN = 15;
            private final int SIZE = 5;

            // SUBTRACTS ALL RAM VALUES BY 6
            @Override
            public void run() {
                for (int i = 0; i < SIZE; i++) {
                    System.out.println(NAME+": BEGINNING OPERATION");
                    long val = dmaChannel.readRAM((i+BEGIN)*4);
                    System.out.println(NAME+": RETRIEVED VALUE FROM RAM: "+val+" @ ADDRESS: "+((i+BEGIN)*4));
                    val -= 6;
                    System.out.println(NAME+": WRITING THIS VALUE TO RAM: "+val+" @ ADDRESS: "+((i+BEGIN)*4));
                    dmaChannel.writeRAM((i+BEGIN)*4, val);
                }
            }
        };

        t1.start();
        t2.start();
        t3.start();
        t4.start();

        try {
            t1.join();
            t2.join();
            t3.join();
            t4.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long[] ramResults = {
                2L,
                4L,
                6L,
                8L,
                10L,
                13L,
                14L,
                15L,
                16L,
                17L,
                88L,
                96L,
                104L,
                112L,
                120L,
                10L,
                11L,
                12L,
                13L,
                14L,
        };

        for (int i = 0; i < ramContents.length; i++) {
            System.out.println(ram.readRam(i));
        }

        for (int i = 0; i < ramResults.length; i++) {
            long expected = ramResults[i];
            long actual = ram.readRam(i);
            assertEquals("After all DMAs operated on I/O values, we should have found "+expected+" at address " +
                    (i*4)+", but instead we got "+actual, expected, actual);
        }
    }
}