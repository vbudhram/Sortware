package steps.array.insertion;

import steps.ColorIndexesTwoColors;
import values.constant.Colors;
import values.constant.PaintTimes;

public class InsertionShiftBarStep extends ColorIndexesTwoColors {

	private int indexSrc, indexDest;

	public InsertionShiftBarStep(byte[] values, int indexSrc, int indexDest) {
		super(PaintTimes.INSERTION_SWAP, values,
				Colors.BAR_INSERTATION_REMOVED, Colors.BAR_INSERTATION_SHIFTED,
				indexSrc, indexDest);
		this.indexSrc = indexSrc;
		this.indexDest = indexDest;
	}

	public String toString() {
		return "Bar shifted to position " + (indexSrc);
	}
}

// public class InsertionShiftBarStep extends ColorIndexes {
//    
// private int indexSrc, indexDest;
//
// public InsertionShiftBarStep(byte[] values, int indexSrc, int indexDest) {
// super(PaintTimes.INSERTION_SWAP, values,
// Colors.BAR_EMPTY,
// Colors.BAR_INSERTATION_SHIFTED, indexSrc, indexDest);
//                
// this.indexSrc = indexSrc;
// this.indexDest = indexDest;
// }
//        
// @Override
// public String toString() {
// return "Bar SHIFTED to position " + (indexSrc + 1);
// }
// }
