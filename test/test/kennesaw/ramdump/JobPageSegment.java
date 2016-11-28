package test.kennesaw.ramdump;

import com.kennesaw.memory.Page;

/**
 * Created by Thomas on 11/20/2016.
 */
public class JobPageSegment {
    public static final int INPUT_SIZE = (int) (0x14 / 4.0);
    public static final int OUTPUT_SIZE = (int) (0xC / 4.0);
    public static final int TEMP_SIZE = (int) (0xC / 4.0);

    private int jobId;
    private int jobSize;
    private Page[] instructions;
    private Page[] inputBuffer;
    private Page[] finalTempBuffer;
    private Page[] initialTempBuffer;
    private Page[] finalOutputBuffer;
    private Page[] initialOutputBuffer;

    public JobPageSegment(int id, int size) {
        jobId = id;
        jobSize = size;
        size = (int) Math.ceil((double) size / 4.0);
        instructions = new Page[size];
        inputBuffer = new Page[INPUT_SIZE];
        finalTempBuffer = new Page[TEMP_SIZE];
        initialTempBuffer = new Page[TEMP_SIZE];
        finalOutputBuffer = new Page[OUTPUT_SIZE];
        initialOutputBuffer = new Page[OUTPUT_SIZE];
    }

    public void addPagesFromRawInstructions(long[] dataArr) {
        int dataOffset = 0;
        long[] instr = new long[jobSize];
        for (int i = 0; i < instr.length; i++) {
            instr[i] = dataArr[i+dataOffset];
        }
        dataOffset += instr.length;
        addInstruction(instr);

        long[] input = new long[0x14];
        for (int i = 0; i < input.length; i++) {
            input[i] = dataArr[i+dataOffset];
        }
        dataOffset += input.length;
        addInputBuffer(input);

        long[] output = new long[0xC];
        for (int i = 0; i < output.length; i++) {
            output[i] = dataArr[i+dataOffset];
        }
        dataOffset += output.length;
        addInitialOutputBuffer(output);

        long[] temp = new long[0xC];
        for (int i = 0; i < temp.length; i++) {
            temp[i] = dataArr[i+dataOffset];
        }
        addInitialTempBuffer(temp);
    }

    private void addInstruction(long[] instr) {
        Page currentPage = new Page();
        int instructionIndex = 0;
        for (long data : instr) {
            if (currentPage.getWordsAvailable() == 0) {
                instructions[instructionIndex++] = currentPage;
                currentPage = new Page();
            }
            currentPage.writeToPage(Page.PAGE_SIZE - currentPage.getWordsAvailable(), data);
        }
        while (currentPage.getWordsAvailable() > 0) {
            currentPage.writeToPage(Page.PAGE_SIZE - currentPage.getWordsAvailable(), 0L);
        }
        instructions[instructionIndex++] = currentPage;
    }

    private void addInputBuffer(long[] input) {
        Page currentPage = new Page();
        int instructionIndex = 0;
        for (long data : input) {
            if (currentPage.getWordsAvailable() == 0) {
                inputBuffer[instructionIndex++] = currentPage;
                currentPage = new Page();
            }
            currentPage.writeToPage(Page.PAGE_SIZE - currentPage.getWordsAvailable(), data);
        }
        while (currentPage.getWordsAvailable() > 0) {
            currentPage.writeToPage(Page.PAGE_SIZE - currentPage.getWordsAvailable(), 0L);
        }
        inputBuffer[instructionIndex++] = currentPage;
    }

    private void addInitialTempBuffer(long[] temp) {
        Page currentPage = new Page();
        int instructionIndex = 0;
        for (long data : temp) {
            if (currentPage.getWordsAvailable() == 0) {
                initialTempBuffer[instructionIndex++] = currentPage;
                currentPage = new Page();
            }
            currentPage.writeToPage(Page.PAGE_SIZE - currentPage.getWordsAvailable(), data);
        }
        while (currentPage.getWordsAvailable() > 0) {
            currentPage.writeToPage(Page.PAGE_SIZE - currentPage.getWordsAvailable(), 0L);
        }
        initialTempBuffer[instructionIndex++] = currentPage;
    }

    private void addInitialOutputBuffer(long[] output) {
        Page currentPage = new Page();
        int instructionIndex = 0;
        for (long data : output) {
            if (currentPage.getWordsAvailable() == 0) {
                initialOutputBuffer[instructionIndex++] = currentPage;
                currentPage = new Page();
            }
            currentPage.writeToPage(Page.PAGE_SIZE - currentPage.getWordsAvailable(), data);
        }
        while (currentPage.getWordsAvailable() > 0) {
            currentPage.writeToPage(Page.PAGE_SIZE - currentPage.getWordsAvailable(), 0L);
        }
        initialOutputBuffer[instructionIndex++] = currentPage;
    }

    @Override
    public String toString() {
        String retStr = "JobPageSegement"+(jobId+1)+"\n";
        Page[][] dataBufferArr = {
            instructions,
            inputBuffer,
            initialOutputBuffer,
            initialTempBuffer
        };
        int counter = 0;
        for (Page[] pageArr : dataBufferArr) {
            for (Page e : pageArr) {
                retStr += "\tPage"+(counter++)+":\n";
                for (int i = 0; i < Page.PAGE_SIZE; i++) {
                    retStr += "\t\t"+e.readPage(i)+"\n";
                }
            }
        }
        return retStr;
    }
}
