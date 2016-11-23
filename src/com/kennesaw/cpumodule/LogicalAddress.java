package com.kennesaw.cpumodule;

/**
 * Created by Thomas on 11/13/2016.
 */
public class LogicalAddress {
    public static final int PAGE_SIZE = 4;
    private int pageNumber;
    private int pageOffset;

    public LogicalAddress() {
        pageNumber = 0;
        pageOffset = 0;
    }

    public LogicalAddress(int pn, int po) {
        pageNumber = pn;
        pageOffset = po;
    }

    public void convertFromRawAddress(int addr) {
        setPageNumber(addr / PAGE_SIZE);
        setPageOffset(addr % PAGE_SIZE);
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageOffset() {
        return pageOffset;
    }

    public void setPageOffset(int pageOffset) {
        this.pageOffset = pageOffset;
    }
}
