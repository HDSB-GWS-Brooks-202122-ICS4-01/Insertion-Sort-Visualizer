import greenfoot.*;  // (World, Actor, GreenfootImage, Greenfoot and MouseInfo)

/**
 * Blocks for use in sorting visualization.  Each block is it's own object and has a value associated with it.  This value
 * is represented visually by it's width and a number display.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class Block extends Actor
{
    private int height;
    private int width;
    private int value;
    private boolean debug;
    
    
    /**
     * Create a block and specify it's initial settings
     * 
     * @param heightIn - The height of the block to be created (can't be < 1)
     * @param widthIn - The width of the interface that the block will be displayed in
     * @param valueIn - The value of the block to be created
     * @param maxValue - The maximum value that a block can be
     * @param debug (optional) - An optionol parameter which if true turns on debugging statements.  Defaults to false.
     */
    public Block(int heightIn, int widthOfInterface, int valueIn, int maxValue, boolean... debugFlag) {

        if (debugFlag.length > 0) {
            debug = debugFlag[0];
        } else {
            debug = false;
        }
        
        height = heightIn;
        
        width = (int)( ( (float)valueIn/maxValue)*widthOfInterface);        
        value = valueIn;
      
        createDisplayImage();
        
        if (debug) System.out.println("Block Created - width: "+width + "  height:  "+height+ " value: "+ value + " maxValue: "+maxValue);
        
    }

    /**
     * Create a block and with default initial settings (only used for testing purposes)
     * 
     * @param heightIn - The height of the block to be created (can't be < 1)
     * @param widthIn - The width of the interface that the block will be displayed in
     * @param valueIn - The value of the block to be created
     * @param maxValue - The maximum value that a block can be
     */
    public Block() {
        value = 99; 
        height = 20;
        //width = (int)(57.0/100*100);
        width = (int)((value/100.0)*400); //value/maxValue*widthOfInterface
        //System.out.println(  Integer.toString(width));
        
        createDisplayImage();
    }
    
    /**
     * Return the width of the block
     * 
     * @return The integer width of the block
     */
    public int getWidth() {
        return width;
    }
    
    
    /**
     * Return the value of the block
     * 
     * @return The integer value of the block
     */
    public int getValue() {
        return value;
    }
    
    /**
     * Create the image of the block to be used by greenfoot for display
     */
    protected void createDisplayImage() {
        /*The Ratio of width:height for letters in greenfoot is 44:100 = 0.44 (determined experimentally on my machine
         * with the code below 
       
        String testString = "";
        for (int i=0; i<=9; i++) {
            GreenfootImage singleLetter = new GreenfootImage( Integer.toString(i),100,Color.RED,Color.BLACK);        
            System.out.println(Integer.toString(i) + " - " + Integer.tString(singleLetter.getWidth()));
        }
          */
         
        //Determind the number of " " needed to pad the block to the correct width.
        int numberSpacesNeeded = (int) (width/ (height*0.3));
        
        //Add the spacers to the block text
        String spacer = "";
        for (int i = 0; i<numberSpacesNeeded; i++){
            spacer += " ";
        }
        //Create and set the image for the block
        GreenfootImage box = new GreenfootImage( Integer.toString(value)+spacer,height,Color.RED,Color.BLACK);
        setImage(box);
    }
    
    /**
     * Count the number of digits in an integet number
     * 
     * @return The number of digits in the number
     */
    private int countDigits(int valueIn) {
        int numDigits = 1;
        if (valueIn < 0) valueIn *= -1;
        
        while (valueIn/10 >0){
            numDigits++;
            valueIn = valueIn/10;
        }
        return numDigits;
    }
}
