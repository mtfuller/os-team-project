package memory;

/**
 * Created by willw on 11/12/2016.
 */
public class Page {
    public static final byte PAGE_SIZE = 4;
    long[] page;
    int wordsAvailable;
    boolean isFull;
    
    public Page(){
        page = new long[PAGE_SIZE];
        wordsAvailable = 4;
        isFull = false;
    }

    public Page(Page sourcePage) {
        page = new long[PAGE_SIZE];
        for (byte b = 0; b < PAGE_SIZE; b++) {
            page[b] = sourcePage.readPage(b);
        }
        wordsAvailable = sourcePage.getWordsAvailable();
        isFull = sourcePage.isPageFull();
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
