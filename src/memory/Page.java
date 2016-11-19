package memory;

/**
<<<<<<< HEAD
 * Created by willw on 11/12/2016.
 */
public class Page {
    
    long[] page;
    int wordsAvailable;
    boolean isFull;
    
    public Page(){
        page = new long[4];
        wordsAvailable = 4;
        isFull = false;
    }
    
    public void writeToPage(int index, long instr){
        page[index]= instr;
        wordsAvailable--;
        if (wordsAvailable == 0) {
            isFull = true;
        }
    }
    
    public long readPage(int index){
        return page[index];
    }
    
    public boolean isPageFull(){
        return isFull;
    }

    public int getWordsAvailable(){
        return wordsAvailable;
    }
    
}
=======
 * Created by Thomas on 11/13/2016.
 */
public class Page {
}
>>>>>>> 067f8149d38e7b3e420bad63ed74748c2577e963
