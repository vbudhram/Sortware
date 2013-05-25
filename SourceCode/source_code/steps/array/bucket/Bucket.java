package steps.array.bucket;

public class Bucket {
	public int theStartIndex;
	public int theEndIndex;
	public int theMaxValue;
	public int theMinValue;

	public Bucket(int startIndex, int endIndex) {
		theStartIndex = startIndex;
		theEndIndex = endIndex;
	}

	public Bucket(int startIndex, int endIndex, int maxValue, int minValue) {
		theStartIndex = startIndex;
		theEndIndex = endIndex;
		theMaxValue = maxValue;
		theMinValue = minValue;
	}
}
