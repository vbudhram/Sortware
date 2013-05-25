package steps.array.merge;

import steps.SimpleArrayStep;
import values.constant.PaintTimes;
import values.constant.Colors;

public class MergeStep extends SimpleArrayStep {

	private int count;
	private byte [] left;
	private byte [] right;
	private byte [] merged;
	public MergeStep(byte[] merged, int count, byte [] left, byte [] right) {
      super(PaintTimes.MERGE_STEP, constructorHelperValues(merged,left,right), constructorHelperColors(count,left,right));
      this.left=left;
      this.right=right;
      this.count=count;
      this.merged=merged;
      
   }
	private static byte [] constructorHelperColors (int count, byte [] left, byte [] right) {
		int llength; int rlength;
		if (left== null){
			llength=0;
		}
		else {
			llength=left.length;
		}
		
		if (right==null){
			rlength=0;
		}
		else {
			rlength = right.length;
		}
		
		
		
		byte color [] = new byte[count+llength+rlength];
		int i;
		for (i=0; i < count; i++){
			color[i]=Colors.BAR_MERGE_MERGED;
		}
		for (; i<count+llength;i++){
			color[i]=Colors.BAR_MERGE_LEFT;
		}
		for (; i<count+llength+rlength;i++){
			color[i]=Colors.BAR_MERGE_RIGHT;	
		}
		
		return color;
	}
	private static byte [] constructorHelperValues (byte [] values, byte [] left, byte [] right) {
		int llength; int rlength;
		if (left== null){
			llength=0;
		}
		else {
			llength=left.length;
		}
		
		if (right==null){
			rlength=0;
		}
		else {
			rlength = right.length;
		}
		
		byte value[] = new byte[values.length+llength+rlength];
		
		int lefti=0;
		int righti=0;
		for (int i=0; i < value.length; i++){
			if (i<values.length){
				value[i]=values[i];
			}
			if (i >= values.length && i<(values.length+llength)){
				value[i]=left[lefti];
				lefti++;
			}
			if (i>= (values.length + llength) && i < (values.length+left.length+rlength)){
				value[i]=right[righti];
				righti++;
			}
				
		}
		
		return value;
	}
	public String toString() {

		return "merging the elements";
	
}
	  
}
