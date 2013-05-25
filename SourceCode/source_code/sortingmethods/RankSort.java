package sortingmethods;

import java.util.ArrayList; //our packages
import steps.SimpleArrayStep;
import steps.Step;
import steps.array.rank.*;
import utils.Convert;
import values.constant.ValueDefinitions;
import steps.array.rank.RankUpStep;

public class RankSort extends SortingMethod{

    @Override
    public String getName() {
      return values.constant.Sorts.SORTING_METHODS[values.constant.Sorts.RANK_SORT];
    }      
    
	@Override
	public Step[][] solve(int[] initialValues) {
            // byte array to hold values in each step of the solution.
		byte[] values = new byte[initialValues.length];
                int[] ranks = new int[initialValues.length];
		/*
		 * Add values array passed into gui into the "values" array as bytes
		 */
		for (int i = 0; i < initialValues.length; i++) {
			values[i] = (byte) initialValues[i];
		}
                for (int j = 0; j < initialValues.length; j++) {
                    ranks[j] = 0;
                }
		/*
		 * Variables: allSteps = an ArrayList that hold only ArrayLists that
		 * hold only steps. n = the length of the array doMore = boolean that
		 * decides when to stop.
		 */
		ArrayList<ArrayList<Step>> allSteps = new ArrayList<ArrayList<Step>>();
		int n = initialValues.length;

		/*
		 * http://www.leepoint.net/notes-java/data/arrays/31arrayselectionsort.html
		 * Follow the weblink to find examples of Se;ection Sort. This algorithm
		 * is the first type, which is a simple Selection Sort.
		 */
                
		for (int pass = 1; pass < values.length; pass++) {
			/*
			 * First we create a new ArrayList that holds only structures of
			 * type step. This list will hold all of the steps in one pass of
			 * the algorithm
			 */
			ArrayList<Step> currentSteps = new ArrayList<Step>();
			for (int j = 0; j < pass; j++) {

				/*
				 * Compare Step: The indices of the two values currently being
				 * compared are passed into the SelectionCompareStep class. Here
				 * the color will be changed on these indices to show that they
				 * are currently being compared. The value array is also passed
				 * in to be stored to the theValues array in the step structure.
				 * Once the colors have been changed and the values array has
				 * been stored, the Step structure is created and stored into
				 * the ArrayList currentSteps which only holds Steps.
				 */
                                 currentSteps.add(new RankCompareStep(values.clone(), 
                                         ranks.clone(), pass, j));

				/*
				 * RANK Step: If a swap is needed, the SelectionSwapStep class
				 * will be created with the current values array and the indices
				 * of the two values that need to be swapped. The color will be
				 * changed on these two values in the color array and the value
				 * array will be stored in the step structure. This step
				 * structure will then be added to the ArrayList currentSteps,
				 * which holds the steps in one pass of the algorithm. The
				 * values are then swapped.
				 */
                                if (values[j] <= values[pass]) {
                                    ranks[pass]++;
                                    currentSteps.add(new RankUpStep(values.clone(), ranks.clone(),
							pass));
                                }
                                else { 
                                    ranks[j]++;
                                    currentSteps.add(new RankUpStep(values.clone(), ranks.clone(),
							j));
                                }       
                        }
                        
                        allSteps.add(currentSteps);
                }
                        
                /*
                 * Swap Step: If a swap is needed, the SelectionSwapStep class
                 * will be created with the current values array and the indices
                 * of the two values that need to be swapped. The color will be
                 * changed on these two values in the color array and the value
                 * array will be stored in the step structure. This step
                 * structure will then be added to the ArrayList currentSteps,
                 * which holds the steps in one pass of the algorithm. The
                 * values are then swapped.
                 */
               
                    byte [] temp=values.clone();
                    for (int z=0;z<values.length;z++) {
                        values[z]=-1;
                    }
                    int [] sortedranks = new int [ranks.length];
                    for (int z=0;z<values.length;z++) {
                        sortedranks[z] = -1;
                    }
               
                for(int i = 0; i < values.length; i++) {
                    ArrayList<Step> currentSteps = new ArrayList<Step>();
                    values[ranks[i]] = temp[i];
                    sortedranks[ranks[i]]=ranks[i];
                    
                    currentSteps.add(new RankHighlightStep(values.clone(), temp.clone(),
                            ranks.clone(), sortedranks.clone(),
                            ranks[i], i));
                    
                    currentSteps.add(new RankRemoveStep(values.clone(), temp.clone(),
                            ranks.clone(), sortedranks.clone(),
                            ranks[i], i));
                    temp[i] = -1;
                    
                    allSteps.add(currentSteps);
                }
                
                        
                           /*
			 * Once we have made one pass through the algorithm, the ArrayList
			 * currentSteps is stored into the ArrayList allSteps. allSteps
			 * holds all of the steps in the solution. At each index it holds
			 * all of the steps in one pass of the algorithm.
			 */
			
                    
                return Convert.toArray(allSteps);
	}

	// Unit Test
	public static void main(String[] args) {

		/*
		 * Variables: An array of unsorted integers and a string to hold those
		 * integers
		 */
		int[] unsorted = new int[] { 13, 42, 20, 34, 45, 44, 30, 39, 19, 6, 22, 36, 38, 18, 2 };
		StringBuilder b = new StringBuilder();

		/*
		 * This block of code adds unsorted array to string the string b and
		 * prints it to the screen
		 */
		b.append("Unsorted ");
		for (int i : unsorted) {
			b.append(i + ", ");
		}
		System.out.println(b);
		System.out.println();

		/*
		 * A double array of steps is created by passing the unsorted array of
		 * integers into the solve method of SimpleSelectionSort.
		 */
		Step[][] steps = (new RankSort()).solve(unsorted);

		/*
		 * Using an enhanced for loop, we enter into each row of steps.
		 */
		int i = 1;
		for (Step[] step : steps) {
			/*
			 * Using another enhanced for loop we now access the individual
			 * steps in each row.
			 */
			int j = 1;
			for (Step substep : step) {
				StringBuilder b2 = new StringBuilder();
				/*
				 * Using another enhanced for loop we enter the array of values
				 * in a single step. As we traverse the values array, we are
				 * adding them to the string b2
				 */
				for (int num = 0; num < ((SimpleArrayStep) substep).theValues.length - 1; num++) {
					b2
							.append(((SimpleArrayStep) substep).theValues[num]
									+ ", ");
				}
				b2
						.append(((SimpleArrayStep) substep).theValues[((SimpleArrayStep) substep).theValues.length - 1]);

				/*
				 * Prints to screen the current step and the array before the
				 * current action is performed.
				 */
				System.out.print("Step [" + i + ", " + j + "] ");
				System.out.println(substep.toString());
				System.out.print("Step [" + i + ", " + j + "] ");
				System.out.println(b2.toString());
				System.out.println();

				// increment column number
				j++;
			}
			// increment row number
			i++;
		}
		System.out.print("\nSorted   ");
		byte[] sorted = ((SimpleArrayStep) steps[steps.length - 1][steps[steps.length - 1].length - 1]).theValues;
		for (int num = 0; num < sorted.length - 1; num++) {
			System.out.print(sorted[num] + ", ");
		}
		System.out.print(sorted[sorted.length - 1]);
	}
}
