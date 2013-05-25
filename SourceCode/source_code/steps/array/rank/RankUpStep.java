package steps.array.rank;

import steps.ColorIndexandIndexs;
import values.constant.Colors;
import values.constant.PaintTimes;

public class RankUpStep extends ColorIndexandIndexs implements RankStep {
    private int[] ranks;
    private byte[] values;
    private int index;
    
    public RankUpStep(byte[] values, int[] ranks, int index) {
      super(PaintTimes.BAR_RANKED, values, Colors.BAR_RANK_UP, index,
            Colors.BAR_RANK_UP, index);
      this.ranks = ranks;
      this.values = values;
      this.index = index;
    }  
    
    public int [] getRanks() {
        return this.ranks;
    }
    
    public byte [] getValues() {
        return this.values;
    }
    
    public byte [] getSortedValues() {
        return null;
    }
    
    public int [] getSortedRanks() {
        return null;
    }
    
    public byte [] getSortedColors() {
        return null;
    }
    
    public String toString() {
        return "Rank of position "+this.index+" has increased";
    }
}