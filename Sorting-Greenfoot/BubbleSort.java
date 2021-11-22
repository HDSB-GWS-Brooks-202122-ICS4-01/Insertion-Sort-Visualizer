import java.util.*;
/**
 * Write a description of class Temp here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class BubbleSort  
{
    // instance variables - replace the example below with your own
    private int x;

    /**
     * Constructor for objects of class Temp
     */
    public BubbleSort()
    {
    }

    /**
     * An example of a method - replace this comment with your own
     * 
     * @param  y   a sample parameter for a method
     * @return     the sum of x and y 
     */
    public void sort(List blockList)
    {
        // put your code here
        for (int i=0; i < blockList.size(); i++ ){
            for (int j =1; j < blockList.size(); j++) {
                
                Block firstBlock = (Block)blockList.get(j-1);
                Block secondBlock = (Block)blockList.get(j);
                
                if ( firstBlock.getValue() > secondBlock.getValue()) {
                    Object temp = blockList.get(j-1);
                    blockList.set(j-1, blockList.get(j));
                    blockList.set(j, temp);
                    
                    
                }
            }
        }
        //        blockList.set(index, element)
    }
}
