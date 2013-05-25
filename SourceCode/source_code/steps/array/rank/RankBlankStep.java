/**
 * 
 */
package steps.array.rank;

import steps.*;

/**
 * @author Owner
 *
 */
public class RankBlankStep extends Step<byte[]> implements RankStep {

	int[] _ranks;
	
	public RankBlankStep(int paintTime, byte[] theValues, byte[] theColors, int[] ranks) {
		super(paintTime, theValues, theColors);
		_ranks = ranks;
	}
	
	@Override
	public int[] getRanks() {
		return _ranks;
	}

	/* (non-Javadoc)
	 * @see steps.array.rank.RankStep#getSortedRanks()
	 */
	@Override
	public int[] getSortedRanks() {
		return null;
	}

	/* (non-Javadoc)
	 * @see steps.array.rank.RankStep#getSortedValues()
	 */
	@Override
	public byte[] getSortedValues() {
		return null;
	}

	/* (non-Javadoc)
	 * @see steps.array.rank.RankStep#getValues()
	 */
	@Override
	public byte[] getValues() {
		return theValues;
	}

        public byte[] getSortedColors() {
            return null;
        }
}
