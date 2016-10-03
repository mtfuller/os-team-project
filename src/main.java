/**
 * Created by willw on 9/29/2016.
 */
public class main {
    public static void main(String[] args) {
        Disk d = new Disk();
        Ram r = new Ram();

        int[] job1 = {
                0xC050005C,
                0x4B060000,
                0x4B010000,
                0x4B000000,
                0x4F0A005C,
                0x4F0D00DC,
                0x4C0A0004,
                0xC0BA0000,
                0x42BD0000,
                0x4C0D0004,
                0x4C060001,
                0x10658000,
                0x56810018,
                0x4B060000,
                0x4F0900DC,
                0x43970000,
                0x05070000,
                0x4C060001,
                0x4C090004,
                0x10658000,
                0x5681003C,
                0xC10000AC,
                0x92000000,
                0x0000000A,
                0x00000006,
                0x0000002C,
                0x00000045,
                0x00000001,
                0x00000007,
                0x00000000,
                0x00000001,
                0x00000005,
                0x0000000A,
                0x00000055,
                0x00000000,
                0x00000000,
                0x00000000,
                0x00000000,
                0x00000000,
                0x00000000,
                0x00000000,
                0x00000000,
                0x00000000,
                0x00000000,
                0x00000000,
                0x00000000,
                0x00000000,
                0x00000000,
                0x00000000,
                0x00000000,
                0x00000000,
                0x00000000,
                0x00000000,
                0x00000000,
                0x00000000,
                0x00000000,
                0x00000000,
                0x00000000,
                0x00000000,
                0x00000000,
                0x00000000,
                0x00000000,
                0x00000000,
                0x00000000,
                0x00000000,
                0x00000000,
                0x00000000,
        };
        for(int i = 0; i < job1.length; i++) {
            d.writeDisk(i, job1[i]);
        }
        for(int i = 0; i< job1.length; i++) {
            System.out.println(d.readDisk(i));

        }
        System.out.println("kasjhd");
        for(int i = 0; i< job1.length; i++){
            r.writeRam(i, job1[i]);
        }
        for(int i = 0; i < job1.length; i++){
            System.out.println(r.readRam(i));
        }
        System.out.println(d.getDiskSpaceAvaliable());
        System.out.println(d.getAmountInDisk());
        System.out.println(r.getRamSpaceAvaliable());
        System.out.println(r.getAmountInRam());
    }
}
