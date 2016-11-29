package com.kennesaw.osmodule;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * Created by Thomas on 11/22/2016.
 */
public class MemoryMapping {
    LinkedList<PCB> pcbOrder;
    private HashMap<PCB, ArrayList<Integer>> memoryMap;

    public MemoryMapping() {
        pcbOrder = new LinkedList<>();
        memoryMap = new HashMap<>();
    }

    public void addPageToPCB(PCB pcb, Integer integer) {
        if (memoryMap.containsKey(pcb)) {
            if (!memoryMap.get(pcb).contains(integer)) memoryMap.get(pcb).add(integer);
        } else {
            pcbOrder.addLast(pcb);
            ArrayList<Integer> temp = new ArrayList<>();
            temp.add(integer);
            memoryMap.put(pcb, temp);
        }
    }

    public ArrayList<Integer> getPagesForPCB(PCB pcb) {
        ArrayList<Integer> retArr = null;
        if (memoryMap.containsKey(pcb)) {
            retArr = memoryMap.get(pcb);
            pcbOrder.remove(pcb);
            memoryMap.remove(pcb);
        }
        return retArr;
    }

    public boolean contains(PCB pcb) {
        return memoryMap.containsKey(pcb);
    }

    public void removeFromQueue(PCB pcb) {
        if (memoryMap.containsKey(pcb)) memoryMap.remove(pcb);
        if (pcbOrder.contains(pcb)) pcbOrder.remove(pcb);
    }

    public PCB getNextPCB() {
        return pcbOrder.getFirst();
    }

    public int size() {
        return pcbOrder.size();
    }

    public boolean isEmpty() {
        return memoryMap.isEmpty();
    }
}
