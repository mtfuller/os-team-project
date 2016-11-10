import com.kennesaw.Analyzer.Analysis;
import com.kennesaw.OS_Module.OS_Driver;

/**
 * Created by Margaret on 9/10/2016.
 */
public class VirtualMachineSimulation {
    
    public static void main(String[] args) throws Exception {
        
        Analysis.initializeDataTables();
        
        OS_Driver simDriver = new OS_Driver();
        
        long startTime = System.nanoTime();
        simDriver.runDriver();
        
        long estimatedTime = System.nanoTime() - startTime;
        
        System.out.println("Overall Program Running Time: " + estimatedTime/1000000 + " ms");
        System.out.println();
        Analysis.calctoString();

    }
    
}