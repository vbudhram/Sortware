package Profiles;

import java.io.Serializable;

import values.constant.ValueDefinitions;

public class Profile implements Serializable {
   public final int[] theValues;

   public Profile(int[] values) {
      theValues = values;
   }

   public boolean equal(Profile p) {
      for (int i = 0; i < ValueDefinitions.VALUE_COUNT_MAX; i++) {
         if (theValues[i] != p.theValues[i]) {
            return false;
         }
      }
      return true;
   }

   public String toString() {
      StringBuilder b = new StringBuilder();
      for (int i : theValues) {
         b.append(i + " ");
      }
      return b.toString();
   }
}
