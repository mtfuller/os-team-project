/**
 * Created by Margaret on 9/3/2016.
 */

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
    
    public String[] currentState = { "New", "Ready", "Waiting", "Running", "Ended" };
    
    public PCB(int jobNum, int jobLines, int prior, int diskBegin) {
        status = currentState[0];
        jobID = jobNum;
        jobSize = jobLines;
        priority = prior;
        diskAddressBegin = diskBegin;
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
        return jobSize;
    }
    
    public void setJobSize(int jobSize) {
        this.jobSize = jobSize;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
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
}