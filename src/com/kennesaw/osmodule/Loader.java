package com.kennesaw.osmodule; /**
 * Created by Margaret on 9/2/2016.
 */

import java.io.InputStream;
import java.util.Scanner;
import java.lang.*;

import com.kennesaw.analysis.Analysis;
import com.kennesaw.memory.Disk;

public class Loader {
    
    // Create Scanner object to read file.
    Scanner loaderScanner;
    
    // Initialize new PCB object
    PCB new_PCB;
    
    // Current PCB's index in PCB Queue.
    int currentPCB = 0;
    
    // Variables to manage writing to Disk
    int looper = 0;
    boolean newJob;
    
    // com.kennesaw.osmodule.Loader takes disk as parameter to write file and
    // Kernel to create/access the readyQueue of PCBs.
    public Loader(Disk simDisk, Kernel simKernel) throws Exception {
        InputStream fileIo = this.getClass().getResourceAsStream("/ProgramFile.txt");
        this.loaderScanner = new Scanner(fileIo);
        // Read the file
        while (loaderScanner.hasNext()) {
            String line = loaderScanner.nextLine();
            
            if (line.contains("JOB")) {
                
                // Remove whitespace to extract the job info.
                String[] lineSplitter = line.split("\\s+");
                newJob = true;
                
                // Create a new PCB object & add to PCB queue
                new_PCB = new PCB(Integer.parseInt(lineSplitter[2], 16), Integer.parseInt(lineSplitter[3], 16), Integer.parseInt(lineSplitter[4], 16));
                new_PCB.setDiskAddressBegin(simDisk.getNextFreePage());
                new_PCB.setStatus(0);
                Analysis.recordNumOFJobs(new_PCB.getJobID()-1);
                Analysis.recordCreateTime(new_PCB.getJobID()-1);
                simKernel.addPCB(currentPCB, new_PCB);
                // Establish PCB's buffers as sets of Pages
            } else if (line.contains("Data")) {
                String[] lineSplitter = line.split("\\s+");
                
                // Set this PCB's Data attributes
                new_PCB.setInputBuffer(Integer.parseInt(lineSplitter[2], 16));
                new_PCB.setOutputBuffer(Integer.parseInt(lineSplitter[3], 16));
                new_PCB.setTempBuffer(Integer.parseInt(lineSplitter[4], 16));
                
                // Grab PCB's ending Disk address
            } else if (line.contains("END")) {
                currentPCB++;
                simDisk.addedJobToDisk();
                
                // At the end of a job, if we're sitting on a partially filled Page in Disk, increment the "nextPage" pointer
                // to the next page in Disk, so no two jobs will share a page. Set the job's ending Disk address.
                if (simDisk.readDisk(simDisk.getNextFreePage()).getWordsAvailable() != 4) {
                    new_PCB.setDiskAddressEnd(simDisk.getNextFreePage());
                    simDisk.incNextFreePage();
                } else {
                    // Otherwise, we've ended up on a new Page in Disk (for example, when the
                    // # of jobLines is evenly divisible by 4), don't increment the nextPage pointer
                    // and set the job's ending address to the previous page #
                    new_PCB.setDiskAddressEnd(simDisk.getNextFreePage() - 1);
                }
            } else {
                // Write the line to Disk as a Page at a specific index.
                long toHex = Long.parseLong(line.substring(2), 16);
                // If we're writing a new job's instructions, reset looper and turn off newJob flag
                if (newJob) {
                    newJob = false;
                    looper = 0;
                }
                // Write to Disk, incrementing the pointer to the next free page after 4 lines are written
                simDisk.writeDisk(simDisk.getNextFreePage(), (looper%4), toHex);
                looper++;
            }
        }
        // When all jobs have been written, close Scanner object
        loaderScanner.close();
    }
}