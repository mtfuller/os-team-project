package com.kennesaw.cpumodule;

import memory.Page;

/**
 * Created by Thomas on 11/13/2016.
 */
public class Cache {
    public static final int CACHE_SIZE = 18;

    private Page cache[];
    private boolean validBitMap[];
    private boolean dirtyBitMap[];

    public Cache() {
        cache = new Page[CACHE_SIZE];
        validBitMap = new boolean[CACHE_SIZE];
        dirtyBitMap = new boolean[CACHE_SIZE];
        for (int i = 0; i < CACHE_SIZE; i++) {
            validBitMap[i] = false;
            dirtyBitMap[i] = false;
        }
    }

    public long readCache(LogicalAddress logAddr) {
        // READ CACHE LOGIC
        return 0;
    }

    public void writeCache(LogicalAddress logAddr, long data) {
        // WRITE CACHE LOGIC
    }

    public boolean isPageValid(LogicalAddress logAddr) {
        return validBitMap[logAddr.getPageNumber()];
    }

    public boolean isPageModified(LogicalAddress logAddr) {
        return dirtyBitMap[logAddr.getPageNumber()];
    }
}
