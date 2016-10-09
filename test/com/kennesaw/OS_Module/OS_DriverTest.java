package com.kennesaw.OS_Module;

import com.kennesaw.cpumodule.CPU;
import com.kennesaw.cpumodule.DmaChannel;
import memory.Disk;
import memory.Ram;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Thomas on 10/9/2016.
 */
public class OS_DriverTest {
    @Test
    public void osDriverTest() {
        Disk simDisk = new Disk(2048);
        Ram simRAM = new Ram(1024);
        Kernel simKernel = new Kernel();
        Loader simLoader;
        DmaChannel simDMA = new DmaChannel(simRAM);
        CPU simCPU = new CPU(simDMA);


    }
}