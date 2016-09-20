/**
 * Created by Margaret on 9/2/2016.
 */

import java.util.Scanner;
import java.lang.*;

public class Loader {
    // Instruction File.
    java.io.File file = new java.io.File("ProgramFile.txt");
    
    // Create Scanner object to read file.
    Scanner loaderScanner;
    
    // PCB Queue to hold all PCBs.
    PCB_Queue pcbQueue = new PCB_Queue();
    
    // Current PCB's index in PCB Queue.
    int currentPCB = 0;
            
    // The PCB's beginning Disk address
    int addyCounter = 0;
    
    // Loader takes disk as parameter to write file.
    public Loader(Disk new_disk) throws Exception {
        
        this.loaderScanner = new Scanner(file);
        
        // Read the file
        while (loaderScanner.hasNext()) {
            String line = loaderScanner.nextLine();
    
            // Begin separating each line in the file by "Job," "Data," "End," and instructions.
            
            if (line.contains("JOB")) {
                // Remove whitespace to extract the job info.
                String[] lineSplitter = line.split("\\s+");
    
                // Create a new PCB object
                PCB new_PCB = new PCB(Integer.parseInt(lineSplitter[2], 16), Integer.parseInt(lineSplitter[3], 16), Integer.parseInt(lineSplitter[4], 16), addyCounter);
                pcbQueue.addPCB(currentPCB, new_PCB);
    
            } else if (line.contains("Data")) {
                String[] lineSplitter = line.split("\\s+");
                PCB thisPCB = pcbQueue.getPCB(currentPCB);
                currentPCB++;
                
                // Set this PCB's Data attributes
                thisPCB.setInputBuffer(Integer.parseInt(lineSplitter[2], 16));
                thisPCB.setOutputBuffer(Integer.parseInt(lineSplitter[3], 16));
                thisPCB.setTempBuffer(Integer.parseInt(lineSplitter[4], 16));
                
            } else if (line.contains("END")) {
                // do nothing here
            } else {
                // Write the line to Disk.
                /** The line below can be uncommented when
                 * a Disk method to write to Disk is created.
                 */
//                new_disk.writeLineToDisk(line, addyCounter);
                addyCounter++;
            }
        }
        loaderScanner.close();
    
        /** Uncomment the lines below to see each PCB's
         *  attributes. Delete altogether when necessary.
         */
//        for (int i = 0; i < pcbQueue.getQueueSize(); i++) {
//            System.out.println("Job # - " + pcbQueue.getPCB(i).getJobID());
//            System.out.println("# of Job lines - " + pcbQueue.getPCB(i).getJobSize());
//            System.out.println("Priority - " + pcbQueue.getPCB(i).getPriority());
//            System.out.println("Current State - " + pcbQueue.getPCB(i).getCurrentState());
//            System.out.println("Input Buffer size - " + pcbQueue.getPCB(i).getInputBuffer());
//            System.out.println("Output Buffer size - " + pcbQueue.getPCB(i).getOutputBuffer());
//            System.out.println("Temp Buffer size - " + pcbQueue.getPCB(i).getTempBuffer());
//            System.out.println("=============");
//        }
    }
    
    // This will allow other components to access the PCBs
    public PCB_Queue accessPCBQueue() {
        return pcbQueue;
    }
}

