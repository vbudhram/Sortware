package steps.heap;

import values.constant.Colors;
import values.constant.PaintTimes;
import values.constant.ValueDefinitions;

public class HeapSwapStep extends HeapStep {
   private static byte[] helperDefaultArray() {
      byte[] result = new byte[ValueDefinitions.VALUE_COUNT_MAX];
      result[0] = Colors.BAR_EMPTY;
      for (int i = 1; i < result.length; i++) {
         result[i] = Colors.BAR_DEFAULT;
      }
      return result;
   }

   private static byte[] helperHeap(int index, int index2) {
      byte[] result = new byte[ValueDefinitions.VALUE_COUNT_MAX];
      for (int i = 0; i < result.length; i++) {
         result[i] = Colors.BAR_DEFAULT;
      }
      result[index] = Colors.BAR_HEAP_SWAPED;
      return result;
   }

   public HeapSwapStep(int paintTime, byte[] arrayValues, byte[] heapValues,
         int index, int index2) {
      super(PaintTimes.HEAP_SWAP, arrayValues, helperDefaultArray(),
            heapValues, helperHeap(index, index2));
   }
}
