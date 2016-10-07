package com.kennesaw.OS_Module;

import java.util.ArrayList;

/**
 * Created by Margaret on 9/20/2016.
 */

// This class holds all PCBs and sorts when necessary
    
public class Kernel {
    
    ArrayList<PCB> pcbQueue;
    ArrayList<PCB> sortedReadyQueue;
    
    public Kernel() {
        pcbQueue = new ArrayList<>();
        sortedReadyQueue = new ArrayList<>();
    }
    
    //    public void sortPCBs(String algo) {
//        ArrayList<PCB> pcbsBefore = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            Random ran = new Random();
//            int x = ran.nextInt(6) + 5;
//            pcbsBefore.add(new PCB(i + 1, (x + 20), x, (x + 555)));
//            System.out.println(pcbsBefore.get(i).getJobID());
//            System.out.println(pcbsBefore.get(i).getPriority());
//        }
//    }
    
    public PCB getPCB(int index) {
        return sortedReadyQueue.get(index);
    }
    
    public void addPCB(int index, PCB newPCB) {
        sortedReadyQueue.add(index, newPCB);
    }
    
    public int getQueueSize() {
        return sortedReadyQueue.size();
    }

    public ArrayList<PCB> accessReadyQueue() {
        return sortedReadyQueue;
    }
    
}
