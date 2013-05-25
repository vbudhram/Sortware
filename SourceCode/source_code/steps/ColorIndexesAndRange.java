package steps;

import values.constant.ValueDefinitions;

public class ColorIndexesAndRange extends SimpleArrayStep {
	private static byte[] constructorHelper(byte indexColor, byte rangeColor,
			int index, int rangeEnd) {
		byte[] colors = new byte[ValueDefinitions.VALUE_COUNT_MAX];
		colors[index] = indexColor;
		for (int i = index + 1; i <= rangeEnd; i++) {
			colors[i] = rangeColor;
		}

		return colors;
	}

	public ColorIndexesAndRange(int paintTime, byte[] values, byte indexColor,
			byte rangeColor, int index, int rangeEnd) {
		super(paintTime, values, constructorHelper(indexColor, rangeColor,
				index, rangeEnd));
	}

}
