package test.kennesaw.ramdump;

/**
 * Created by Thomas on 11/8/2016.
 */
public class JobSegment {
    public static final int INPUT_SIZE = 0x14;
    public static final int OUTPUT_SIZE = 0xC;
    public static final int TEMP_SIZE = 0xC;

    private int jobId;
    private long[] instructions;
    private long[] inputBuffer;
    private long[] finalTempBuffer;
    private long[] initialTempBuffer;
    private long[] finalOutputBuffer;
    private long[] initialOutputBuffer;

    public JobSegment(int id, int jobSize) {
        jobId = id;
        instructions = new long[jobSize];
        inputBuffer = new long[INPUT_SIZE];
        finalTempBuffer = new long[TEMP_SIZE];
        initialTempBuffer = new long[TEMP_SIZE];
        finalOutputBuffer = new long[OUTPUT_SIZE];
        initialOutputBuffer = new long[OUTPUT_SIZE];
    }

    public long getRawData(int address, boolean isInitialData) {
        int logicalAddress;
        final int instructionLimit = instructions.length;
        final int inputLimit = instructionLimit + inputBuffer.length;
        final int outputLimit = inputLimit + initialOutputBuffer.length;
        final int tempLimit = outputLimit + initialTempBuffer.length;
        if (address >= 0 && address < instructionLimit) {
            logicalAddress = address;
            return instructions[logicalAddress];
        } else if (address >= instructions.length && address < inputLimit) {
            logicalAddress = address - instructionLimit;
            return inputBuffer[logicalAddress];
        } else if (address >= inputBuffer.length && address < outputLimit) {
            logicalAddress = address - inputLimit;
            if (isInitialData) return initialOutputBuffer[logicalAddress];
            else return finalOutputBuffer[logicalAddress];
        } else if (address >= initialTempBuffer.length && address < tempLimit) {
            logicalAddress = address - outputLimit;
            if (isInitialData) return initialTempBuffer[logicalAddress];
            else return finalTempBuffer[logicalAddress];
        } else {
            return -1L;
        }
    }

    public void setRawData(int address, long data, boolean isInitialData) {
        int logicalAddress;
        final int instructionLimit = instructions.length;
        final int inputLimit = instructionLimit + inputBuffer.length;
        final int outputLimit = inputLimit + initialOutputBuffer.length;
        final int tempLimit = outputLimit + initialTempBuffer.length;
        if (address >= 0 && address < instructionLimit) {
            logicalAddress = address;
            instructions[logicalAddress] = data;
        } else if (address >= instructions.length && address < inputLimit) {
            logicalAddress = address - instructionLimit;
            inputBuffer[logicalAddress] = data;
        } else if (address >= inputBuffer.length && address < outputLimit) {
            logicalAddress = address - inputLimit;
            if (isInitialData) initialOutputBuffer[logicalAddress] = data;
            else finalOutputBuffer[logicalAddress] = data;
        } else if (address >= initialTempBuffer.length && address < tempLimit) {
            logicalAddress = address - outputLimit;
            if (isInitialData) initialTempBuffer[logicalAddress] = data;
            else finalTempBuffer[logicalAddress] = data;
        } else {

        }
    }

    @Override
    public String toString() {
        String retStr = "Job Segment ("+(jobId+1)+"):\n";
        retStr += "\tInstructions:\n";
        for (int i = 0; i < instructions.length; i++) retStr += "\t\t"+i+":\t"+instructions[i]+"\n";
        retStr += "\tInput Buffer:\n";
        for (int i = 0; i < inputBuffer.length; i++) retStr += "\t\t"+i+":\t"+inputBuffer[i]+"\n";
        retStr += "\tOutput Buffer:\n";
        for (int i = 0; i < initialOutputBuffer.length; i++) retStr += "\t\t"+i+":\t"+initialOutputBuffer[i]+"\n";
        retStr += "\tTemp Buffer:\n";
        for (int i = 0; i < initialTempBuffer.length; i++) retStr += "\t\t"+i+":\t"+initialTempBuffer[i]+"\n";
        retStr += "\n";
        return retStr;
    }
}
