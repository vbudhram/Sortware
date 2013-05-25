package steps;

import values.constant.ValueDefinitions;

public class ColorIndexandIndexs extends SimpleArrayStep {
   private static byte[] constructorHelper(byte indexColor, int index,
         byte indexsColor, int... indexes) {
      byte[] colors = new byte[ValueDefinitions.VALUE_COUNT_MAX];
      colors[index] = indexColor;
      for (int index2 : indexes) {
         colors[index2] = indexsColor;
      }
      return colors;
   }

   public ColorIndexandIndexs(int paintTime, byte[] values, byte indexColor,
         int index, byte indexsColor, int... indexes) {
      super(paintTime, values, constructorHelper(indexColor, index,
            indexsColor, indexes));
   }
}
