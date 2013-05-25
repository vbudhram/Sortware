package steps.array.rank;

import steps.ColorIndexes;
import values.constant.Colors;
import values.constant.PaintTimes;

/*
 * This class is used to create a step structure where the
 * color has been changed to indicate which values are 
 * currently being compared.
 * 
 * For an example of how this class is used, refer to 
 * BubbleSort.java in the sortingmethods package.
 */

public class RankCompareStep extends ColorIndexes implements RankStep {
   //Variables used in toString method
   private int index1, index2;
   private int [] ranks;
   private byte [] values;

   public RankCompareStep(byte[] values, int ranks[], int index1, int index2) {
      /* 
       * Changes the colors in the color array of a step at the indexes 
       * listed (index1, index2) to the color listed (Colors.BAR_BUBBLE_COMPARED).
       * 
       */
      super(PaintTimes.BUBBLE_COMPARE, values, Colors.BAR_BUBBLE_COMPARED, index1, index2);
      
      //Used in toString method
      this.index1 = index1;
      this.index2 = index2;
      this.values = values;
      this.ranks = ranks;
   }

   public int [] getRanks() {
       return this.ranks;
   }
   
   public int [] getSortedRanks() {
       return null;
   }
   
   public byte [] getValues() {
       return null;
   }
   
   public byte [] getSortedValues() {
       return this.values;
   }
   
   public byte [] getSortedColors () {
       return null;
   }
   
   public String toString() {
      return "Comparing the bar at position " + index1 + " to the bar at position " + index2;
   }
}

