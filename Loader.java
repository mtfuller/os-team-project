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
    
    // Initialize new PCB object
    PCB new_PCB;
    
    // PCB Queue to hold all PCBs.
    PCB_Queue pcbQueue = new PCB_Queue();
    
    // Current PCB's index in PCB Queue.
    int currentPCB = 0;
            
    // The counter to establish the PCB's beginning and ending Disk address
    int addyCounter = 0;
    
    // Loader takes disk as parameter to write file.
    public Loader(Disk new_disk) throws Exception {
        
        this.loaderScanner = new Scanner(file);
        
        // Read the file
        while (loaderScanner.hasNext()) {
            String line = loaderScanner.nextLine();
            
            // A new job (PCB)
            if (line.contains("JOB")) {
                // Remove whitespace to extract the job info.
                String[] lineSplitter = line.split("\\s+");
                
//                // Create a new PCB object
//                new_PCB = new PCB(Integer.parseInt(lineSplitter[2], 16), Integer.parseInt(lineSplitter[3], 16), Integer.parseInt(lineSplitter[4], 16), addyCounter);
//                // Add PCB to the queue
//                pcbQueue.addPCB(currentPCB, new_PCB);
    
                // Establish PCB's buffers
            } else if (line.contains("Data")) {
                String[] lineSplitter = line.split("\\s+");
                currentPCB++;
//
//                // Set this PCB's Data attributes
//                new_PCB.setInputBuffer(Integer.parseInt(lineSplitter[2], 16));
//                new_PCB.setOutputBuffer(Integer.parseInt(lineSplitter[3], 16));
//                new_PCB.setTempBuffer(Integer.parseInt(lineSplitter[4], 16));
//
////                 Grab PCB's ending Disk address
            } else if (line.contains("END")) {
//                new_PCB.setDiskAddressEnd(addyCounter);
//                // Write line to Disk (minus 0th and 1st elements - only 8 byte Words here)
            } else {
                // Write the line to Disk.
                /** The line below can be uncommented when
                 * a Disk method to write to Disk is created.
                 */
//                new_disk.writeLineToDisk(line.substring(2));
                addyCounter++;
            }
        }
        loaderScanner.close();
    
        /** Uncomment the lines below to see each PCB's
         *  attributes. Delete the lines when necessary.
         */
//        for (int i = 1; i <= pcbQueue.getQueueSize(); i++) {
//            System.out.println("Job # - " + pcbQueue.getPCB(i).getJobID());
//            System.out.println("# of Job lines - " + pcbQueue.getPCB(i).getJobSize());
//            System.out.println("Priority - " + pcbQueue.getPCB(i).getPriority());
//            System.out.println("Disk Address begin- " + pcbQueue.getPCB(i).getDiskAddressBegin());
//            System.out.println("Disk Address end- " + pcbQueue.getPCB(i).getDiskAddressEnd());
//            System.out.println("Input Buffer size - " + pcbQueue.getPCB(i).getInputBuffer());
//            System.out.println("Output Buffer size - " + pcbQueue.getPCB(i).getOutputBuffer());
//            System.out.println("Temp Buffer size - " + pcbQueue.getPCB(i).getTempBuffer());
//            System.out.println("=============");
//        }
    }
    
//    // This will allow other components to access the PCBs
//    public PCB_Queue accessPCBQueue() {
//        return pcbQueue;
//    }
}

