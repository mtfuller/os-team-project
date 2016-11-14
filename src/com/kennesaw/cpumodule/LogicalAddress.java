package com.kennesaw.cpumodule;

/**
 * Created by Thomas on 11/13/2016.
 */
public class LogicalAddress {
    private byte pageNumber;
    private byte pageOffset;

    public LogicalAddress(byte pn, byte po) {
        pageNumber = pn;
        pageOffset = po;
    }

    public byte getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(byte pageNumber) {
        this.pageNumber = pageNumber;
    }

    public byte getPageOffset() {
        return pageOffset;
    }

    public void setPageOffset(byte pageOffset) {
        this.pageOffset = pageOffset;
    }
}
