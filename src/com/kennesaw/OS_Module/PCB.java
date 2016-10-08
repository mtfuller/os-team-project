package com.kennesaw.OS_Module;

/**
 * Created by Margaret on 9/3/2016.
 */

import com.kennesaw.cpumodule.State;

import java.util.Arrays;

public class PCB {
    
    private int jobID;  // Job ID
    private int priority; // Job's priority number, from data file
    private int jobSize;  // Number of line instructions
    private String status;
    private int diskAddressBegin; // Beginning address where job is located on Disk
    private int diskAddressEnd; // Beginning address where job is located on Disk
    private int RAMAddressBegin; // Long scheduler will write this to the PCBs
    private int RAMAddressEnd; // Long scheduler will write this to the PCBs
    private int inputBuffer;
    private int outputBuffer;
    private int tempBuffer;
    private State state;
    
    public String[] currentState = { "New", "Ready", "Waiting", "Running", "Ended" };
    
    public PCB(int jobNum, int jobLines, int prior, int diskBegin) {
        status = currentState[0];
        jobID = jobNum;
        jobSize = jobLines;
        priority = prior;
        diskAddressBegin = diskBegin;
        status = currentState[0];
        state = new State();
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
    
    public int getDiskAddressBegin() {
        return diskAddressBegin;
    }
    
    public int getDiskAddressEnd() {
        return diskAddressEnd;
    }
    
    public void setDiskAddressEnd(int diskAddressEnd) {
        this.diskAddressEnd = diskAddressEnd;
    }
    
    public int getBaseAddress() {
        return state.getBase_addr();
    }
    
    
    public void setBaseAddress(int addr) {
        state.setBase_addr(addr);
    }
    
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
    
    public int getInputBuffer() {
        return inputBuffer;
    }
    
    public void setInputBuffer(int inputBuffer) {
        this.inputBuffer = inputBuffer;
    }
    
    public int getOutputBuffer() {
        return outputBuffer;
    }
    
    public void setOutputBuffer(int outputBuffer) {
        this.outputBuffer = outputBuffer;
    }
    
    public int getTempBuffer() {
        return tempBuffer;
    }
    
    public void setTempBuffer(int tempBuffer) {
        this.tempBuffer = tempBuffer;
    }
    
    public State getState() {
        return state;
    }
    
    public void setState(State state) {
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