package com.kennesaw.Analyzer;

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
    
    public static void initializeDataTables() {
        for (int i = 0; i < 30; i++) {
            createTimes.add(0L);
            waitTimes.add(0L);
            completeTimes.add(0L);
            io.add(0);
            cpuSpace.add(0.0);
            cpuID1.add(0);
            ramSpace1.add(0.0);
        }
    }
    
    public static void recordNumOFJobs(int jobID){
            jobID1.add(jobID,jobID+1);
    }
    public static void recordCreateTime(int jobID){
        createTimes.set(jobID, System.nanoTime());
        
    }
    public static void recordWaitTime(int jobID){
        waitTimes.set(jobID,System.nanoTime());
        
        
    }
    public static void recordCompleteTime(int jobID){
        completeTimes.set(jobID, System.nanoTime());
        
    }
    public static void recordIO(int jobID, int numOFIO){
        io.set(jobID, numOFIO);
    }
    public static void recordCPU(int jobID, int cpuID, double cacheUsedSize){
        cpuSpace.set(jobID, cacheUsedSize);
        cpuID1.set(jobID, cpuID);
    }
    public static void recordRamSpace(int jobID, double ramSpace){
        ramSpace1.add(jobID, ramSpace);
    }
    
    
    public static void calctoString() {
        
        
        //subtracts createTime by waitTime
        
        ArrayList<Long> realWait = new ArrayList<>();
        ArrayList<Long> realComplete = new ArrayList<>();
        for(int i = 0; i < 30; i++){
            realWait.add(waitTimes.get(i) - createTimes.get(i));
            realComplete.add(completeTimes.get(i) - waitTimes.get(i));
        }

        //printout into table format all arraylists must have same size to work

        System.out.println(String.format("%5s    | %5s      |%5s  |%9s   |%9s      | %9s       |%9s", "JobID", "Wait Times",
               "    Complete Times", "Number of IO", "CPUID", "CPU Space", " Ram Space Used "));
        for(int i = 0; i < 30; i ++) {
            DecimalFormat df = new DecimalFormat("#.####");
            String real_wait = df.format(realWait.get(i)/1000000.00);
            String real_complete = df.format(realComplete.get(i)/1000000.0000);

            DecimalFormat dfCpu = new DecimalFormat("#.###");
            String prctCPU = dfCpu.format(cpuSpace.get(i));
            String prctRam = dfCpu.format(ramSpace1.get(i));
            System.out.println(String.format("%5s    | %9s ms    | %9s ms       | %9s     | %9s     | %9s       | %9s", jobID1.get(i),
                    real_wait,
                    real_complete,
                    io.get(i),
                    cpuID1.get(i),
                    prctCPU,
                    prctRam));

            
        }
        
        
        
        //calc avg wait
        
        long totalWait =0;
        int size = realWait.size();
        long avgWait = 0;
        for(int i = 0; i < 30; i++){
            totalWait += realWait.get(i);
        }
        avgWait = totalWait/size;
        System.out.println("====================");
        System.out.println();
        DecimalFormat df = new DecimalFormat("#.####");
        String avg_wait = df.format(avgWait/1000000.00);
        System.out.println("Average Wait Time: " + avg_wait + " ms");
        
        //calc avg complete
        
        long totalComplete = 0;
        int size1 = completeTimes.size();
        long avgComplete = 0;
        for(int i = 0; i < 30; i++){
            totalComplete += realComplete.get(i);
        }
        avgComplete = totalComplete/size1;
        String avg_complete = df.format(avgComplete/1000000.0000);
        System.out.println("Average Complete Time: " + avg_complete + " ms");
        
    }
    
}