package steps.array.insertion;

import steps.ColorIndexesAndRange;
import values.constant.Colors;
import values.constant.PaintTimes;

public class InsertionRemoveBarStep extends ColorIndexesAndRange {
    
    private int indexSrc, indexDest;

	public InsertionRemoveBarStep(byte[] values, int indexSrc, int indexDest) {
		super(PaintTimes.INSERTION_SWAP, values,
				Colors.BAR_DEFAULT,
				Colors.BAR_INSERTATION_REMOVED, indexSrc, indexDest);
                
                this.indexSrc = indexSrc;
                this.indexDest = indexDest;
	}
        
    @Override
   public String toString() {
      return "The bar removed from position " + indexDest;
   }
}
