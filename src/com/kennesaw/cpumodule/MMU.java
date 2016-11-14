package com.kennesaw.cpumodule;

import com.kennesaw.OS_Module.Kernel;

/**
 * Created by Thomas on 11/13/2016.
 */
public class MMU {
    private Kernel kernel;

    public MMU(Kernel kern) {
        kernel = kern;
    }

    public long read(LogicalAddress logicalAddress, Cache cache) {
        if (!cache.isPageValid(logicalAddress)) {
            // IF PAGE IS NOT IN CACHE
        }
        return cache.readCache(logicalAddress);
    }

    public void write(LogicalAddress logicalAddress, long data, Cache cache) {
        // WRITE LOGIC
    }
}
