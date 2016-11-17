package memory;

/**
 * Created by willw on 11/12/2016.
 */
public class Page {
    long[] page;
    public Page(){
        page = new long[4];
    }
    public void writeToPage(int index, long instr){
        page[index]= instr;
    }
    public long readPage(int index){
        return page[index];
    }

}