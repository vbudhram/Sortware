package steps.array.selection;

import steps.ColorIndexes;
import values.constant.Colors;
import values.constant.PaintTimes;

public class SelectionSmallestStep extends ColorIndexes {
    
    private int index;

	public SelectionSmallestStep(byte[] values, int index) {
		super(PaintTimes.SELECTION_COMPARE, values, Colors.BAR_SELECTION_SMALLEST_INDEX, index);
                
                this.index = index;
	}
        
        public String toString() {
          return index+" is the index of the new smallest value";
   }

}
