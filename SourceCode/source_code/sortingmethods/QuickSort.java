package sortingmethods;

import java.util.ArrayList;

import steps.SimpleArrayStep;
import steps.Step;
import steps.array.bucket.quick.QuickCompareStep;
import steps.array.bucket.quick.QuickSwapStep;
import utils.Convert;
import values.constant.ValueDefinitions;

/**
 * This Sorting Method take a list of values. Picks a value from that list.
 * Divides the list into 2 lists: one list that has values greater then the
 * pivot and a list containing the others. Keeps reapplying this procedure until
 * the list has less then 2 values.
 *
 * @author esnellman
 *
 */
public class QuickSort extends SortingMethod {
	@Override
	public String getName() {
		return "Quick Sort";
	}

	@Override
	public Step[][] solve(int[] initialValues) {
		byte[] values = new byte[initialValues.length];
		for (int i = 0; i < initialValues.length; i++)
			values[i] = (byte) initialValues[i];
		ArrayList<ArrayList<Step>> allSteps = new ArrayList<ArrayList<Step>>();
		int n = initialValues.length;
		sort(allSteps, values, 0, n - 1);
		return Convert.toArray(allSteps);
	}

	private void sort(ArrayList<ArrayList<Step>> allSteps, byte[] values,
			int start, int end) {
		if (end - start < 1)
			return;
		ArrayList<Step> steps = new ArrayList<Step>();
		int pivotIndex = start;
		if (end - start == 1) {
			steps.add(new QuickCompareStep(pivotIndex, start, end, values.clone(),
					start, end));
			if (values[start] > values[end]) {
				byte temp = values[start];
				values[start] = values[end];
				values[end] = temp;
				steps.add(new QuickSwapStep(pivotIndex, start, end, values.clone(),
						start, end));
			}
		} else {
			for (int i = start + 1; i <= end; i++) {
				steps.add(new QuickCompareStep(pivotIndex, start, end, values
						.clone(), i, pivotIndex));
				if (values[i] < values[pivotIndex]) {
					for (int j = i; j > pivotIndex; j--) {
						byte temp = values[j];
						values[j] = values[j - 1];
						values[j - 1] = temp;
						if (j - 1 == pivotIndex)
							pivotIndex++;
						steps.add(new QuickSwapStep(pivotIndex, start, end,
								values.clone(), j - 1, j));
					}
				}
			}
		}
		allSteps.add(steps);
		sort(allSteps, values, start, pivotIndex - 1);
		sort(allSteps, values, pivotIndex + 1, end);
	}

	public static void main(String[] args) {
		int[] unsorted = new int[] { 22, 21, 2, 21, 11, 31, 19, 19, 19, 49, 13,
				31, 16, 19, 22, 8 };
		StringBuilder b = new StringBuilder();
		b.append("Unsorted ");
		for (int i : unsorted)
			b.append(i + ", ");
		System.out.println(b);
		Step[][] steps = (new QuickSort()).solve(unsorted);
		int i = 1;
		for (Step[] step : steps) {
			int j = 1;
			for (Step substep : step) {
				StringBuilder b2 = new StringBuilder();
				for (int num : ((SimpleArrayStep) substep).theValues) {
					if (num != ValueDefinitions.VALUE_UNDEFINED) {
						b2.append(num + ", ");
					}
				}
				System.out
						.printf(
								"Step [%1$2d,%2$2d] %3$s\nStep [%1$2d,%2$2d]  %4$s\n\n",
								i, j, substep.toString(), b2.toString());
				j++;
			}
			i++;
		}
		System.out.print("\nSorted   ");
		byte[] sorted = ((SimpleArrayStep) steps[steps.length - 1][steps[steps.length - 1].length - 1]).theValues;
		for (int num : sorted) {
			System.out.print(num + ", ");
		}
	}
}
