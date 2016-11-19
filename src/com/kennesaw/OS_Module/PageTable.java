package com.kennesaw.OS_Module;

import memory.Page;

import java.util.ArrayList;

/**
 * Created by Margaret on 11/15/2016.
 */
public class PageTable {
    
    ArrayList<Integer> pageSpan;
    ArrayList<Boolean> valid;
    
    public PageTable(){
        pageSpan = new ArrayList<>();
        valid = new ArrayList<>();
    }
    
    public void writePageTable(int pageNumber) {
        pageSpan.add(pageNumber);
        valid.add(true);
    }
    
    public ArrayList readPageTable(PCB pcb) {
        return pageSpan;
    }
    
    public int getPage(int index) {
        return pageSpan.get(index);
    }
    
    public Boolean getValid(int index) {
        return valid.get(index);
    }
    
    public void flipValid(int index) {
        valid.set(index, !valid.get(index));
    }
    
}
