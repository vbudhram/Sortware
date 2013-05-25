package steps.heap;

import steps.Step;
/*
 * This structure extends the Step class to hold byte
 * arrays for the properties theValues and theColors.
 * This structure should be used for heap sort.
 */
public class HeapStep extends Step<byte[][]> {
   public HeapStep(int paintTime, byte[] arrayValues, byte[] arrayColors,
         byte[] heapValues, byte[] heapColors) {
      super(paintTime, new byte[][] { arrayValues, heapValues }, new byte[][] {
            arrayColors, heapColors });
   }
   
   public void setColor(int index1, int index2, byte color) {
	   this.theColors[index1][index2] = color;
   }
}
