package sortingmethods;

import java.util.ArrayList;

import steps.SimpleArrayStep;
import steps.Step;
import steps.array.bucket.Bucket;
import steps.array.bucket.BucketBubbleCompareStep;
import steps.array.bucket.BucketBubbleSwapStep;
import steps.array.bucket.BucketMinMaxCompare;
import steps.array.bucket.BucketMinMaxSet;
import steps.array.bucket.BucketMoveFromContainerStep;
import steps.array.bucket.BucketMoveToContainerStep;
import steps.array.bucket.Container;
import utils.Convert;
import values.constant.ValueDefinitions;

public class BucketSort extends SortingMethod {
	@Override
	public String getName() {
		return values.constant.Sorts.SORTING_METHODS[values.constant.Sorts.BUCKET_SORT];
	}

	@Override
	public Step[][] solve(int[] initialValues) {
		ArrayList<ArrayList<Step>> allSteps = new ArrayList<ArrayList<Step>>();
		ArrayList<Step> currentSteps = new ArrayList<Step>();
		allSteps.add(currentSteps);
		int numOfValues = initialValues.length;
		byte[] values = new byte[numOfValues];
		for (int i = 0; i < numOfValues; i++)
			values[i] = (byte) initialValues[i];
		int indexMax = 0;
		int indexMin = 0;
		currentSteps
				.add(new BucketMinMaxSet(values.clone(), indexMax, indexMin));
		for (int i = 1; i < values.length; i++) {
			currentSteps.add(new BucketMinMaxCompare(values.clone(), indexMax,
					indexMin, i, indexMax));
			if (values[i] > values[indexMax]) {
				indexMax = i;
				currentSteps.add(new BucketMinMaxSet(values.clone(), indexMax,
						indexMin));
			}
			currentSteps.add(new BucketMinMaxCompare(values.clone(), indexMax,
					indexMin, i, indexMin));
			if (values[i] < values[indexMin]) {
				indexMin = i;
				currentSteps.add(new BucketMinMaxSet(values.clone(), indexMax,
						indexMin));
			}
		}
		/*
		 * byte min = Byte.MAX_VALUE; byte max = Byte.MIN_VALUE; for (int i = 0;
		 * i < values.length; i++) { if (values[i] > max) max = values[i]; if
		 * (values[i] < min) min = values[i]; }
		 */
		int range = values[indexMax] - values[indexMin] + 1;
		int bucketCount = range < 4 ? range : 4;
		bucketCount = bucketCount > numOfValues ? numOfValues : bucketCount;
		Bucket[] buckets = new Bucket[bucketCount];
		// Buckets are Approximate ( fast to compute)
		int bucketWidth = range / bucketCount
				+ (range % bucketCount > 0 ? 1 : 0);
		for (int i = 0; i < buckets.length; i++) {
			buckets[i] = new Bucket(0, 0, values[indexMin] + (i + 1)
					* bucketWidth - 1, values[indexMin] + i * bucketWidth);
		}
		ArrayList<ArrayList<Byte>> bytes = new ArrayList<ArrayList<Byte>>();
		for (int i = 0; i < buckets.length; i++) {
			bytes.add(new ArrayList<Byte>());
		}
		//Move to containers.
		ArrayList<Step> currentSteps2 = new ArrayList<Step>();
		allSteps.add(currentSteps2);
		for (int i = 0; i < values.length; i++) {
			for (int b = 0; b < buckets.length; b++) {
				if (buckets[b].theMaxValue >= values[i]
						&& buckets[b].theMinValue <= values[i]) {
					bytes.get(b).add(values[i]);
					values[i] = ValueDefinitions.VALUE_UNDEFINED;
					// Container generation
					Container[] containers = new Container[buckets.length];
					for (int n = 0; n < containers.length; n++) {
						ArrayList<Byte> blist = bytes.get(n);
						byte[] byteArray = new byte[blist.size()];
						for (int k = 0; k < byteArray.length; k++) {
							byteArray[k] = blist.get(k);
						}
						containers[n] = new Container(byteArray);
					}
					currentSteps2.add(new BucketMoveToContainerStep(values.clone(), i,
							new Bucket[0], containers));
				}
			}
		}
		//Base Bucket Size on Container Size
		int start = 0;
		for (int i = 0; i < buckets.length; i++) {
			buckets[i].theStartIndex = start;
			buckets[i].theEndIndex = start + bytes.get(i).size() - 1;
			start += bytes.get(i).size();
		}
		//Move items back.
		{
			ArrayList<Step> currentSteps3 = new ArrayList<Step>();
			allSteps.add(currentSteps3);
			int i = 0;
			for (int b = 0; b < buckets.length; b++) {
				for (int j = 0; j < (buckets[b].theEndIndex
						- buckets[b].theStartIndex + 1); j++) {
					values[i++] = bytes.get(b).get(0);
					bytes.get(b).remove(0);
					// Container generation
					Container[] containers = new Container[buckets.length];
					for (int n = 0; n < containers.length; n++) {
						ArrayList<Byte> blist = bytes.get(n);
						byte[] byteArray = new byte[blist.size()];
						for (int k = 0; k < byteArray.length; k++) {
							byteArray[k] = blist.get(k);
						}
						containers[n] = new Container(byteArray);
					}
					currentSteps3.add(new BucketMoveFromContainerStep(values.clone(), i,
							new Bucket[0], containers));
				}
			}
		}
		//Bubble sort each bucket.
		for (int b = 0; b < buckets.length; b++) {
			int n = buckets[b].theEndIndex + 1;
			int zero = buckets[b].theStartIndex;
			for (int pass = 1; pass < n; pass++) {
				ArrayList<Step> currentSteps4 = new ArrayList<Step>();
				boolean swap = false;
				for (int i = zero; i < n - pass; i++) {
					currentSteps4.add(new BucketBubbleCompareStep(buckets,
							values.clone(), i, i + 1));
					if (values[i] > values[i + 1]) {
						byte temp = values[i];
						values[i] = values[i + 1];
						values[i + 1] = temp;
						swap = true;
						currentSteps4.add(new BucketBubbleSwapStep(buckets,
								values.clone(), i, i + 1));
					}
				}
				if (currentSteps4.size() > 0)
					allSteps.add(currentSteps4);
				if (!swap)
					break;
			}
		}
		// move values to buckets. // harris' edits (probably wrong) for (int
		// b = 0; b < buckets.length; b++) { for (int pass =
		// buckets[b].theStartIndex; pass <= buckets[b].theEndIndex; pass++) {
		// ArrayList<Step> currentSteps = new ArrayList<Step>(); boolean swap =
		// false; for (int i = 0; i < buckets[b].theEndIndex - pass; i++) {
		// currentSteps.add(new BucketBubbleCompareStep(buckets.clone(),
		// buckets[b].theValues.clone())); if (buckets[b].theValues[i] >
		// buckets[b].theValues[i + 1]) { byte temp = buckets[b].theValues[i];
		// buckets[b].theValues[i] = buckets[b].theValues[i + 1];
		// buckets[b].theValues[i + 1] = temp; swap = true; currentSteps.add(new
		// BucketBubbleSwapStep(buckets.clone(), buckets[b].theValues.clone()));
		// } }
		// allSteps.add(currentSteps); if (!swap) break; } }
		// Buckets are precise:
		// for (int i = 0; i < buckets.length; i++) {
		// buckets[i] = new Bucket(0, 0, min + i * range / bucketCount
		// + (i > 0 ? ((range % bucketCount) > i - 1 ? i : range % bucketCount)
		// : 0), min + (i + 1) * range
		// / bucketCount + (i + 1 < buckets.length ? ((range % bucketCount) > i
		// ? i + 1 : range % bucketCount) : 0)
		// - 1);
		// }
		return Convert.toArray(allSteps);
		// byte[] values = new byte[initialValues.length];
		// byte[][] buckets = new byte[5][initialValues.length];
		// byte[] bucketSize = new byte[5];
		// byte temp = 0;
		// for (int i = 0; i < values.length; i++) {
		// values[i] = (byte) initialValues[i];
		// }
		// for (int i = 0; i < values.length; i++) {
		// if (values[i] >= 0 || values[i] <= 20) {
		// buckets[0][bucketSize[0]] = values[i];
		// bucketSize[0]++;
		// } else if (values[i] >= 21 || values[i] <= 40) {
		// buckets[1][bucketSize[1]] = values[i];
		// bucketSize[1]++;
		// } else if (values[i] >= 41 || values[i] <= 60) {
		// buckets[2][bucketSize[2]] = values[i];
		// bucketSize[2]++;
		// } else if (values[i] >= 61 || values[i] <= 80) {
		// buckets[3][bucketSize[3]] = values[i];
		// bucketSize[3]++;
		// } else if (values[i] >= 81) {
		// buckets[4][bucketSize[4]] = values[i];
		// bucketSize[4]++;
		// }
		// }
		// for (int i = 0; i < 5; i++) {
		// for (int j = 1; j < bucketSize[i]; j++) {
		// for (int k = 0; k < bucketSize[i] - j; k++) {
		// if (buckets[i][k] > buckets[i][k + 1]) {
		// temp = buckets[i][k];
		// buckets[i][k] = buckets[i][k + 1];
		// buckets[i][k + 1] = temp;
		// }
		// }
		// }
		// return null;
		// }
	}

	public static void main(String[] args) {
		int[] unsorted = new int[] { 11, 15, 9, 7, 1, 2, 6, 7, 9 };
		StringBuilder b = new StringBuilder();
		b.append("Unsorted ");
		for (int i : unsorted)
			b.append(i + ", ");
		System.out.println(b);
		Step[][] steps = (new BucketSort()).solve(unsorted);
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
