package com.kennesaw.analysis;

import java.text.DecimalFormat;
import java.util.ArrayList;

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

    public static void initializeDataTables() {
        for (int i = 0; i < 30; i++) {
            jobID1.add(0);
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
        }
    }

    public static void recordNumOFJobs(int jobID){
        jobID1.set(jobID,jobID+1);
    }

    public static void recordCreateTime(int jobID){
        createTimes.set(jobID, System.nanoTime());
    }

    public static void recordWaitTime(int jobID){
        waitTimes.set(jobID - 1, System.nanoTime());
    }

    public static void recordCompleteTime(int jobID){
        completeTimes.set(jobID - 1, System.nanoTime());
    }

    public static void recordIO(int jobID){
        int tempIO;
        tempIO = io.get(jobID - 1);
        io.set(jobID - 1, (tempIO + 1));
    }

    public static void recordCPU(int jobID, int cpuID, double cacheUsedSize){
        cpuSpace.set(jobID, cacheUsedSize);
        cpuID1.set(jobID, cpuID);
    }

    public static void recordRamSpace(int jobID, double ramSpace){
        ramSpace1.set(jobID - 1, ramSpace);
    }
    
    public static void recordMemoryUtilization(int jobID, int pageUsed){
        memoryUtilization.set(jobID - 1, pageUsed);
    }
    
    public static void recordNumOFPageFaults(int jobID){
        int tempPF;
        tempPF = numOFPageFaults.get(jobID - 1);
        numOFPageFaults.set(jobID - 1, (tempPF + 1));
    }
    
    public static void recordPageFaultStart(int jobID){
        pageFaultStart.set(jobID - 1,System.nanoTime());
    }
    
    public static void recordPageFaultComplete(int jobID){
        pageFaultComplete.set(jobID - 1,System.nanoTime());
        long totalTimeSoFar;
        totalTimeSoFar = (pageFaultRunningTotals.get(jobID - 1) + (pageFaultComplete.get(jobID - 1) - pageFaultStart.get(jobID - 1)));
        pageFaultRunningTotals.set(jobID - 1, totalTimeSoFar);
    }
    
    public static void calctoString() {
        //subtracts createTime/completeTime by waitTime
        ArrayList<Long> realWait = new ArrayList<>();
        ArrayList<Long> realComplete = new ArrayList<>();
        ArrayList<Long> realPageFaultTime = new ArrayList<>();
        for(int i = 0; i < 30; i++){
            realWait.add(waitTimes.get(i) - createTimes.get(i));
            realComplete.add(completeTimes.get(i) - createTimes.get(i));
            realPageFaultTime.add(pageFaultRunningTotals.get(i));
        }

        //printout into table format all arraylists must have same size to work
        System.out.println(String.format("%5s    | %5s    |%5s |%8s |%9s      | %9s       |%9s |%9s |%9s",
                "JobID", "  Wait Times", "Complete Times",
                "  # of IO ", "CPUID", "CPU Space",
                " Ram Space Used ", "Number of PF", "Servicing Times for Paging"));
        System.out.println("=========================================================================");
        for(int i = 0; i < 30; i ++) {
            DecimalFormat df = new DecimalFormat("#.####");
            String real_wait = df.format(realWait.get(i)/1000000.00);
            String real_complete = df.format(realComplete.get(i)/1000000.0000);
            String real_PageFault = df.format(realPageFaultTime.get(i)/1000000.0000);

            DecimalFormat dfCpu = new DecimalFormat("#.###");
            String prctCPU = dfCpu.format(cpuSpace.get(i));
            String prctRam = dfCpu.format(ramSpace1.get(i));
            System.out.println(String.format("%5s      |  %9s ms    | %9s ms       |    %5s       | %9s         | %9s           | %9s%%       |%9s    | %9s ms",
                    jobID1.get(i),
                    real_wait,
                    real_complete,
                    io.get(i),
                    cpuID1.get(i),
                    prctCPU,
//                    prctRam,
                    ((ramSpace1.get(i) / 256)*100),
                    numOFPageFaults.get(i),
                    real_PageFault)
            );
        }

        //calc avg wait
        long totalWait =0;
        int size = realWait.size();
        long avgWait = 0;
        for(int i = 0; i < 30; i++) totalWait += realWait.get(i);
        avgWait = totalWait/size;
        System.out.println("=========================================================================");
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
    }

}