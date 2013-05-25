package steps;

import values.constant.ValueDefinitions;

public class ColorIndexesFourColors extends SimpleArrayStep {
   private static byte[] constructorHelperShift(byte color1, byte color2, byte color3, byte color4, int index1,
         int index2, int index3, int index4) {
      byte[] colors = new byte[ValueDefinitions.VALUE_COUNT_MAX + 1];
      colors[index1] = color1;
      colors[index2] = color2;
      colors[index3] = color3;
      colors[index4] = color4;
      return colors;
   }

   public ColorIndexesFourColors(int paintTime, byte[] values, byte color1, byte color2, byte color3, byte color4,
         int index1, int index2, int index3, int index4) {
      super(paintTime, values, constructorHelperShift(color1, color2, color3, color4, index1, index2, index3, index4));
   }
}
