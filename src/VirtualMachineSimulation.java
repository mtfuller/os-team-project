import com.kennesaw.Analyzer.Analysis;
import com.kennesaw.OS_Module.OS_Driver;

/**
 * Created by Margaret on 9/10/2016.
 */
public class VirtualMachineSimulation {
    
    public static void main(String[] args) throws Exception {
        // Set up Analysis Module
        Analysis.initializeDataTables();

        // Initialize the OS/CPU system
        OS_Driver simDriver = new OS_Driver();

        // Clock the start time, and then run the system
        long startTime = System.nanoTime();
        simDriver.runDriver();

        // Get the start/stop time, print to the console, and send to Analysis Module
        long estimatedTime = System.nanoTime() - startTime;
        System.out.println("Overall Program Running Time: " + estimatedTime/1000000 + " ms");
        System.out.println();
        Analysis.calctoString();
    }
    
}