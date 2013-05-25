package steps;

import values.constant.ValueDefinitions;

public class ColorIndexesThreeColors extends SimpleArrayStep {
   private static byte[] constructorHelperShift(byte color1, byte color2, byte color3, int index1, int index2,
         int index3) {
      byte[] colors = new byte[ValueDefinitions.VALUE_COUNT_MAX + 1];
      colors[index1] = color1;
      colors[index2] = color2;
      colors[index3] = color3;
      return colors;
   }

   public ColorIndexesThreeColors(int paintTime, byte[] values, byte color1, byte color2, byte color3, int index1,
         int index2, int index3) {
      super(paintTime, values, constructorHelperShift(color1, color2, color3, index1, index2, index3));
   }
}
