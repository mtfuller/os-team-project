/**
 * Created by Margaret on 9/20/2016.
 */
public class ShortTermScheduler {

//    DmaChannel simDMA;
    Ram simRAM;
    CPU simCPU;

    public ShortTermScheduler(Ram ram, CPU cpu) {
//        simDMA = new DmaChannel(ram);
        simCPU = cpu;
        simRAM = ram;
    }

//    public void dispatchProcess() {
//        simCPU
//    }

    public void runSTS(){
        for (int i = 0; i < simRAM.getRAMlength(); i++) {
            for (int j = 0; j < simRAM.getRAMlength(); j++) {
                System.out.println(simRAM.readRam(j));
            }
        }
    }

}
