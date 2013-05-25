package values.constant;

import GUI.Visuals.BarGraph;


public class PresetValues {
    public static final int[] BUBBLE_SORT_BEST_CASE = {2,4,6,8,10,12,14,16,18,20};
    public static final int[] BUBBLE_SORT_WORST_CASE = {20,18,16,14,12,10,8,6,4,2};
    
    public static final int[] BUCKET_SORT_BEST_CASE = {5,6,12,17,27,29,33,34,47,48};
    public static final int[] BUCKET_SORT_WORST_CASE = {12,15,13,19,11,17,14,18,20,16};
    
    public static final int[] HEAP_SORT_BEST_CASE = {};
    public static final int[] HEAP_SORT_WORST_CASE = {};
    
    public static final int[] INSERTION_SORT_BEST_CASE = {6,9,17,19,23,26,29,35,41,50};
    public static final int[] INSERTION_SORT_WORST_CASE = {50,41,35,29,26,23,19,17,9,6};
    
    public static final int[] MERGE_SORT_BEST_CASE = {5,10,15,20,25,30,35,40};
    public static final int[] MERGE_SORT_WORST_CASE = {50,1,47,4,48,3,49,2};
    
    public static final int[] QUICK_SORT_BEST_CASE = {14,36,24,50,26,1,40,10};
    public static final int[] QUICK_SORT_WORST_CASE = {39,48,26,50,45,1};
    
    public static final int[] RANK_SORT_BEST_CASE = {};
    public static final int[] RANK_SORT_WORST_CASE = {};
    
    public static final int[] SELECTION_SORT_BEST_CASE = {5,10,15,20,25,30,35,40,45,50};
    public static final int[] SELECTION_SORT_WORST_CASE = {50,45,40,35,30,25,20,15,10,5};
    
    public static final steps.SimpleArrayStep[][] EMPTY_STEP_ARRAY = new steps.SimpleArrayStep[][] {{
    	new steps.SimpleArrayStep(
    			0,
    			new byte[]{0}, 
    			new byte[]{values.constant.Colors.BAR_EMPTY})}};

}
