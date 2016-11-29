package com.kennesaw.cpumodule;

import com.kennesaw.memory.Page;

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

    public synchronized void setValidPage(int pageNumber, boolean isValidPage) {
        if (isValidPage && !validBitMap[pageNumber]) numberOfValidPages++;
        else if(!isValidPage && validBitMap[pageNumber]) numberOfValidPages--;
        validBitMap[pageNumber] = isValidPage;
    }

    public synchronized void setDirtyPage(int pageNumber, boolean isDirtyPage) {
        dirtyBitMap[pageNumber] = isDirtyPage;
    }

    public synchronized Page readPage(int pageNumber) {
        return cache[pageNumber];
    }

    public synchronized long readCache(LogicalAddress logAddr) {
        return cache[logAddr.getPageNumber()].readPage(logAddr.getPageOffset());
    }

    public synchronized void writeCache(LogicalAddress logAddr, long data) {
        cache[logAddr.getPageNumber()].writeToPage(logAddr.getPageOffset(), data);
        setDirtyPage(logAddr.getPageNumber(), true);
    }

    public synchronized boolean isPageValid(LogicalAddress logAddr) {
        return validBitMap[logAddr.getPageNumber()];
    }

    public synchronized boolean isPageValid(int logAddr) {
        return validBitMap[logAddr];
    }

    public synchronized boolean isPageModified(LogicalAddress logAddr) {
        return dirtyBitMap[logAddr.getPageNumber()];
    }

    public synchronized boolean isPageModified(int logAddr) {
        return dirtyBitMap[logAddr];
    }

    public synchronized int getCacheSize() {
        return numberOfValidPages;
    }

    @Override
    public String toString() {
        final String HEADER_FORMAT = "DEBUG | CACHE | ";
        String retStr = HEADER_FORMAT;
        retStr += "Cache:\n";
        for (int i = 0; i < cache.length; i++) {
            retStr += HEADER_FORMAT+"\t"+cache[i].toString()+"\n";
        }
        return retStr;
    }
}
