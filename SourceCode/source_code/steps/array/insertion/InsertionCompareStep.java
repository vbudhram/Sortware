package steps.array.insertion;

import steps.ColorIndexes;
import values.constant.Colors;
import values.constant.PaintTimes;

public class InsertionCompareStep extends ColorIndexes {
    
    private int index1, index2;
    
	public InsertionCompareStep(byte[] values, int index1, int index2) {
		super(PaintTimes.INSERTION_COMPARE, values, Colors.BAR_INSERTATION_COMPARED, index1, index2);
                
                this.index1 = index1;
                this.index2 = index2;
	}
       
   public String toString() {
      return "Comparing the bar at position " + index1 + " to the bar at position " + index2;
   }
}