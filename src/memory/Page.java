package memory;

/**
<<<<<<< HEAD
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
=======
 * Created by Thomas on 11/13/2016.
 */
public class Page {
}
>>>>>>> 067f8149d38e7b3e420bad63ed74748c2577e963
