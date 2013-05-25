package steps.array.bucket;

import steps.ColorIndexes;
import values.constant.Colors;

public class BucketContainerStep extends ColorIndexes implements Containers{
   Container[] theContainers;

   public BucketContainerStep(int paintTime, byte[] values, byte color, int[] indexes, Container[] containers) {
      super(paintTime, values, Colors.BAR_DEFAULT, new int[0]);
      theContainers = containers;
   }

   @Override
   public Container[] getContainers() {
      return theContainers;
   }
}
