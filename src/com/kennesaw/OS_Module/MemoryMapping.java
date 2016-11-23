package com.kennesaw.OS_Module;

/**
 * Created by Thomas on 11/22/2016.
 */
public class MemoryMapping {
    private PCB pcbReference;
    private int destIndex;

    public MemoryMapping(PCB pcb, int dest) {
        pcbReference = pcb;
        destIndex = dest;
    }

    public PCB getPcbReference() {
        return pcbReference;
    }

    public int getDestIndex() {
        return destIndex;
    }
}
