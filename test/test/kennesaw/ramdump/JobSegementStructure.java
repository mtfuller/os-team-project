package test.kennesaw.ramdump;

/**
 * Created by Thomas on 11/8/2016.
 */
public class JobSegementStructure {
    public static final byte NUMBER_OF_JOBS = 30;

    public JobPageSegment[] jobPageSegments;

    public JobSegementStructure() {
        jobPageSegments = new JobPageSegment[NUMBER_OF_JOBS];
        build();
    }

    private void build() {
        final int DATA_SIZE = 0x14 + 0xC + 0xC;
        int dataOffset = 0;

        for (int i = 0; i < ProgramFileData.NUMBER_OF_JOBS; i++) {
            int instrSize = ProgramFileData.jobSizes[i];
            int jobSize = instrSize + DATA_SIZE;
            JobPageSegment newPage = new JobPageSegment(i, instrSize);

            long[] jobArr = new long[jobSize];
            for (int j = 0; j < jobArr.length; j++) {
                jobArr[j] = ProgramFileData.initialRamDump[j + dataOffset];
            }
            newPage.addPagesFromRawInstructions(jobArr);
            dataOffset += jobArr.length;

            jobPageSegments[i] = newPage;
        }
    }

    @Override
    public String toString() {
        String retStr = "JOB SEGMENTS:\n\n";
        for (JobPageSegment e : jobPageSegments) retStr += e.toString();
        return retStr;
    }
}
