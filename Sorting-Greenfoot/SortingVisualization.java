import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)
import java.util.*;
/**
 * Creates a world which can be used to visualize sorting methods by arranging "blocks" on 
 * the screen.  Each block is it's own object and has a value associated with it.  This value
 * is represented visually by it's width and a number display.
 * 
 * This might help you when programming - https://docs.oracle.com/javase/8/docs/api/java/util/List.html
 * 
 * 
 * @author C. Brooks-Prenger
 * @version 1.0
 */
public class SortingVisualization extends World
{
    int maxOfRange;  //Largest value for block (can't be less than 1)
    List blockList;  //List containing all of the block objects
    boolean debug = true;           //Shows extra info and debugging statements if true.
    
    /**
     * Constructor for objects of class SortingVisualization.
     * 
     */
    public SortingVisualization()
    {    
        // Create a new world with 600x400 cells with a cell size of 1x1 pixels.
        super(400, 400, 1); 
        
        
        maxOfRange = 100;       //Set the block values to range between 0 and 100 (exclusive)
        int numNewBlocks = 20;  //Note, 133 blocks maximum. Adding 134 blocks or more causes problems...bug in my code or a greenfoot limitation? Not sure and don't have the time to find out.
        
        if (debug) System.out.println("Generate the blocks for the world.");
        if (debug) System.out.println("maxOfRange: "+maxOfRange+ "   numNewBlocks: " + numNewBlocks);
        
        int sizeBlock = this.getHeight()/numNewBlocks;
        if (debug) System.out.println("sizeBlock: "+sizeBlock);
        
        //Create block objects and assign them to a list
        blockList = new ArrayList();
        for (int i = 0; i < numNewBlocks; i++) {
            blockList.add(new Block(sizeBlock,getWidth(),Greenfoot.getRandomNumber(maxOfRange),maxOfRange,debug)); 
            addObject( (Actor)blockList.get( blockList.size()-1 ),  getWidth()/2, (i*sizeBlock)+(sizeBlock/2) );
        }
        
        
        //Instantiate your sort visualizer here
        //Instant Bubble Sort
        /*
        BubbleSort sort = new BubbleSort();
        sort.sort(blockList);
        //*/
       
        //Step by Step Bubble Sort
        //Replace this with your own vizualizer class
        BubbleSortVisualizerBrooks sort = new BubbleSortVisualizerBrooks();
        addObject(sort,10,10);
        //*/
        
        
        displayList();
    }
    
    /**
     * Display the list of blocks on the screen in same order as they are stored in the list
     */
    public void displayList() {
        int sizeBlock = this.getHeight()/blockList.size();
        for (int i = 0; i < blockList.size(); i++) {
            Block currentBlock = (Block)blockList.get(i);
            currentBlock.setLocation(getWidth()/2, (i*sizeBlock)+(sizeBlock/2) );
        }
        
    }
    
    /**
     * Return the max value that the block are able to have
     * 
     * @return The maximum integer value the blocks are allowed to have
     */
    public int getMaxOfRange() {
        return maxOfRange;
    }
    
    /**
     * Return the list of blocks
     * 
     * @return The list of blocks to be sorted
     */
    public List getBlockList() {
        return blockList;
    }
    
    
    
}
