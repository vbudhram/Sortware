package values.constant;

import java.awt.Color;

public class Colors {
        //Bar range that is currently being looked at
	public static final byte BAR_DEFAULT = 0;
        //Bar range that is not currently being looked at
        public static final byte BAR_INACTIVE = 1;
        //bar that is in its correct position
        public static final byte BAR_SORTED = 2;
        //bar that is being compared to another bar
	public static final byte BAR_COMPARE = 3;
        //bar that is being swapped with another
	public static final byte BAR_SWAP = 4;
        //bar that is being moved to another position
        public static final byte BAR_SHIFT = 5;
        //single bar that is being observed
        public static final byte BAR_SELECT = 6;
        //bar that is being inserted
        public static final byte BAR_INSERT = 6;
        //bar that has been removed
	public static final byte BAR_EMPTY = 7;
        //bar that must be kept track of (like a pivot)
        public static final byte BAR_KEEP_TRACK = 8;
        
                    
	public static final byte BAR_INSERTATION_SHIFTED        = BAR_SHIFT;
	public static final byte BAR_INSERTATION_INSERTED       = BAR_INSERT;
	public static final byte BAR_INSERTATION_COMPARED       = BAR_COMPARE;
	public static final byte BAR_INSERTATION_REMOVED        = BAR_EMPTY;
        
	public static final byte BAR_MERGE_FINAL          	= BAR_SORTED;
	public static final byte BAR_MERGE_SORT      		= BAR_COMPARE;
	public static final byte BAR_MERGE_MERGED		= BAR_SWAP;
	public static final byte BAR_MERGE_LEFT	    		= BAR_SELECT;
	public static final byte BAR_MERGE_RIGHT	    	= BAR_DEFAULT;
	public static final byte BAR_MERGE_GRAY                 = BAR_DEFAULT;
	
	public static final byte BAR_BUBBLE_COMPARED    = BAR_COMPARE;
	public static final byte BAR_BUBBLE_SWAPED      = BAR_SWAP;
	
	public static final byte BAR_SELECTION_COMPARED         = BAR_COMPARE;
	public static final byte BAR_SELECTION_SWAPED           = BAR_SWAP;
        public static final byte BAR_SELECTION_SMALLEST_INDEX   = BAR_KEEP_TRACK;
	
	public static final byte BAR_RANK_SWAPED    = BAR_SWAP;
	public static final byte BAR_RANK_COMPARED  = BAR_COMPARE;
	public static final byte BAR_RANK_SELECTED  = BAR_SELECT;
        public static final byte BAR_RANK_UP        = BAR_SELECT;
        public static final byte BAR_RANK_HIGHLIGHT      = BAR_SELECT;
        public static final byte BAR_RANK_REMOVE      = BAR_EMPTY;
	
        public static final byte BAR_QUICK_SWAPED   = BAR_SWAP;
        public static final byte BAR_QUICK_COMPARED = BAR_COMPARE;
        public static final byte BAR_QUICK_PIVOT    = BAR_KEEP_TRACK;

        public static final byte BAR_BUCKET_SWAPED      = BAR_SWAP;
        public static final byte BAR_BUCKET_MIN         = BAR_KEEP_TRACK;
        public static final byte BAR_BUCKET_MAX         = BAR_KEEP_TRACK;
        public static final byte BAR_BUCKET_COMPARED    = BAR_COMPARE;
        public static final byte BAR_BUCKET_MOVE        = BAR_INACTIVE;
    		
	public static final byte BAR_HEAP_SWAPED    = BAR_SWAP;
	public static final byte BAR_HEAP_COMPARED  = BAR_COMPARE;
	            
	public static final Color TRANSPARENT = new Color(255, 255, 255, 0);	
	public static final Color[] COLORS 
                = new Color[] { Color.DARK_GRAY, Color.lightGray, Color.GREEN, 
                                Color.YELLOW, Color.RED, Color.BLUE, Color.CYAN, TRANSPARENT, Color.MAGENTA};
}


