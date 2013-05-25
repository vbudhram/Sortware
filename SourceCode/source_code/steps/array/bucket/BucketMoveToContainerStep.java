package steps.array.bucket;

import values.constant.Colors;
import values.constant.PaintTimes;

public class BucketMoveToContainerStep extends BucketContainerStep implements
		BucketSortVaribles {
   public final int index;
	public BucketMoveToContainerStep(byte[] values, int index, Bucket[] buckets,
			Container[] containers) {
		super(PaintTimes.BUCKET_MOVE, values, Colors.BAR_BUCKET_MOVE,
				new int[] { index }, containers);
		theBuckets = buckets;
		this.index=index;
	}

	private final Bucket[] theBuckets;

	@Override
	public Bucket[] getBuckets() {
		return theBuckets;
	}
	
	public String toString() {
      return "Moving to Container from index " + index;
   }
}
