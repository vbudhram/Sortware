package steps.array.selection;

import steps.ColorIndexes;
import values.constant.Colors;
import values.constant.PaintTimes;

public class SelectionSwapStep extends ColorIndexes {
    
    private int index1, index2;

	public SelectionSwapStep(byte[] values, int index1, int index2) {
		super(PaintTimes.SELECTION_SWAP, values, Colors.BAR_SELECTION_SWAPED, index1,
				index2);
                
                this.index1 = index1;
                this.index2 = index2;
	}
        
   public String toString() {
      return "The bar at position " + index1 + " has been swapped with the bar at position " + index2;
   }

}
