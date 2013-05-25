package steps.heap;

import values.constant.Colors;
import values.constant.PaintTimes;
import values.constant.ValueDefinitions;

public class ArrayHeapSwapStep extends HeapStep
{
    
    private int index1;
    private int index2;
    private int cutoff;

   private static byte[] generateHeapColor(int lastElement, int index1, int index2)
   {
      byte[] result = new byte[lastElement];
      for (int i = 0; i < result.length; i++) {
         result[i] = Colors.BAR_DEFAULT;
      }
      result[index1] = Colors.BAR_SWAP;
      result[index2] = Colors.BAR_SWAP;
      
      return result;
   }
   
    
   private static byte[] generateArrayColor(int arrayLength, int cutoff, int index1, int index2) 
   {
       byte[] result = new byte[arrayLength];
       result[0] = Colors.BAR_EMPTY;
       for(int i = 1; i < result.length - cutoff; i++)
       {
           result[i] = Colors.BAR_DEFAULT;   // There is a problem with the default, no black is obtained           
       }
       for(int i = result.length - cutoff; i < result.length; i++)
       {
           result[i] = Colors.BAR_SORTED;
       }
       result[index1] = Colors.BAR_SWAP;
       result[index2] = Colors.BAR_SWAP;
       
       return result;       
   }   
   
   // The array representing the heap should start in index 1, that means, the position or index 0 of the array is unused.   
   // This is because the heap works indexing its elements from 1 instead of 1. Based on this, the array passed to the array representation
   // should ignore the position 0 as well, so the elements in both the heap and the array have the same index

   public ArrayHeapSwapStep(byte[] arrayValues, byte[] heapValues, int cutoff, int index1, int index2) 
   {
      super(PaintTimes.HEAP_SWAP, arrayValues, generateArrayColor(arrayValues.length, cutoff, index1, index2),
            heapValues, generateHeapColor(arrayValues.length, index1, index2));
      {
          this.index1 = index1;
          this.index2 = index2;
          this.cutoff = cutoff;
      }
   }   
   
   public String toString() 
   {
      String result = "";
      if(cutoff == 0) result = result + "Heap initialization: ";
      result = result + "Node at position " + index2 + " swapped with node at position " + index1;
      return result;
   }
}