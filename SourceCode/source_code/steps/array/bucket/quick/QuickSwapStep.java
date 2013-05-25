package steps.array.bucket.quick;

import steps.ColorIndexesThreeColors;
import values.constant.Colors;
import values.constant.PaintTimes;

public class QuickSwapStep extends ColorIndexesThreeColors implements
		QuickSortVaribles {
	private final int theBinStop;
	private final int theBinStart;
	private final int thePivot;
	private final int index1;
	private final int index2;

	public QuickSwapStep(int pivot, int binStart, int binStop, byte[] values,
			int index1, int index2) {
		super(PaintTimes.QUICK_SWAP, values, Colors.BAR_QUICK_PIVOT,
				Colors.BAR_RANK_SWAPED, Colors.BAR_RANK_SWAPED, pivot, index1,
				index2);
		this.theBinStop = binStop;
		this.theBinStart = binStart;
		this.thePivot = pivot;
		this.index1 = index1;
		this.index2 = index2;
	}

	public int getPivotIndex() {
		return thePivot;
	}

	public int getBinStartIndex() {
		return theBinStart;
	}

	public int getBinStopIndex() {
		return theBinStop;
	}

	public String toString() {
		return "The bar at position " + index1
				+ " has been swapped with the bar at position " + index2;
	}
}
