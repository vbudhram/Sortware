package steps.array.bubble;

import steps.ColorIndexes;
import values.constant.Colors;
import values.constant.PaintTimes;

/*
 * This class is used to create a step structure where the
 * color has been changed to indicate which values are 
 * currently being swaped.
 * 
 * For an example of how this class is used, refer to 
 * BubbleSort.java in the sortingmethods package.
 */

public class BubbleSwapStep extends ColorIndexes {
   //Variables used in toString method
    private int index1, index2;

   public BubbleSwapStep(byte[] values, int index1, int index2) {
      /* 
       * Changes the colors in the color array of a step at the indexes 
       * listed (index1, index2) to the color listed (Colors.BAR_BUBBLE_SWAPED).
       * 
       */       
      super(PaintTimes.BUBBLE_SWAP, values, Colors.BAR_BUBBLE_SWAPED, index1, index2);
      
      //Used in toString method
      this.index1 = index1;
      this.index2 = index2;
   }

   public String toString() {
      return "The bar at position " + index1 + " has been swapped with the bar at position " + index2;
   }
}
