package com.kennesaw.analysis;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by willw on 10/19/2016.
 */
public class Analysis {
    private static ArrayList<Long> createTimes = new ArrayList<>();
    private static ArrayList<Long> waitTimes = new ArrayList<>();
    private static ArrayList<Integer> jobID1 = new ArrayList<>();
    private static ArrayList<Long> completeTimes = new ArrayList<>();
    private static ArrayList<Integer> io= new ArrayList<>();
    private static ArrayList<Double> cpuSpace = new ArrayList<>();
    private static ArrayList<Integer> cpuID1= new ArrayList<>();
    private static ArrayList<Double> ramSpace1 = new ArrayList<>();
    private static ArrayList<Integer> memoryUtilization = new ArrayList<>();
    private static ArrayList<Integer> numOFPageFaults = new ArrayList<>();
    private static ArrayList<Long> pageFaultStart = new ArrayList<>();
    private static ArrayList<Long> pageFaultComplete = new ArrayList<>();
    private static ArrayList<Long> pageFaultRunningTotals = new ArrayList<>();
    private static ArrayList<Long> cpuSpinningStart = new ArrayList<>();
    private static ArrayList<Long> cpuSpinningComplete = new ArrayList<>();
    private static ArrayList<Long> cpuSpinningRunningTotals = new ArrayList<>();

    public synchronized static void initializeDataTables() {
        for (int i = 0; i < 30; i++) {
//            jobID1.add(0);
            createTimes.add(0L);
            waitTimes.add(0L);
            completeTimes.add(0L);
            io.add(0);
            cpuSpace.add(0.0);
            cpuID1.add(0);
            ramSpace1.add(0.0);
            memoryUtilization.add(0);
            numOFPageFaults.add(0);
            pageFaultStart.add(0L);
            pageFaultComplete.add(0L);
            pageFaultRunningTotals.add(0L);
            cpuSpinningStart.add(0L);
            cpuSpinningComplete.add(0L);
            cpuSpinningRunningTotals.add(0L);
        }
    }

    public synchronized static void recordNumOFJobs(int jobID){
        jobID1.add(jobID);
    }

    public synchronized static void recordCreateTime(int jobID){
        createTimes.set(jobID, System.nanoTime());
    }

    public synchronized static void recordWaitTime(int jobID){
        waitTimes.set(jobID, System.nanoTime());
    }

    public synchronized static void recordCompleteTime(int jobID){
        completeTimes.set(jobID, System.nanoTime());
    }

    public synchronized static void recordIO(int jobID){
        int tempIO;
        tempIO = io.get(jobID);
        io.set(jobID, (tempIO + 1));
    }

    public synchronized static void recordCPU(int jobID, int cpuID, double cacheUsedSize){
        cpuSpace.set(jobID, cacheUsedSize);
        cpuID1.set(jobID, cpuID);
    }

    public synchronized static void recordRamSpace(int jobID, double ramSpace){
        ramSpace1.set(jobID, ramSpace);
    }
    
    public synchronized static void recordMemoryUtilization(int jobID, int pageUsed){
        memoryUtilization.set(jobID, pageUsed);
    }
    
    public synchronized static void recordNumOFPageFaults(int jobID){
        int tempPF;
        tempPF = numOFPageFaults.get(jobID);
        numOFPageFaults.set(jobID, (tempPF + 1));
    }
    
    public synchronized static void recordPageFaultStart(int jobID){
        pageFaultStart.set(jobID,System.nanoTime());
    }
    
    public synchronized static void recordPageFaultComplete(int jobID){
        pageFaultComplete.set(jobID,System.nanoTime());
        long totalTimeSoFar;
        totalTimeSoFar = (pageFaultRunningTotals.get(jobID) + (pageFaultComplete.get(jobID) - pageFaultStart.get(jobID)));
        pageFaultRunningTotals.set(jobID, totalTimeSoFar);
    }
    
    public synchronized static void recordCPUSpinningStart(int jobID) {
        cpuSpinningStart.set(jobID, System.nanoTime());
    }
    
    public synchronized static void recordCPUSpinningComplete(int jobID) {
        cpuSpinningComplete.set(jobID,System.nanoTime());
        long totalTimeSoFar;
        totalTimeSoFar = cpuSpinningRunningTotals.get(jobID) + ((cpuSpinningComplete.get(jobID) - cpuSpinningStart.get(jobID)));
        cpuSpinningRunningTotals.set(jobID, totalTimeSoFar);
    }
    
    public synchronized static void calctoString() {
        //subtracts createTime/completeTime by waitTime
        ArrayList<Long> realWait = new ArrayList<>();
        ArrayList<Long> realComplete = new ArrayList<>();
        ArrayList<Long> realPageFaultTime = new ArrayList<>();
        ArrayList<Long> realCPUSpinningTime = new ArrayList<>();
        for(int i = 0; i < 30; i++){
            realWait.add(waitTimes.get(i) - createTimes.get(i));
            realComplete.add(completeTimes.get(i) - createTimes.get(i));
            realPageFaultTime.add(pageFaultRunningTotals.get(i));
            realCPUSpinningTime.add(cpuSpinningRunningTotals.get(i));
        }

        //printout into table format all arraylists must have same size to work
        System.out.println(String.format("%5s    | %5s    |%5s |%8s |%9s      | %9s   |%9s |%9s |%9s |%9s",
                "JobID", "  Wait Times", "Complete Times",
                "  # of IO ", "CPUID", "CPU Space",
                " Ram Space", "# of PF", "Paging Svc.", "CPU Running Times"));
        System.out.println("==============================================================");
        for(int i = 0; i < 30; i ++) {
            DecimalFormat df = new DecimalFormat("#.####");
            String real_wait = df.format(realWait.get(i)/1000000.00);
            String real_complete = df.format(realComplete.get(i)/1000000.0000);
            String real_PageFault = df.format(realPageFaultTime.get(i)/1000000.0000);
            String real_CPUSpinning = df.format(realCPUSpinningTime.get(i)/1000000.0000);

            DecimalFormat dfCpu = new DecimalFormat("#.###");
            String prctCPU = dfCpu.format(cpuSpace.get(i));
            DecimalFormat dfRAM = new DecimalFormat("#.##");
            String prctRam = dfRAM.format((ramSpace1.get(i) / 256)*100);
            System.out.println(String.format("%5s      |  %9s ms    | %9s ms       |    %5s       | %9s         | %9s      |%9s%%      |%9s      | %9s ms | %9s ms",
                    jobID1.get(i),
                    real_wait,
                    real_complete,
                    io.get(i),
                    cpuID1.get(i),
                    prctCPU,
                    prctRam,
//                    ((ramSpace1.get(i) / 256)*100),
                    numOFPageFaults.get(i),
                    real_PageFault,
                    real_CPUSpinning)
            );
        }

        //calc avg wait
        long totalWait =0;
        int size = realWait.size();
        long avgWait = 0;
        for(int i = 0; i < 30; i++) totalWait += realWait.get(i);
        avgWait = totalWait/size;
        System.out.println("==============================================================");
        System.out.println();
        DecimalFormat df = new DecimalFormat("#.####");
        String avg_wait = df.format(avgWait/1000000.00);
        System.out.println("Average Wait Time: " + avg_wait + " ms");

        //calc avg complete
        long totalComplete = 0;
        int size1 = completeTimes.size();
        long avgComplete = 0;
        for(int i = 0; i < 30; i++) totalComplete += realComplete.get(i);
        avgComplete = totalComplete/size1;
        String avg_complete = df.format(avgComplete/1000000.0000);
        System.out.println("Average Complete Time: " + avg_complete + " ms");
    
        //calc avg page svc times
        long totalPageSvc = 0;
        int size2 = pageFaultRunningTotals.size();
        long avgPageSvc = 0;
        for(int i = 0; i < 30; i++) totalPageSvc += realPageFaultTime.get(i);
        avgPageSvc = totalPageSvc/size2;
        String avg_pageFaults = df.format(avgPageSvc/1000000.0000);
        System.out.println("Average Page Servicing Time: " + avg_pageFaults + " ms");
    
        //calc avg CPU spinning times
        long totalCPUSpin = 0;
        int size3 = completeTimes.size();
        long avgCPUSpin = 0;
        for(int i = 0; i < 30; i++) totalCPUSpin += realCPUSpinningTime.get(i);
        avgCPUSpin = totalCPUSpin/size3;
        String avg_CPUSpin = df.format(avgCPUSpin/1000000.0000);
        System.out.println("Average CPU Spinning Time: " + avg_CPUSpin + " ms");
        
    }

    public synchronized  static void calculateAnalysisToFile(String method, String cpus) throws IOException {
        FileWriter fileWriter = new FileWriter(new File("simulationData.csv"));

        Date todaysDate = new Date();
        fileWriter.write("Virtual Machine Simulation - CS 3502 Operating Systems - Team Project\n");
        fileWriter.write("Timestamp:, "+todaysDate.toString()+"\n");
        fileWriter.write("Sorting Method:, "+method+"\n");
        fileWriter.write("Number of CPUs:, "+cpus+"\n");


        //subtracts createTime/completeTime by waitTime
        ArrayList<Long> realWait = new ArrayList<>();
        ArrayList<Long> realComplete = new ArrayList<>();
        ArrayList<Long> realPageFaultTime = new ArrayList<>();
        ArrayList<Long> realCPUSpinningTime = new ArrayList<>();
        for(int i = 0; i < 30; i++){
            realWait.add(waitTimes.get(i) - createTimes.get(i));
            realComplete.add(completeTimes.get(i) - createTimes.get(i));
            realPageFaultTime.add(pageFaultRunningTotals.get(i));
            realCPUSpinningTime.add(cpuSpinningRunningTotals.get(i));
        }

        //printout into table format all arraylists must have same size to work
        fileWriter.write(
                String.format("%s, %s, %s, %s, %s, %s, %s, %s, %s, %s,\n",
                        "JobID", "Wait Times", "Complete Times",
                        "# of IO", "CPU ID", "CPU Space",
                        "Ram Space Used", "# of PF", "Servicing Times for Paging", "CPU Running Times")
        );

        for(int i = 0; i < 30; i ++) {
            DecimalFormat df = new DecimalFormat("#.####");
            String real_wait = df.format(realWait.get(i)/1000000.00);
            String real_complete = df.format(realComplete.get(i)/1000000.0000);
            String real_PageFault = df.format(realPageFaultTime.get(i)/1000000.0000);
            String real_CPUSpinning = df.format(realCPUSpinningTime.get(i)/1000000.0000);

            DecimalFormat dfCpu = new DecimalFormat("#.###");
            String prctCPU = dfCpu.format(cpuSpace.get(i));
            String prctRam = dfCpu.format(ramSpace1.get(i));
            fileWriter.write(
                    String.format("%s, %s, %s, %s, %s, %s, %s, %s, %s, %s,\n",
                            jobID1.get(i),
                            real_wait,
                            real_complete,
                            io.get(i),
                            cpuID1.get(i),
                            prctCPU,
                            ((ramSpace1.get(i) / 256)*100),
                            numOFPageFaults.get(i),
                            real_PageFault,
                            real_CPUSpinning)
            );
        }

        fileWriter.write("\n");

        //calc avg wait
        long totalWait =0;
        int size = realWait.size();
        long avgWait = 0;
        for(int i = 0; i < 30; i++) totalWait += realWait.get(i);
        avgWait = totalWait/size;
        DecimalFormat df = new DecimalFormat("#.####");
        String avg_wait = df.format(avgWait/1000000.00);
        fileWriter.write("Average Wait Time (ms), ");
        fileWriter.write(avg_wait+"\n");

        //calc avg complete
        long totalComplete = 0;
        int size1 = completeTimes.size();
        long avgComplete = 0;
        for(int i = 0; i < 30; i++) totalComplete += realComplete.get(i);
        avgComplete = totalComplete/size1;
        String avg_complete = df.format(avgComplete/1000000.0000);
        fileWriter.write("Average Complete Time (ms), ");
        fileWriter.write(avg_complete+"\n");

        //calc avg page svc times
        long totalPageSvc = 0;
        int size2 = pageFaultRunningTotals.size();
        long avgPageSvc = 0;
        for(int i = 0; i < 30; i++) totalPageSvc += realPageFaultTime.get(i);
        avgPageSvc = totalPageSvc/size2;
        String avg_pageFaults = df.format(avgPageSvc/1000000.0000);
        fileWriter.write("Average Page Servicing Time (ms), ");
        fileWriter.write(avg_pageFaults+"\n");

        //calc avg CPU spinning times
        long totalCPUSpin = 0;
        int size3 = completeTimes.size();
        long avgCPUSpin = 0;
        for(int i = 0; i < 30; i++) totalCPUSpin += realCPUSpinningTime.get(i);
        avgCPUSpin = totalCPUSpin/size3;
        String avg_CPUSpin = df.format(avgCPUSpin/1000000.0000);
        fileWriter.write("Average CPU Spinning Time (ms), ");
        fileWriter.write(avg_CPUSpin+"\n");

        fileWriter.close();
    }

}