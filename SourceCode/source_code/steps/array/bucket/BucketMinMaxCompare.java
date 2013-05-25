package steps.array.bucket;

import steps.ColorIndexesFourColors;
import values.constant.Colors;
import values.constant.PaintTimes;

public class BucketMinMaxCompare extends ColorIndexesFourColors {
   private final int index1, index2, indexMax, indexMin;

   public BucketMinMaxCompare(byte[] values, int indexMax, int indexMin, int index1, int index2) {
      super(PaintTimes.BUCKET_MINMAXCOMPARE, values, Colors.BAR_BUCKET_MAX, Colors.BAR_BUCKET_MIN,
            Colors.BAR_BUCKET_COMPARED, Colors.BAR_BUCKET_COMPARED, indexMax, indexMin, index1, index2);
      this.index1 = index1;
      this.index2 = index2;
      this.indexMin = indexMin;
      this.indexMax = indexMax;
   }

   public String toString() {
      return "The bar at position " + index1 + " has been compared with the bar at position " + index2
            + " Current Min is at " + indexMin + " Current Min is at " + indexMax;
   }
}
