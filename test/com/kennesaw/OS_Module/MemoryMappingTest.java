package com.kennesaw.OS_Module;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by Thomas on 11/25/2016.
 */
public class MemoryMappingTest {
    @Test
    public void addPageToPCB() throws Exception {
        MemoryMapping memoryMapping = new MemoryMapping();
        ArrayList<Integer> newList;

        // PCB 1 test with Integer 0, 1, 2, 3
        PCB pcb1 = new PCB(10, 24, 12);
        Integer[] correctPages1 = {0, 1, 2, 3};
        Integer[] pages1 = {0, 1, 2, 3};
        for (Integer page : pages1) {
            memoryMapping.addPageToPCB(pcb1, page);
        }
        newList = memoryMapping.getPagesForPCB(pcb1);
        for (int i = 0; i < newList.size(); i++) {
            assertEquals("PCB 1 Test FAILED", correctPages1[i],newList.get(i));
        }

        // PCB 2 test with Integer 0, 2, 2, 3
        PCB pcb2 = new PCB(22, 24, 7);
        Integer[] correctPages2 = {0, 2, 3};
        Integer[] pages2 = {0, 2, 2, 3};
        for (Integer page : pages2) {
            memoryMapping.addPageToPCB(pcb2, page);
        }
        newList = memoryMapping.getPagesForPCB(pcb2);
        for (int i = 0; i < newList.size(); i++) {
            assertEquals("PCB 2 Test FAILED", correctPages2[i],newList.get(i));
        }


        // PCB 3 test with Integer 4,4,4,4,4,4,4,4
        PCB pcb3 = new PCB(21, 44, 24);
        Integer[] correctPages3 = {4};
        Integer[] pages3 = {4,4,4,4,4,4,4,4};
        for (Integer page : pages3) {
            memoryMapping.addPageToPCB(pcb3, page);
        }
        newList = memoryMapping.getPagesForPCB(pcb3);
        for (int i = 0; i < newList.size(); i++) {
            assertEquals("PCB 3 Test FAILED", correctPages3[i],newList.get(i));
        }


    }

    @Test
    public void getPagesForPCB() throws Exception {

    }

    @Test
    public void contains() throws Exception {

    }

    @Test
    public void removeFromQueue() throws Exception {

    }

    @Test
    public void getNextPCB() throws Exception {

    }

    @Test
    public void size() throws Exception {

    }

    @Test
    public void isEmpty() throws Exception {

    }

}