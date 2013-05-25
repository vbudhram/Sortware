package steps.heap;

import values.constant.Colors;
import values.constant.PaintTimes;
import values.constant.ValueDefinitions;

public class ArrayHeapSwappedWithMaxStep extends HeapStep {
    
    private int index;

   private static byte[] generateHeapColor(int lastElement)
   {
      byte[] result = new byte[lastElement + 1];
      for (int i = 0; i < result.length; i++) {
         result[i] = Colors.BAR_DEFAULT;
      }
      if(lastElement > 0)
      {
          result[1] = Colors.BAR_SWAP;          
      }
      return result;
   }
   
   private static byte[] generateHeapValues(byte[] array, int cutoff) 
   {
       if(array.length - cutoff - 1 < 1) return new byte[1];;
       byte[] result = new byte[array.length - cutoff - 1];
       for(int i = 0; i < result.length; i++)
       {
           result[i] = array[i];
       }       
       return result;       
   }
   
   private static byte[] generateArrayColor(int arrayLength, int cutoff) 
   {
       byte[] result = new byte[arrayLength];
       result[0] = Colors.BAR_EMPTY;
       for(int i = 1; i < result.length - cutoff; i++)
       {
           result[i] = Colors.BAR_DEFAULT;
       }
       for(int i = result.length - cutoff; i < result.length; i++)
       {
           result[i] = Colors.BAR_SORTED;
       }
       
       if(result.length - cutoff - 1 > 1)
       {
         result[1] = Colors.BAR_SWAP;
         result[result.length - cutoff - 1] = Colors.BAR_SWAP;  
       }      
       else result[1] = Colors.BAR_SORTED;
           
       return result;       
   }
   
    // The array representing the heap should start in index 1, that means, the position or index 0 of the array is unused.   
    // This is because the heap works indexing its elements from 1 instead of 1. Based on this, the array passed to the array representation
    // should ignore the position 0 as well, so the elements in both the heap and the array have the same index   
 
   public ArrayHeapSwappedWithMaxStep(byte[] arrayValues,
         int orderedCutOff) {
      super(PaintTimes.HEAP_SWAP, arrayValues, generateArrayColor(arrayValues.length, orderedCutOff),
            generateHeapValues(arrayValues, orderedCutOff), generateHeapColor(arrayValues.length - orderedCutOff - 1));
      {
          this.index = arrayValues.length - orderedCutOff - 2;
      }
   }
   
   public String toString() 
   {
       String result;
       if(index == 0) result = "The elements are in order and the method is complete";
       else result = "Heap max was removed and swapped with element in position " + index + " of the array. Begin reheapification." ;
       return result;
   }
}
