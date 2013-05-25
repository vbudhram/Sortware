package steps;

/*
 * This Structure will represent state of our data at a single
 * point in time.
 * 
 * The class uses generics. The Datatype must be specified 
 * when the class is extended. 
 * 
 * The array theValues will represent the current order of 
 * the values. The array theColors will represent the current
 * order of the colors that corresponds to each value at each
 * index.
 * 
 * The int thePaintTime will be a number used as a multiplier 
 * for the slider bar when painting the images to the screen.
 * Paint time constants can be found in the values.constant 
 * package.
 * 
 * For an example of how it can be used, look at SimpleStepArray.java
 * in theh steps package.
 */
public class Step<Datatype> {
   public final int thePaintTime;
   public final Datatype theValues;
   public final Datatype theColors;
   public Step(int paintTime, Datatype values, Datatype colors) {
      theValues=values;
      theColors=colors;
      thePaintTime=paintTime;
   }
}
