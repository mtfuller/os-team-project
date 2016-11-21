package com.kennesaw.OS_Module;

import memory.Page;
import memory.Ram;

/**
 * Created by Margaret on 11/16/2016.
 */
public class PageManager extends Thread {
    
    int freeFramePool;
    boolean pageAvailable;
    
    public PageManager(Ram ram) {
        freeFramePool = ram.getFreePages();
        pageAvailable = true;
    }
    
    public Page getFreePage() {
        freeFramePool--;
        if (freeFramePool == 0) {
            pageAvailable = false;
        }
        return new Page();
    }
    
    public boolean isPageAvailable(){
        return pageAvailable;
    }
    
}
