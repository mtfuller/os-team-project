import com.kennesaw.OS_Module.OS_Driver;

/**
 * Created by Margaret on 9/10/2016.
 */
public class VirtualMachineSimulation {
    
    public static void main(String[] args) throws Exception {
    
        long startTime = System.nanoTime();
        
        OS_Driver simDriver = new OS_Driver();
        simDriver.runDriver();
        
        long estimatedTime = System.nanoTime() - startTime;
        
    
        System.out.println("Overall Program: " + estimatedTime/1000000 + " ms");
    
    }
    
}

