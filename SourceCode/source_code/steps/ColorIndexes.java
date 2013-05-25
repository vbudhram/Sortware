package steps;

import values.constant.Colors;
import values.constant.ValueDefinitions;

/*
 * This class is used to change the colors of the values
 * depending on their state. 
 */
public class ColorIndexes extends SimpleArrayStep {
   
    /*
     * This method initializes the array of colors that will be passed to 
     * the constructor. 
     * 
     * For an example of how this class can be used, look at
     * BubbleCompareStep.java and BubbleSwapStep.java in the 
     * steps.array.bubble package.
     */
    private static byte[] constructorHelper(byte color, int... indexes) {
      
       byte[] colors = new byte[ValueDefinitions.VALUE_COUNT_MAX];
       /*
        * All values in the color array are initialized to the 
        * default color. 
        */
       for (int i = 0; i < ValueDefinitions.VALUE_COUNT_MAX; ++i) {
         colors[i] = Colors.BAR_DEFAULT;
        }
       /*
        * The color at the specific indices is changed to the color 
        * specified in the parameter of the method.
        */
       for (int index : indexes)
         colors[index] = color;
       
       //a byte array is returned
       return colors;
   }

   public ColorIndexes(int paintTime, byte[] values, byte color, int... indexes) {
      /*
       * The color array that is passed into the super constructor depends on 
       * the color array that is returned by the method constructorHelper.
       */
      super(paintTime, values, constructorHelper(color, indexes));
   }
}
