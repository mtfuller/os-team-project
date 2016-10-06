import java.util.Arrays;

public class DmaChannel {
    private Ram pseudoRAM;
    
    public DmaChannel(Ram ram) {
        pseudoRAM = ram;
    }
    
    private int effectiveAddr(int addr) {
        return addr / 4;
    }
    
    public long readRAM(int addr) {
        addr = effectiveAddr(addr);
        return pseudoRAM.readRam(addr);
    }
    
    public void writeRAM(int addr, long val) {
        addr = effectiveAddr(addr);
        pseudoRAM.writeRam(addr, val);
    }
    
    @Override
    public String toString() {
        return "DmaChannel{" +
                "pseudoRAM=" + pseudoRAM.toString() +
                '}';
    }
}