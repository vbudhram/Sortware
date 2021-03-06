package sortingmethods;

import java.util.ArrayList;
//our packages
import steps.SimpleArrayStep;
import steps.Step;
import steps.array.bubble.BubbleCompareStep;
import steps.array.bubble.BubbleSwapStep;
import utils.Convert;
import values.constant.ValueDefinitions;

public class BubbleSort extends SortingMethod {
    
    @Override
    public String getName() {
      return "Bubble Sort";
    }    
    
    /*
     * Will implement the solve method in the SortingMethod 
     * class for a Bubble Sort solution.
     */
   @Override
   public Step[][] solve(int[] initialValues) {
      /* byte array to hold values in each step of the solution.
       */
      byte[] values = new byte[initialValues.length];
      /* Add values array passed into gui into the "values" array
       * as bytes
       */
      for (int i = 0; i < initialValues.length; i++) {
            values[i] = (byte) initialValues[i];
      }
      /* Variables:
       * allSteps = an ArrayList that hold only ArrayLists that hold only steps.
       * n = the length of the array
       * doMore = boolean that decides when to stop.
       */
      ArrayList<ArrayList<Step>> allSteps = new ArrayList<ArrayList<Step>>();
      int n = initialValues.length;
      
      /* http://www.leepoint.net/notes-java/data/arrays/32arraybubblesort.html
       * Follow the weblink to find examples of BubbleSort. This algorithm is the
       * first type, which uses a fixed number of passes.
       */

      for (int pass = 1; pass < n; pass++) {
          /*
           * First we create a new ArrayList that holds only structures
           * of type step. This list will hold all of the steps in one 
           * pass of the algorithm
           */
         ArrayList<Step> currentSteps = new ArrayList<Step>();
         boolean swap = false;
         for (int i = 0; i < n - pass; i++) {
            /* Compare Step:
             * The indices of the two values currently being compared 
             * are passed into the BubbleCompareStep class. Here the 
             * color will be changed on these indices to show that they
             * are currently being compared. The value array is also 
             * passed in to be stored to the theValues array in the step
             * structure. Once the colors have been changed and the values
             * array has been stored, the Step structure is created and 
             * stored into the ArrayList currentSteps which only holds
             * Steps.
             */
            currentSteps.add(new BubbleCompareStep(values.clone(), i, i + 1));
            /* Swap Step:
             * If a swap is needed, the BubbleSwapStep class will be created
             * with the current values array and the indices of the two values 
             * that need to be swapped. The color will be changed on these two
             * values in the color array and the value array will be stored in 
             * the step structure. This step structure will then be added to the
             * ArrayList currentSteps, which holds the steps in one pass of the
             * algorithm. The values are then swapped.
             */
            if (values[i] > values[i + 1]) {
               byte temp = values[i];
               values[i] = values[i + 1];
               values[i + 1] = temp;
               swap = true;
                currentSteps.add(new BubbleSwapStep(values.clone(), i, i + 1));
            }
         }
         /*
          * Once we have made one pass through the algorithm, the ArrayList
          * currentSteps is stored into the ArrayList allSteps. allSteps holds
          * all of the steps in the solution. At each index it holds all of the
          * steps in one pass of the algorithm.
          */
         allSteps.add(currentSteps);
         if (!swap)
            break;
      }
      /* The Convert class will convert an ArrayList<ArrayList<Step>> 
       * to a Step[][]. It takes an ArrayList<ArrayList<Step>> (all of 
       * the steps in the solution)
       */
      return Convert.toArray(allSteps);
   }

   // Unit Test
   public static void main(String[] args) {
       
       /*
        * Variables: An array of unsorted integers
        * and a string to hold those integers
        */
      int[] unsorted = new int[] { 5, 9, 3, 1, 3, 4 }; 
      StringBuilder b = new StringBuilder();
      
      /*
       * This block of code adds unsorted 
       * array to string the string b and
       * prints it to the screen
       */
      b.append("Unsorted ");
      for (int i : unsorted) {
         b.append(i + ", ");
      }
      System.out.println(b);
      System.out.println();
      
      /*
       * A double array of steps is created by passing 
       * the unsorted array of integers into the solve 
       * method of BubbleSort. 
       */
      Step[][] steps = (new BubbleSort()).solve(unsorted);
      
      /*
       * Using an enhanced for loop, we enter into each
       * row of steps.
       */
      int i = 1;
      for (Step[] step : steps) {
         /*
          * Using another enhanced for loop we now access the 
          * individual steps in each row.
          */
         int j = 1;
         for (Step substep : step) {
            StringBuilder b2 = new StringBuilder();
            /*
             * Using another enhanced for loop we enter the array
             * of values in a single step. As we traverse the values
             * array, we are adding them to the string b2
             */
            for (int num=0; num<((SimpleArrayStep) substep).theValues.length-1; num++) {
                  b2.append(((SimpleArrayStep) substep).theValues[num] + ", ");
            }
            b2.append(((SimpleArrayStep) substep).theValues[((SimpleArrayStep) substep).theValues.length-1]);
            
            /*
             * Prints to screen the current step and
             * the array before the current action is
             * performed.
             */
            System.out.print("Step [" + i + ", " + j + "] ");
            System.out.println(substep.toString());
            System.out.print("Step [" + i + ", " + j + "] ");
            System.out.println(b2.toString());        
            System.out.println();

            //increment column number
            j++;
         }
         //increment row number
         i++;
      }
      System.out.print("\nSorted   ");
      byte[] sorted = ((SimpleArrayStep) steps[steps.length - 1][steps[steps.length - 1].length - 1]).theValues;
      for (int num=0; num<sorted.length-1; num++) {
         System.out.print(sorted[num] + ", ");
      }
      System.out.print(sorted[sorted.length-1]);
   }
}