package steps.array.rank;

import steps.ColorIndexesTwoColors;
import values.constant.Colors;
import values.constant.PaintTimes;

public class RankRemoveStep extends ColorIndexesTwoColors implements RankStep{
    
        private int index1,index2;
        private int [] ranks, sortedranks;
        private byte [] values, values2, sortedColors;

	public RankRemoveStep(byte[] values, byte [] values2,
                int [] ranks, int [] sortedranks, int index1, int index2) {
		super(PaintTimes.RANK_SWAP, values, 
                        Colors.BAR_RANK_REMOVE, Colors.BAR_RANK_HIGHLIGHT, 
                                index1, index2);
		this.index1 = index1;
                this.index2 = index2;
                this.values = values;
                this.values2 = values2;
                this.ranks = ranks;
                this.sortedranks = sortedranks;
                
                this.sortedColors = new byte[values.length];
                
                for (int x=0;x<values.length;x++) {
                    if (values[x]==-1)
                        theColors[x]=5;
                    else if (x==index1) theColors[x]=2;
                    else theColors[x]=0;
                    if (values2[x]==-1)
                        this.sortedColors[x]=5;
                    else if (x==index2) this.sortedColors[x]=2;
                    else this.sortedColors[x]=0;
                }
	}
        
        public int [] getRanks() {
          return this.sortedranks;   
        }
        
        public int [] getSortedRanks() {
            return this.ranks;
        }
        
        public byte [] getValues() {
            return this.values;
        }
        
        public byte [] getSortedValues() {
            return this.values2;
        }
        
        public byte [] getSortedColors() {
            return this.sortedColors;
        }
        
        public String toString() {
            return "Ranked array index "+index2+" placed in sorted array index "+index1;
        }
}