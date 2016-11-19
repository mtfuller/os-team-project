package com.kennesaw.OS_Module;

import memory.Page;

import java.util.ArrayList;

/**
 * Created by Margaret on 11/15/2016.
 */
public class PageTable {
    
    int[] pageSpan;
    boolean[] valid;
    int counter;
    
    public PageTable(int arraySize){
        pageSpan = new int[arraySize];
        valid = new boolean[arraySize];
        counter = 0;
    }
    
    public void writePageTable(int pageNumber) {
        pageSpan[counter] = pageNumber;
        valid[counter] = true;
        counter++;
    }
    
    public int[] readPageTable(PCB pcb) {
        return pageSpan;
    }
    
    public int getPage(int pageNum) {
        return pageSpan[pageNum];
    }
    
    public boolean getValid(int index) {
        return valid[index];
    }
    
    public void flipValid(int index) {
        valid[index] = !valid[index];
    }
    
}
