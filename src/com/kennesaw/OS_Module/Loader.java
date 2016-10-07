package com.kennesaw.OS_Module; /**
 * Created by Margaret on 9/2/2016.
 */

import java.io.File;
import java.util.Scanner;
import java.lang.*;

import com.kennesaw.OS_Module.PCB;
import com.kennesaw.OS_Module.Kernel;
import memory.Disk;

public class Loader {
    
    // Instruction File.
    File file = new java.io.File("ProgramFile.txt");
    
    // Create Scanner object to read file.
    Scanner loaderScanner;
    
    // Initialize new PCB object
    PCB new_PCB;
    
    // Current PCB's index in PCB Queue.
    int currentPCB = 0;
            
    // The counter to establish the PCB's beginning and ending Disk addresses
    int addyCounter = 0;
    
    // com.kennesaw.OS_Module.Loader takes disk as parameter to write file and
    // Kernel to create/access the readyQueue of PCBs.
    public Loader(Disk simDisk, Kernel simKernel) throws Exception {
        this.loaderScanner = new Scanner(file);
        // Read the file
        while (loaderScanner.hasNext()) {
            String line = loaderScanner.nextLine();
            
            if (line.contains("JOB")) {
                
                // Remove whitespace to extract the job info.
                String[] lineSplitter = line.split("\\s+");
                
                // Create a new PCB object & add to PCB queue
                new_PCB = new PCB(Integer.parseInt(lineSplitter[2], 16), Integer.parseInt(lineSplitter[3], 16), Integer.parseInt(lineSplitter[4], 16), addyCounter);
                simKernel.addPCB(currentPCB, new_PCB);
    
                // Establish PCB's buffers
            } else if (line.contains("Data")) {
                String[] lineSplitter = line.split("\\s+");

                // Set this PCB's Data attributes
                new_PCB.setInputBuffer(Integer.parseInt(lineSplitter[2], 16));
                new_PCB.setOutputBuffer(Integer.parseInt(lineSplitter[3], 16));
                new_PCB.setTempBuffer(Integer.parseInt(lineSplitter[4], 16));

                 // Grab PCB's ending Disk address
            } else if (line.contains("END")) {
                currentPCB++;
                new_PCB.setDiskAddressEnd(addyCounter - 1);
                new_PCB.setJobSize(addyCounter - new_PCB.getDiskAddressBegin());
                simDisk.addedJobToDisk();
            } else {
                // Write the line to Disk as a String at a specific index.
                long toHex = Long.parseLong(line.substring(2), 16);
                simDisk.writeDisk(addyCounter, toHex);
                addyCounter++;
            }
        }
        // When all jobs have been written, close Scanner object
        loaderScanner.close();
    }
}