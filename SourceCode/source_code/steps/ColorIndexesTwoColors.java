package steps;

import values.constant.ValueDefinitions;

public class ColorIndexesTwoColors extends SimpleArrayStep {
	private static byte[] constructorHelperShift(byte firstColor,
			byte secondColor, int index1, int index2) {
		byte[] colors = new byte[ValueDefinitions.VALUE_COUNT_MAX + 1];
		colors[index2] = firstColor;
		colors[index1] = secondColor;
		return colors;
	}

	public ColorIndexesTwoColors(int paintTime, byte[] values, byte firstColor,
			byte secondColor, int index1, int index2) {
		super(paintTime, values, constructorHelperShift(firstColor,
				secondColor, index1, index2));
	}
}
