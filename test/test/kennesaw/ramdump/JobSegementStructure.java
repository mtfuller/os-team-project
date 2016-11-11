package test.kennesaw.ramdump;

/**
 * Created by Thomas on 11/8/2016.
 */
public class JobSegementStructure {
    public static final byte NUMBER_OF_JOBS = 30;

    public JobSegment[] jobSegments;

    public JobSegementStructure() {
        jobSegments = new JobSegment[NUMBER_OF_JOBS];
        build();
    }

    private void build() {
        // Intialize settings
        int dataOffset = 0;
        long[] initDump = ProgramFileData.initialRamDump;
        long[] finalDump = ProgramFileData.finalRamDump;

        // Iteratorate through each job from Program-File.txt
        for (int jobId = 0; jobId < ProgramFileData.NUMBER_OF_JOBS; jobId++) {
            //Get segment size of job (i.e. sum of instruction, input buffer, temp buffer, and output buffer sizes)
            int segmentSize = ProgramFileData.jobSizes[jobId] + JobSegment.INPUT_SIZE + JobSegment.OUTPUT_SIZE +
                    JobSegment.TEMP_SIZE;
            jobSegments[jobId] = new JobSegment(jobId, ProgramFileData.jobSizes[jobId]);

            // Add Job Data
            for (int index = 0; index < segmentSize; index++) {
                jobSegments[jobId].setRawData(index, initDump[index + dataOffset], true);
                jobSegments[jobId].setRawData(index, finalDump[index + dataOffset], false);
            }
            dataOffset += segmentSize;
        }
    }

    @Override
    public String toString() {
        String retStr = "JOB SEGMENTS:\n\n";
        for (JobSegment e : jobSegments) retStr += e.toString();
        return retStr;
    }
}
