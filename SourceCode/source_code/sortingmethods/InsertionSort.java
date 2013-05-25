package sortingmethods;

import java.util.ArrayList;
//our packages
import steps.SimpleArrayStep;
import steps.Step;
import steps.array.insertion.InsertionCompareStep;
import steps.array.insertion.InsertionRemoveBarStep;
import steps.array.insertion.InsertionShiftBarStep;
import steps.array.insertion.InsertionInsertBarStep;
import utils.Convert;
import values.constant.ValueDefinitions;

public class InsertionSort extends SortingMethod{
    
    @Override
    public String getName() {
      return "Insertion Sort";
    }      
    
    /*
     * Will implement the solve method in the SortingMethod 
     * class for a Selection Sort solution.
     */
    @Override
    public Step[][] solve(int[] initialValues) {
     
      // byte array to hold values in each step of the solution.
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

      /* http://www.leepoint.net/notes-java/data/arrays/31arrayselectionsort.html
       * Follow the weblink to find examples of Se;ection Sort. This algorithm is the
       * first type, which is a simple Selection Sort.
       */
        
      int i, j;
      byte temp;
      //The sorting algorithm
        for (i=1; i < values.length; i++)
        {
            j=i;
            temp=values[j];
            ArrayList<Step> currentSteps = new ArrayList<Step>();
            currentSteps.add(new InsertionCompareStep(values.clone(), j-1, j));
            //internal sort
            int secondRemove=0;
            while (j>0 && values[j-1]>temp)
            {
                values[j]=values[j-1];
                if(secondRemove == 0){
                    currentSteps.add(new InsertionRemoveBarStep(values.clone(), j-1, j));   
                    secondRemove++;
                }
                currentSteps.add(new InsertionShiftBarStep(values.clone(), j, j-1));                
                j--;
                
            }
            values[j]=temp;
            //swap step uses values before last j index change
                currentSteps.add(new InsertionInsertBarStep(values.clone(), j, j+1));
            
            allSteps.add(currentSteps);
        }
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
       * method of SelectionSort. 
       */
      Step[][] steps = (new InsertionSort()).solve(unsorted);
      
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
