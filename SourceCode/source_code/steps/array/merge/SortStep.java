package steps.array.merge;

import steps.SimpleArrayStep;
import values.constant.Colors;
import values.constant.PaintTimes;

public class SortStep extends SimpleArrayStep{
private boolean l;
public SortStep ( byte [] left, byte [] right, boolean l) {
	super(PaintTimes.MERGE_SORT, constructorValueHelper(left,right), constructorColorHelper(left,right,l));
	this.l=l;
  }
private static byte[] constructorValueHelper(byte [] left, byte [] right) {
	byte[] value = new byte[left.length+right.length];

	for (int i=0; i <left.length;i++){
		value[i]=left[i];
	}
	for (int j=left.length;j<value.length;j++){
		value[j]=right[j-left.length];
	}
	
	
return value;
}

private static byte[] constructorColorHelper(byte [] left, byte [] right, boolean l) {
	byte[] color = new byte[left.length+right.length];
	if (l){
	for (int i=0; i < color.length; i++){
		if (i < left.length){
			color[i]=Colors.BAR_MERGE_SORT;
		}
		else {
			color[i]=Colors.BAR_MERGE_GRAY;
		}
		
	}
	}
	else { // switch the coloring
		for (int i=0; i < color.length; i++){
			if (i < left.length){
				color[i]=Colors.BAR_MERGE_GRAY;
			}
			else {
				color[i]=Colors.BAR_MERGE_SORT;
			}
			
		}
	}
	
	
	return color;
}
public String toString() {
	
	if (l){
		return "Selecting the left half of the array to call the sort method";
	}
	else {
		return "Selecting the right half of the array to call the sort method";
	}
	
}
	
}


