package steps.array.bucket.quick;

import steps.ColorIndexesThreeColors;
import steps.array.bucket.Bucket;
import steps.array.bucket.BucketSortVaribles;
import values.constant.Colors;
import values.constant.PaintTimes;

public class QuickCompareStep extends ColorIndexesThreeColors implements
		QuickSortVaribles, BucketSortVaribles {
	private final int theBinStop;
	private final int theBinStart;
	private final int thePivot;
	private final int index1;
	private final int index2;
	private final Bucket[] theBuckets;

	public QuickCompareStep(int pivot, int binStart, int binStop,
			byte[] values, int index1, int index2) {
		super(PaintTimes.QUICK_COMPARE, values, Colors.BAR_QUICK_PIVOT,
				Colors.BAR_RANK_COMPARED, Colors.BAR_RANK_COMPARED, pivot,
				index1, index2);
		theBuckets = new Bucket[] { new Bucket(binStart, binStop) };
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
				+ " has been compared with the bar at position " + index2;
	}

	@Override
	public Bucket[] getBuckets() {
		return theBuckets;
	}
}
