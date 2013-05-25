package steps;

/*
 * This structure extends the Step class to hold byte
 * arrays for the properties theValues and theColors.
 * This structure should be used for sorts that 
 * will require simple arrays to represent the data
 * as it is sorted.
 * 
 * For an example of how it can be used, look at
 * ColorIndexes.java in the steps package.
 */
public class SimpleArrayStep extends Step<byte[]> {
   public SimpleArrayStep(int paintTime, byte[] values, byte[] colors) {
      super(paintTime, values, colors);
   }
   
   public void setColor(int index, byte color) {
	   this.theColors[index] = color;
   }
}
