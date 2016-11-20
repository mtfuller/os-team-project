package com.kennesaw.OS_Module;

/**
 * Created by Margaret on 9/3/2016.
 */

import java.util.ArrayList;
import java.util.Arrays;

import com.kennesaw.cpumodule.CpuState;
import memory.Page;

public class PCB {
    public static final int NEW_STATE = 0;
    public static final int READY_STATE = 1;
    public static final int WAITING_STATE = 2;
    public static final int RUNNING_STATE = 3;
    public static final int ENDED_STATE = 4;
    
    private int jobID;  // Job ID
    private int priority; // Job's priority number, from data file
    private int jobSize;  // Number of line instructions
    private String status;
    private PageTable pageTable;
    private int diskAddressBegin; // Beginning address where job is located on Disk
    private int diskAddressEnd; // Beginning address where job is located on Disk
    private int RAMAddressBegin; // Long scheduler will write this to the PCBs
    private int RAMAddressEnd; // Long scheduler will write this to the PCBs
    private ArrayList<Page> inputBuffer;
    private ArrayList<Page> outputBuffer;
    private ArrayList<Page> tempBuffer;
    private CpuState state;
    
    public String[] currentState = { "New", "Ready", "Waiting", "Running", "Ended" };
    
    public PCB(int jobNum, int jobLines, int prior) {
        status = currentState[0];
        jobID = jobNum;
        jobSize = jobLines;
        priority = prior;
        diskAddressBegin = 0;
        status = currentState[0];
        pageTable = new PageTable(jobSize);
        state = new CpuState();
        inputBuffer = new ArrayList<>();
        outputBuffer = new ArrayList<>();
        tempBuffer = new ArrayList<>();
    }
    
    public int getJobID() {
        return jobID;
    }
    
    public void setJobID(int jobID) {
        this.jobID = jobID;
    }
    
    public int getPriority() {
        return priority;
    }
    
    public void setPriority(int priority) {
        this.priority = priority;
    }
    
    public int getJobSize() {
        return (getDiskAddressEnd()-getDiskAddressBegin());
    }
    
    public void setJobSize(int jobSize) {
        this.jobSize = jobSize;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(int status) {
        this.status = currentState[status];
    }
    
    public PageTable getPageTable() {
        return pageTable;
    }
    
    public int getDiskAddressBegin() {
        return diskAddressBegin;
    }
    
    public void setDiskAddressBegin(int diskAddressBegin) {
        this.diskAddressBegin = diskAddressBegin;
    }
    
    public int getDiskAddressEnd() {
        return diskAddressEnd;
    }
    
    public void setDiskAddressEnd(int diskAddressEnd) {
        this.diskAddressEnd = diskAddressEnd;
    }

//    public int getBaseAddress() {
//        return state.getBase_addr();
//    }
//
//
//    public void setBaseAddress(int addr) {
//        state.setBase_addr(addr);
//    }
    
    public int getRAMAddressBegin() {
        return RAMAddressBegin;
    }
    
    
    public void setRAMAddressBegin(int RAMAddressBegin) {
        this.RAMAddressBegin = RAMAddressBegin;
    }
    
    public int getRAMAddressEnd() {
        return RAMAddressEnd;
    }
    
    public void setRAMAddressEnd(int RAMAddressEnd) {
        this.RAMAddressEnd = RAMAddressEnd;
    }
    
    public ArrayList getInputBuffer() {
        return inputBuffer;
    }
    
    public void setInputBuffer(int input) {
        for (int i = 0; i < (input/4); i++) {
            inputBuffer.add(new Page());
        }
    }
    
    public ArrayList getOutputBuffer() {
        return outputBuffer;
    }
    
    public void setOutputBuffer(int output) {
        for (int i = 0; i < (output/4); i++) {
            inputBuffer.add(new Page());
        }
    }
    
    public ArrayList getTempBuffer() {
        return tempBuffer;
    }
    
    public void setTempBuffer(int temp) {
        for (int i = 0; i < (temp/4); i++) {
            inputBuffer.add(new Page());
        }
    }
    
    public CpuState getState() {
        return state;
    }
    
    public void setState(CpuState state) {
        this.state = state;
    }
    
    @Override
    public String toString() {
        return "PCB{" +
                "jobID=" + jobID +
                ", priority=" + priority +
                ", jobSize=" + jobSize +
                ", status='" + status + '\'' +
                ", diskAddressBegin=" + diskAddressBegin +
                ", diskAddressEnd=" + diskAddressEnd +
                ", RAMAddressBegin=" + RAMAddressBegin +
                ", RAMAddressEnd=" + RAMAddressEnd +
                ", inputBuffer=" + inputBuffer +
                ", outputBuffer=" + outputBuffer +
                ", tempBuffer=" + tempBuffer +
                ", state=" + state +
                ", currentState=" + Arrays.toString(currentState) +
                '}';
    }
}