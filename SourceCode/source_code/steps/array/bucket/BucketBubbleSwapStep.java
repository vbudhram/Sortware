package steps.array.bucket;

import steps.ColorIndexes;
import values.constant.Colors;
import values.constant.PaintTimes;

public class BucketBubbleSwapStep extends ColorIndexes implements BucketSortVaribles {
   private final Bucket[] theBuckets;
   private final int index1, index2;

   public BucketBubbleSwapStep(Bucket[] buckets, byte[] values, int index1, int index2) {
      super(PaintTimes.BUCKET_SWAP, values, Colors.BAR_BUCKET_SWAPED, index1, index2);
      theBuckets = buckets;
      this.index1 = index1;
      this.index2 = index2;
   }

   @Override
   public Bucket[] getBuckets() {
      return theBuckets;
   }

   public String toString() {
      return "The bar at position " + index1 + " has been swapped with the bar at position " + index2;
   }
}
