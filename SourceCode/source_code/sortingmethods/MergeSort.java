package sortingmethods;

import java.util.ArrayList;

import steps.Step;
import steps.array.merge.MergeStep;
import steps.array.merge.SortStep;
import utils.Convert;
import values.constant.Colors;
import values.constant.PaintTimes;

public class MergeSort extends SortingMethod {

	@Override
	public String getName() {
		return values.constant.Sorts.SORTING_METHODS[values.constant.Sorts.MERGE_SORT];
	}

	private ArrayList<Step> currentSteps;
	private ArrayList<ArrayList<Step>> allSteps;
	private int count;
	private byte[] f;

	/*
	 * solve calls the sort method to begin the mergesort the steps are added
	 * within the sort and merge functions of this class while the actual sort
	 * is taking place
	 * 
	 */

	@Override
	public Step[][] solve(int[] initialValues) {
		ArrayList<ArrayList<Step>> allSteps = new ArrayList<ArrayList<Step>>();
		currentSteps = new ArrayList<Step>();
		byte v[] = new byte[initialValues.length];

		for (int i = 0; i < initialValues.length; i++) {
			v[i] = (byte) initialValues[i];
		}
		f = v;
		count = 0;
		sort(v);
		allSteps.ensureCapacity(allSteps.size() + 1);
		allSteps.add(currentSteps);

		return Convert.toArray(allSteps);

	}

	public byte[] sort(byte[] daValues) {

		if (daValues.length < 2) {
			return daValues;
		}

		// Create an array to hold the left half of the whole array
		// and copy the left half of whole into the new array.

		byte[] daLeft = new byte[daValues.length / 2];
		System.arraycopy(daValues, 0, daLeft, 0, daLeft.length);

		// Create an array to hold the right half of the whole array
		// and copy the right half of whole into the new array.

		byte[] daRight = new byte[daValues.length - daLeft.length];
		System.arraycopy(daValues, daLeft.length, daRight, 0, daRight.length);

		// *********** sortstep ******************** left

		currentSteps.ensureCapacity(currentSteps.size() + 1);
		currentSteps.add(new SortStep(daLeft, daRight, true));

		if (daLeft.length > 1) {
			daLeft = sort(daLeft);
		}

		if (daLeft.length < 2) {
			count += daLeft.length;
		}

		// *********** sortstep ******************** right

		currentSteps.ensureCapacity(currentSteps.size() + 1);
		currentSteps.add(new SortStep(daLeft, daRight, false));

		if (daRight.length > 1) {

			daRight = sort(daRight);
		}
		if (daRight.length < 2) {
			count += daRight.length;
		}

		byte[] t = merge(daLeft, daRight);
		// update locations of the display array while a merge occured...

		// create a temp array to hold the old left and right

		byte[] test = new byte[daLeft.length + daRight.length];
		for (int i = 0; i < test.length; i++) {
			if (i < daLeft.length) {
				test[i] = daLeft[i];
			} else {
				test[i] = daRight[i - daLeft.length];
			}
		}

		// find the temp array in f and replace it with the merged array

		boolean stop = false; // ends the loop once the array is copied
		int i;
		for (i = 0; !stop; i++) {

			if (f[i] == test[0]) {

				boolean temp = true;

				for (int j = 0; j < test.length; j++) {

					if (f[j + i] != test[j]) {
						temp = false;
					}

				}

				if (temp) {
					for (int j = 0; j < test.length; j++) {
						f[j + i] = t[j];
					}
					stop = true;
				}

			}
		}
		return t;

	}

	/*
	 * returns an array from temp[begin] to temp[end]
	 */

	public byte[] mergeStepHelper(byte temp[], int begin, int end) {
		byte t[] = new byte[end - begin];
		for (int i = 0; i < t.length; i++) {
			t[i] = temp[begin];
			begin++;
		}
		return t;
	}

	public byte[] merge(byte[] left, byte[] right) {

		// temp holds the merged array

		byte[] temp = new byte[left.length + right.length];

		// indexes

		int lefti = 0, righti = 0, tempi = 0;

		// As long as neither the left nor the right array has
		// been used up, keep taking the smaller of them and adding it to temp

		while (lefti < left.length && righti < right.length) {

			if (left[lefti] < right[righti]) {

				temp[tempi] = left[lefti];

				// *********** mergestep ********************

				currentSteps.ensureCapacity(currentSteps.size() + 1);
				currentSteps.add(new MergeStep(mergeStepHelper(temp, 0, tempi),
						tempi, mergeStepHelper(left, lefti, left.length),
						mergeStepHelper(right, righti, right.length)));
				// end of mergestep
				lefti++;

			} else {
				temp[tempi] = right[righti];

				// *********** mergestep ********************
				currentSteps.ensureCapacity(currentSteps.size() + 1);
				currentSteps.add(new MergeStep(mergeStepHelper(temp, 0, tempi),
						tempi, mergeStepHelper(left, lefti, left.length),
						mergeStepHelper(right, righti, right.length)));
				// end of mergestep

				righti++;

			}
			tempi++;
		}

		byte[] rest;
		int resti;

		if (lefti >= left.length) {
			// The left array has been used up...
			rest = right;
			resti = righti;
		} else {
			// The right array has been used up...
			rest = left;
			resti = lefti;
		}

		// Copy the rest of whichever array (left or right) was
		// not used up.
		for (int i = resti; i < rest.length; i++) {
			temp[tempi] = rest[i];
			tempi++;

		}

		// *********** mergestep ********************
		currentSteps.ensureCapacity(currentSteps.size() + 1);
		currentSteps.add(new MergeStep(temp, temp.length, null, null));

		return temp;
	}

	public static void main(String args[]) {

		int[] initialValues = new int[] { 5, 9, 2, 3, 2, 1, 4, 5, 3, 1, 3, 4 };

		MergeSort temp = new MergeSort();
		temp.solve(initialValues);

	}

} /// end of class

