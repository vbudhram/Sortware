package sortingmethods;

import java.util.ArrayList;

import steps.Step;
import steps.heap.*;
import values.constant.*;
import utils.Convert;

public class HeapSort extends SortingMethod
{
    // globlal variables
    private static byte[] arrayValues;
    private static byte[] heapValues;
    // will have information about the number of elements already sorted
    private static int orderedCount;
    // number of elements in the array (originally equal to the heap)
    private static int numberElements;
    
    
    @Override
    public String getName() 
    {
      return "Heap Sort";
    }
    
    
    @Override
    public Step[][] solve(int[] initialValues) 
    {
        arrayValues = new byte[initialValues.length + 1];
        heapValues = new byte[initialValues.length + 1];
        
        //The number of elements currently sorted
        orderedCount = 0;    
        //The total number of elements to be sorted
        numberElements = arrayValues.length - 1;
        
        for (int i = 1; i <= initialValues.length; i++) 
        {
            arrayValues[i] = (byte) initialValues[i - 1];
            heapValues[i] = (byte) initialValues[i - 1];
        }
        
        // 2 dimensional ArrayList will contain all the steps
        ArrayList<ArrayList<Step>> allSteps = new ArrayList<ArrayList<Step>>();
        
        // Array list will contain the heap initialization steps
        ArrayList<Step> iterationSteps = new ArrayList<Step>();        
       
        // First iteration is to heapify the unordered array
        //initialize heap
        initialize(iterationSteps);
        
        // Make sure enough room in allSteps then add iterationSteps
        allSteps.ensureCapacity(allSteps.size()+1);        
        allSteps.add(iterationSteps);
        
        // The next steps are to extract the greatest value of the heap, swap it to the end, mark it as ordered, 
        // and repeat until everything is ordered
             
        while(orderedCount < arrayValues.length - 1)
        {
            // Creating one arraylist for the steps in each iteration
            ArrayList<Step> currentSteps = new ArrayList<Step>();
            currentSteps.ensureCapacity(currentSteps.size()+1);
            currentSteps.add(new ArrayHeapToSwapWithMaxStep(arrayValues.clone(),orderedCount));
            
            //Max about to be swapped (ArrayHeapToSwapWithMaxStep)      
            // switching the max element in the heap with the last that is not ordered yet
            byte temp = heapValues[numberElements - orderedCount];
            heapValues[numberElements - orderedCount] = heapValues[1];
            heapValues[1] = temp;
            arrayValues[1] = heapValues[1];
            arrayValues[numberElements - orderedCount] = heapValues[numberElements - orderedCount];
            //max just swapped out (ArrayHeapSwappedWithMaxStep)
            
            currentSteps.ensureCapacity(currentSteps.size()+1);
            currentSteps.add(new ArrayHeapSwappedWithMaxStep(arrayValues.clone(),orderedCount));            
            
            // a element has been put in place, increase number of known ordered elements
            
            increaseOrder();
            
            // reheapify our heap (which had the max element removed)            
            reHeapify(currentSteps);
            //add all the steps for ordering one element in the list
            allSteps.ensureCapacity(allSteps.size()+1);        
            allSteps.add(currentSteps); 
        }    
              
        
        return Convert.toArray(allSteps);

    }
    
    //initialize array as max heap in heapValues
   private void initialize(ArrayList<Step> steps)
   {       
      int size = heapValues.length - 1;
      /* starting at the middle of the array, compare each element to its
       * largest child, and swap that element with the child if the child is
       * larger.  Then, make sure tree under new child is a max heap. 
       * continue decrementing element of the array being compared until the
       * root of the maxheap is reached */
      for (int root = size / 2; root >= 1; root--)
      {
         byte rootElement = heapValues[root];
   
         // find place to put rootElement
         int child = 2 * root; // parent of child is target
                               // location for rootElement
         while (child <= size)
         {
            // heapValues[child] should be larger sibling
            if ((child < size) && (heapValues[child] < heapValues[child + 1])) child++;
            
            steps.ensureCapacity(steps.size()+1);          
            steps.add(new ArrayHeapCompareStep(arrayValues.clone(), heapValues.clone(),0, child, child/2));
   
            // can we put rootElement in heap[child/2]? (ArrayHeapCompareStep)
            if ( rootElement >=heapValues[child])
               break;  // yes
   
            // no, then swap (ArrayHeapSwapStep)
            heapValues[child / 2] = heapValues[child]; // move child up
            arrayValues[child / 2] = arrayValues[child];
            heapValues[child] = rootElement;
            arrayValues[child] = rootElement;
            
            steps.ensureCapacity(steps.size()+1);
            steps.add(new ArrayHeapSwapStep(arrayValues.clone(), heapValues.clone(),0, child, child/2));
            
            child *= 2;                    // move down a level
         }         
      } 
      
   }
   
   private void increaseOrder()
   {
       orderedCount++;
       int n = heapValues.length;
       byte[] temp = new byte[n - 1];
       for(int i = 1; i < n - 1; i++)
       {
           temp[i] = heapValues[i];
       }
       heapValues = temp;           
   }
   
   private void reHeapify(ArrayList<Step> steps)
   {
       // number of elements in the heap
       int size = heapValues.length - 1;
       
      // find place for lastElement starting at root
      //int currentNode = 1;
      int child = 2;     // child of currentNode
      
      while (child <= size)
      {
         // heapValues[child] should be larger child of currentNode
         if ((child < size) && (heapValues[child] < heapValues[child + 1])) child++;
         
         steps.ensureCapacity(steps.size()+1);          
         steps.add(new ArrayHeapCompareStep(arrayValues.clone(), heapValues.clone(), orderedCount, child, child/2));          
   
         // can we put lastElement in heap[currentNode]? (ArrayHeapCompareStep)
         if (heapValues[child/2] > heapValues[child]) break;   // yes
   
         // no, then swap (ArrayHeapSwapStep)
         byte temp = heapValues[child/2];
         heapValues[child/2] = heapValues[child];
         heapValues[child] = temp;
         
         arrayValues[child/2] = heapValues[child/2];
         arrayValues[child] = heapValues[child];
         
         steps.ensureCapacity(steps.size()+1);
         steps.add(new ArrayHeapSwapStep(arrayValues.clone(), heapValues.clone(),orderedCount, child, child/2));
         
         // move down the heap
         child *= 2;
      }
      //heap[currentNode] = lastElement;
   }
}