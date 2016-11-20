package com.kennesaw.OS_Module;

import memory.Page;
import memory.Ram;

/**
 * Created by Margaret on 11/16/2016.
 */
public class PageManager {
    
    int freeFramePool;
    boolean isPageAvailable;
    
    public PageManager(Ram ram) {
        freeFramePool = ram.getFreePages();
        isPageAvailable = true;
    }
    
    public Page getFreePage() {
        freeFramePool--;
        if (freeFramePool == 0) {
            isPageAvailable = false;
        }
        return new Page();
    }
    
    public boolean getIsPageAvailable(){
        return isPageAvailable;
    }
    
}
