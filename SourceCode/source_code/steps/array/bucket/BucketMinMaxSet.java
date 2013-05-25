package steps.array.bucket;

import steps.ColorIndexesTwoColors;
import values.constant.Colors;
import values.constant.PaintTimes;

public class BucketMinMaxSet extends ColorIndexesTwoColors {
   private final int indexMax, indexMin;

   public BucketMinMaxSet(byte[] values, int indexMax, int indexMin) {
      super(PaintTimes.BUCKET_MINMAXCOMPARE, values, Colors.BAR_BUCKET_MAX, Colors.BAR_BUCKET_MIN, indexMax, indexMin);
      this.indexMin = indexMin;
      this.indexMax = indexMax;
   }

   public String toString() {
      return "Current Min is at " + indexMin + " Current Min is at " + indexMax;
   }
}
