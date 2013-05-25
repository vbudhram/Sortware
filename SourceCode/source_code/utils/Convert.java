package utils;

import java.util.ArrayList;
import steps.SimpleArrayStep;
import steps.Step;
import values.constant.ValueDefinitions;


public class Convert {
    public static Step[][] toArray(ArrayList<ArrayList<Step>> allSteps){
        /* Creates an 2 dimensional array of steps 
         * with as many rows as there are passes 
         * through the algorithm
         */
        Step[][] solutionSteps = new Step[allSteps.size()][];
        
        /* Initializes each row of solutionSteps to contain an
         * array of steps with as many spaces as there 
         * are substeps in a single pass through the 
         * algorithm.
         */
        for (int i=0; i < allSteps.size(); i++)
            solutionSteps[i]=new Step[allSteps.get(i).size()];
        
        /* Uses the toArray method in the ArrayList class
         * to turn each ArrayList in allSteps into an array
         * and puts that array into a row of solutionSteps
         */
        for (int i= 0; i< allSteps.size(); i++){
        allSteps.get(i).toArray(solutionSteps[i]);     
        }
        return solutionSteps;
    }
    
    public static Step[][] toArrayStep(Step<byte[][]>[][] steps) {
		ArrayList<ArrayList<Step>> s = new ArrayList<ArrayList<Step>>();
		int longestRow = 0;
		for (int i=0; i<steps.length; i++) {
			ArrayList<Step> ss = new ArrayList<Step>();
			for (int j=0; j<steps[i].length; j++) {
				ss.add(new SimpleArrayStep(0, steps[i][j].theValues[0], steps[i][j].theColors[0]));
//				System.out.println(""+steps[i][j].theValues[0] + steps[i][j].theColors[0]);
//				System.out.println(""+ss.get(j).theValues[0] + ss.get(j).theColors[0]);
				if (j > longestRow) longestRow = j;
			}
			s.add(ss);
		}
		Step[][] sa = toArray(s);
		
		for (int i=0; i<sa.length; i++) {
			for (int j=0; j<sa[sa.length-1].length; j++) {
				System.out.println(""+sa[i][j]);
			}
		}
		
		return sa;
    }

}
