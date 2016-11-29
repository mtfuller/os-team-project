package com.kennesaw.osmodule;

/**
 * Created by Margaret on 11/15/2016.
 */
public class PageTable {
    
    int[] pageSpan;
    boolean[] valid;
    int pointer;
    
    public PageTable(int arraySize){
        pageSpan = new int[arraySize];
        valid = new boolean[arraySize];
        pointer = 0;
    }
    
    public synchronized void writePageTable(int logicalPageNumber, int pageNumber) {
        pageSpan[logicalPageNumber] = pageNumber;
        valid[logicalPageNumber] = true;
    }
    
    public int[] readPageTable(PCB pcb) {
        return pageSpan;
    }
    
    public int getNumberOfPages() {
        return pageSpan.length;
    }
    
    public synchronized int getPage(int pageNum) {
        return pageSpan[pageNum];
    }
    
    public synchronized boolean getValid(int index) {
        return valid[index];
    }
    
    public synchronized void flipValid(int index) {
        valid[index] = !valid[index];
    }
    
    public int getPointer() {
        return pointer;
    }
    
}
