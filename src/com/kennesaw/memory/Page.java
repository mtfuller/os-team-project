package com.kennesaw.memory;

import java.util.Arrays;

/**
 * Created by willw on 11/12/2016.
 */
public class Page {
    public static final int PAGE_SIZE = 4;
    
    long[] page;
    int wordsAvailable;
    boolean isFull;
    
    public Page(){
        page = new long[4];
        wordsAvailable = 4;
        for (byte b = 0; b < PAGE_SIZE; b++) page[b] = 0L;
        isFull = false;
    }
    
    public synchronized void writeToPage(int index, long instr){
        page[index]= instr;
        wordsAvailable--;
        if (wordsAvailable == 0) {
            isFull = true;
        }
    }
    
    public synchronized long readPage(int index){
        return page[index];
    }
    
    public synchronized boolean isPageFull(){
        return isFull;
    }
    
    public synchronized int getWordsAvailable(){
        return wordsAvailable;
    }

    @Override
    public String toString() {
        return "Page{" +
                "page=" + Arrays.toString(page) +
                ", wordsAvailable=" + wordsAvailable +
                ", isFull=" + isFull +
                '}';
    }
}
