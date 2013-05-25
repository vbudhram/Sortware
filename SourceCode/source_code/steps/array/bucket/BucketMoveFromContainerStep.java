package steps.array.bucket;

import values.constant.Colors;
import values.constant.PaintTimes;

public class BucketMoveFromContainerStep extends BucketContainerStep implements BucketSortVaribles {
   private final int index;

   public BucketMoveFromContainerStep(byte[] values, int index, Bucket[] buckets, Container[] containers) {
      super(PaintTimes.BUCKET_MOVE, values, Colors.BAR_BUCKET_MOVE, new int[] { index }, containers);
      theBuckets = buckets;
      this.index = index;
   }

   private final Bucket[] theBuckets;

   @Override
   public Bucket[] getBuckets() {
      return theBuckets;
   }

   public String toString() {
      return "Moving from Container to index " + index;
   }
}
