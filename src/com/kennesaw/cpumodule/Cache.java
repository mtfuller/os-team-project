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
    private int numberOfValidPages;

    public Cache() {
        cache = new Page[CACHE_SIZE];
        numberOfValidPages = 0;
        validBitMap = new boolean[CACHE_SIZE];
        dirtyBitMap = new boolean[CACHE_SIZE];
        for (int i = 0; i < CACHE_SIZE; i++) {
            cache[i] = new Page();
            validBitMap[i] = false;
            dirtyBitMap[i] = false;
        }
    }

    public void setValidPage(int pageNumber, boolean isValidPage) {
        if (isValidPage && !validBitMap[pageNumber]) numberOfValidPages++;
        else if(!isValidPage && validBitMap[pageNumber]) numberOfValidPages--;
        validBitMap[pageNumber] = isValidPage;
    }

    public void setDirtyPage(int pageNumber, boolean isDirtyPage) {
        dirtyBitMap[pageNumber] = isDirtyPage;
    }

    public long readCache(LogicalAddress logAddr) {
        return cache[logAddr.getPageNumber()].readPage(logAddr.getPageOffset());
    }

    public void writeCache(LogicalAddress logAddr, long data) {
        cache[logAddr.getPageNumber()].writeToPage(logAddr.getPageOffset(), data);
        setDirtyPage(logAddr.getPageNumber(), true);
    }

    public boolean isPageValid(LogicalAddress logAddr) {
        return validBitMap[logAddr.getPageNumber()];
    }

    public boolean isPageValid(int logAddr) {
        return validBitMap[logAddr];
    }

    public boolean isPageModified(LogicalAddress logAddr) {
        return dirtyBitMap[logAddr.getPageNumber()];
    }

    public boolean isPageModified(int logAddr) {
        return dirtyBitMap[logAddr];
    }

    public int getCacheSize() {
        return numberOfValidPages;
    }
}
