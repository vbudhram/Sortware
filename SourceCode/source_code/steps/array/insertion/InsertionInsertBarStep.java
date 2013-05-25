package steps.array.insertion;

import steps.ColorIndexesAndRange;
import values.constant.Colors;
import values.constant.PaintTimes;

public class InsertionInsertBarStep extends ColorIndexesAndRange {

	private int indexSrc, indexDest;

	public InsertionInsertBarStep(byte[] values, int indexSrc, int indexDest) {
		super(PaintTimes.INSERTION_SWAP, values,
				Colors.BAR_INSERTATION_INSERTED, Colors.BAR_DEFAULT, indexSrc,
				indexDest);
		this.indexSrc = indexSrc;
		this.indexDest = indexDest;
	}

	@Override
	public String toString() {
		return "Bar inserted at position " + indexSrc;
	}
}
