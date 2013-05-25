package GUI.Visuals;

import steps.SimpleArrayStep;
import steps.Step;
import steps.heap.HeapStep;
import steps.array.bucket.Bucket;
import steps.array.bucket.BucketSortVaribles;
import steps.array.rank.*;
import java.util.*;
import javax.swing.*;
import javax.swing.Timer;

import java.awt.*;

import utils.Convert;
import values.constant.*;

public class BarGraph implements VisualizationGraph,
		java.awt.event.ActionListener {
	private Step<byte[]>[][] _steps;
	private VisualizationPanel _panel;

	private int STARTING_POSITION;
	private int SPACING_WIDTH;
	private int NUM_BARS;
	private int BOTTOM;
	private int animationStep1;
	private int animationStep2;
	private int swapIndex1;
	private int swapIndex2;
	private int currentSwapX1;
	private int currentSwapX2;
	private int endSwapX1;
	private int endSwapX2;
	private int currentMoveX;
	private int startMoveX;
	private int endMoveX;
	private javax.swing.Timer _animationTimer;
	private GUI.SimulationFrame.SpeedAccessor speed;
	private int ANIMATION_DELAY;
	private boolean HAS_ADDITIONAL_IMAGE;
	private int HEIGHT_MULTIPLIER;
	private boolean _drawBuckets;

	private final int MAX_SPACING = 15;
	private final int MIN_SPACING = 10;
	private final int BAR_WIDTH = 12;
	private final int ARRAY_HEIGHT_MULTIPLIER = 4;
	private final int HEAP_HEIGHT_MULTIPLIER = 1;
	private final int BAR_TEXT_SPACING = 20;
	private final int ANIMATION_FRAMES = 10;
	private final int LINE_THICKNESS = 3;

	/* Constructor */
	public BarGraph(Step<byte[]>[][] Steps, VisualizationPanel Panel,
			GUI.SimulationFrame.SpeedAccessor s) {
		_steps = Steps;
		_panel = Panel;
		_animationTimer = new Timer(s.getSpeed() / ANIMATION_FRAMES + 1, this);
		_animationTimer.setInitialDelay(0);
		speed = s;
		HAS_ADDITIONAL_IMAGE = false;
		_additionalImage = null;
		_drawBuckets = false;
	}

	/*
	 * ************************************************************************************
	 *  Public drawing methods for creating visuals 
	 **************************************************************************************
	 */
	
	public void draw(Graphics g, int index1, int index2) {
		if (index1 != animationStep1 || index2 != animationStep2) {
			this._animationTimer.stop();
		}
		animationStep1 = index1;
		animationStep2 = index2;

		/* Create a buffer to prevent flicker */
		Image buffer = _panel
				.createImage(_panel.getWidth(), _panel.getHeight());
		Graphics2D bufferGraphics = (Graphics2D)buffer.getGraphics();
		
		/* Draw the bars to the buffer */
		drawToGraphics(bufferGraphics, index1, index2);
		
		/* Transfer the contents of the buffer to the GUI panel */
		drawToPanel(g, buffer);
	}

	public void drawToGraphics(Graphics g, int index1, int index2) {
		if (index1 != animationStep1 || index2 != animationStep2) {
			this._animationTimer.stop();
		}
		animationStep1 = index1;
		animationStep2 = index2;
		
		Graphics2D bufferGraphics = (Graphics2D)g;
		bufferGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		/* Calculate the constants used in drawing the bars */
		calculateConstants(index1, index2);
		int currentX = STARTING_POSITION;

		/* Color bars green where appropriate */
		if (!HAS_ADDITIONAL_IMAGE) {
			colorBarsGreen(animationStep1, animationStep2);
		}
		
		/* Draw the background */
		drawBackground(bufferGraphics);

		/* Draw additional image if applicable */
		if (HAS_ADDITIONAL_IMAGE) {
			bufferGraphics.drawImage(_additionalImage, 3, 3, null);
		}

		/* See if there are any values that need to be animated */
		Vector<Integer> swaps = findSwaps(index1, index2);
		if (swaps.size() == 2) {
			swapIndex1 = swaps.get(0).intValue();
			swapIndex2 = swaps.get(1).intValue();
		} else {
			swapIndex1 = swapIndex2 = -1;
		}
		int moveIndex = findMove(index1, index2);

		/* Draw the bars */
		for (int i = 0; i < _steps[index1][index2].theValues.length; i++) {
			if (i != swapIndex1 && i != swapIndex2 && i != moveIndex) {
				drawNextRectangle(bufferGraphics, currentX,
						_steps[index1][index2].theValues[i],
						_steps[index1][index2].theColors[i]);
			} else {
				if (swaps.size() == 2) {
					if (i == swapIndex1 && !_animationTimer.isRunning()) {
						currentSwapX2 = currentX;
						endSwapX1 = currentX;
					} else if (i == swapIndex2 && !_animationTimer.isRunning()) {
						currentSwapX1 = currentX;
						endSwapX2 = currentX;
					}
				}
				if (moveIndex >= 0 && (i == moveIndex)
						&& !_animationTimer.isRunning()) {
					endMoveX = currentX;
					startMoveX = currentX - BAR_WIDTH - SPACING_WIDTH;
					currentMoveX = currentX - BAR_WIDTH - SPACING_WIDTH;
				}
			}
			currentX += BAR_WIDTH + SPACING_WIDTH;
		}
		
		/* Show ranks if this is a rank step */
		if (_steps[index1][index2] instanceof RankStep) {
			drawRanks(bufferGraphics, index1, index2);
		}

		/* Show buckets if this is a bucket step */
		if (hasBuckets(index1, index2)) {
			drawBuckets(bufferGraphics, index1, index2);
		}
		
		/* Handle moving animation if this is a move step */
		if (moveIndex >= 0) {
			_animationTimer.setDelay(ANIMATION_DELAY);
			currentMoveX += Math.abs(endMoveX - startMoveX) / ANIMATION_FRAMES;
			if (currentMoveX > endMoveX)
				currentMoveX = endMoveX;
			drawNextRectangle(
					bufferGraphics,
					currentMoveX,
					_steps[index1][index2].theValues[moveIndex],
					_steps[index1][index2].theColors[moveIndex]);
			if (!_animationTimer.isRunning())
				_animationTimer.start();
			if (currentMoveX >= endMoveX)
				_animationTimer.stop();
		}

		/* Handle swapping animation if this is a swap step */
		if (swaps.size() == 2) {
			_animationTimer.setDelay(ANIMATION_DELAY);
			currentSwapX1 -= Math.abs(endSwapX1 - endSwapX2) / ANIMATION_FRAMES;
			if (currentSwapX1 < endSwapX1)
				currentSwapX1 = endSwapX1;
			currentSwapX2 += Math.abs(endSwapX1 - endSwapX2) / ANIMATION_FRAMES;
			if (currentSwapX2 > endSwapX2)
				currentSwapX2 = endSwapX2;
			drawNextRectangle(
					bufferGraphics,
					currentSwapX1,
					_steps[index1][index2].theValues[swapIndex1],
					_steps[index1][index2].theColors[swapIndex1]);
			drawNextRectangle(
					bufferGraphics,
					currentSwapX2,
					_steps[index1][index2].theValues[swapIndex2],
					_steps[index1][index2].theColors[swapIndex2]);
			if (!_animationTimer.isRunning())
				_animationTimer.start();
			if (currentSwapX1 <= endSwapX1 && currentSwapX2 >= endSwapX2)
				_animationTimer.stop();
		}
	}
	
	/*
	 * ************************************************************************************
	 *  Helper methods for visualization
	 **************************************************************************************
	 */

	/* Calculate constants for use in visuals */
	private void calculateConstants(int i1, int i2) {
		/* Calculate the animation delay */
		ANIMATION_DELAY = speed.getSpeed() / (ANIMATION_FRAMES + 2);

		/* Get the number of bars in this data set */
		NUM_BARS = _steps[i1][i2].theValues.length;

		/* Configure constants dependent on any additional images */
		if (_additionalImage != null)
			HAS_ADDITIONAL_IMAGE = true;
		else
			HAS_ADDITIONAL_IMAGE = false;
		double offset = 2;
		HEIGHT_MULTIPLIER = ARRAY_HEIGHT_MULTIPLIER;
		if (HAS_ADDITIONAL_IMAGE) {
			offset = 4.0 / 3.0;
			HEIGHT_MULTIPLIER = HEAP_HEIGHT_MULTIPLIER;
		}

		/* Center the bars vertically according to their height */
		byte[] lastValues = _steps[_steps.length - 1][_steps[_steps.length - 1].length - 1].theValues;
		int max = lastValues[lastValues.length - 1];
		BOTTOM = (int) (_panel.getHeight() / offset + (max / 2 * HEIGHT_MULTIPLIER));

		/*
		 * Space the bars appropriately to fill the panel according to the
		 * number of bars
		 */
		SPACING_WIDTH = MAX_SPACING - (NUM_BARS * 1);
		if (SPACING_WIDTH < MIN_SPACING)
			SPACING_WIDTH = MIN_SPACING;

		/* Center the bars horizontally according to the number of bars */
		STARTING_POSITION = ((int) (_panel.getWidth() / 2))
				- (NUM_BARS * (BAR_WIDTH + SPACING_WIDTH)) / 2;
	}
	/* Draw the background for the visuals, a white fill with double outline frame */
	private void drawBackground(Graphics g) {
		/* Paint the background white */
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, _panel.getWidth(), _panel.getHeight());

		/* Draw the frame */
		g.setColor(Color.DARK_GRAY);
		g.draw3DRect(0, 0, _panel.getWidth() - 1, _panel.getHeight() - 1, true);
		g.draw3DRect(2, 2, _panel.getWidth() - 5, _panel.getHeight() - 5, true);
	}
	/* Draw the rectangle representing a value in a color at point x on Graphics g */
	private void drawNextRectangle(Graphics g, int x, byte value, byte color) {
		/* Draw a rectangle of appropriate size and color, with a gradient fill */
		g.setColor(Colors.COLORS[color]);
		if (shouldBeDrawn(value, color)) {
			GradientPaint p = new GradientPaint(x, BOTTOM
					- (value * HEIGHT_MULTIPLIER), Colors.COLORS[color]
					.darker(), x + ((int) BAR_WIDTH / 2), BOTTOM
					- (value * HEIGHT_MULTIPLIER), Colors.COLORS[color]
					.brighter(), true);
			Graphics2D g2d = (Graphics2D) g;
			g2d.setPaint(p);
			g2d.fill(new java.awt.geom.Rectangle2D.Double(x, BOTTOM
					- (value * HEIGHT_MULTIPLIER), BAR_WIDTH, value
					* HEIGHT_MULTIPLIER));
			g2d.draw3DRect(x, BOTTOM - (value * HEIGHT_MULTIPLIER), BAR_WIDTH,
					value * HEIGHT_MULTIPLIER, true);
			g2d.setColor(Color.black);
			g2d.drawString("" + value, x, BOTTOM + BAR_TEXT_SPACING);
		}
	}
	/* Draw the Image i to the VisualizationPanel */
	private void drawToPanel(Graphics g, Image i) {
		g.drawImage(i, 0, 0, null);
	}
	/* Colors bars which have reached their final destination green. */
	private void colorBarsGreen(int index1, int index2) {
		byte[] finalSet = _steps[_steps.length - 1][_steps[_steps.length - 1].length - 1].theValues;
		boolean[] finished = new boolean[finalSet.length];
		for (int i = 0; i < finished.length; i++)
			finished[i] = false;

		for (int k = 0; k < _steps[index1][index2].theValues.length; k++) {
			if (_steps[index1][index2].theValues[k] == finalSet[k] && finished[k] == false) {
				if (_steps[index1][index2].theColors[k] == Colors.BAR_DEFAULT ||
						(index1 == _steps.length - 1 && index2 == _steps[_steps.length-1].length - 1 
								&& _steps[index1][index2].theColors[k] != Colors.BAR_EMPTY))
					((SimpleArrayStep) _steps[index1][index2]).setColor(k,
							Colors.BAR_SORTED);
			} else
				finished[k] = true;
		}
	}
	/* Checks whether a rectangle should be drawn */
	private boolean shouldBeDrawn(byte value, byte color) {
		if (color != Colors.BAR_EMPTY && value >= 0)
			return true;
		return false;
	}
	/* Checks whether a rectangle should be drawn */
	private boolean shouldBeDrawn(int index1, int index2, int bar) {
		return shouldBeDrawn(
				_steps[index1][index2].theValues[bar],
				_steps[index1][index2].theColors[bar]);
	}
	
	/*
	 * ************************************************************************************
	 *  Methods for handling animations
	 **************************************************************************************
	 */
	
	/* Redraws the panel when a timer event is received */
	public void actionPerformed(java.awt.event.ActionEvent evt) {
		_panel.draw(animationStep1, animationStep2);
	}
	/* Find any bars that need to be animated as swap */
	private Vector<Integer> findSwaps(int index1, int index2) {
		Vector<Integer> indexes = new Vector<Integer>();
		for (int i = 0; i < _steps[index1][index2].theColors.length; i++) {
			if (_steps[index1][index2].theColors[i] == Colors.BAR_SWAP) {
				indexes.add(new Integer(i));
			}
		}
		return indexes;
	}
	/* Find any bars that need to be animated as shift */
	private int findMove(int index1, int index2) {
		int index = -1;
		for (int i = 0; i < _steps[index1][index2].theColors.length; i++) {
			if (_steps[index1][index2].theColors[i] == Colors.BAR_SHIFT) {
				index = i;
				break;
			}
		}
		return index;
	}
	
	/*
	 * ************************************************************************************
	 *  Methods for handling specialized visuals 
	 **************************************************************************************
	 */
	
	/* Draw rank values above the bars for rank sort */
	private void drawRanks(Graphics g, int index1, int index2) {
		int x = STARTING_POSITION;
		int y;
		g.setColor(Color.black);
		int[] ranks = ((RankStep)(_steps[index1][index2])).getRanks();
		for (int i=0; i<ranks.length; i++) {
			y = BOTTOM - (_steps[index1][index2].theValues[i]) * HEIGHT_MULTIPLIER - BAR_TEXT_SPACING/4;
			if (ranks[i] >= 0 && shouldBeDrawn(index1, index2, i))
				g.drawString(""+ranks[i], (int)(x+g.getFontMetrics(g.getFont()).getStringBounds(""+ranks[i], g).getWidth()/2), y);
			x += BAR_WIDTH + SPACING_WIDTH;
		}
	}
	/* Check if this step has buckets to be shown */
	private boolean hasBuckets(int index1, int index2) {
		if (_steps[index1][index2] instanceof BucketSortVaribles && _drawBuckets) {
			return true;
		}
		return false;
	}
	/* Draw buckets over bars for bucket sort */
	private void drawBuckets(Graphics g, int index1, int index2) {
		Bucket[] buckets = ((BucketSortVaribles)_steps[index1][index2]).getBuckets();
		g.setColor(Color.black);
		((Graphics2D)g).setStroke(new BasicStroke(LINE_THICKNESS));
		for (int i=0; i<buckets.length; i++) {
			Bucket b = buckets[i];
			int height = Math.min(
					BOTTOM - _steps[index1][index2].theValues[b.theStartIndex] * HEIGHT_MULTIPLIER,
					BOTTOM - _steps[index1][index2].theValues[b.theEndIndex] * HEIGHT_MULTIPLIER);
			//left line
			g.drawLine(
					STARTING_POSITION + (b.theStartIndex * (BAR_WIDTH + SPACING_WIDTH)),
					BOTTOM,
					STARTING_POSITION + (b.theStartIndex * (BAR_WIDTH + SPACING_WIDTH)) - SPACING_WIDTH/2,
					height);
			//middle line
			g.drawLine(
					STARTING_POSITION + (b.theStartIndex * (BAR_WIDTH + SPACING_WIDTH)),
					BOTTOM,
					STARTING_POSITION + (b.theEndIndex * (BAR_WIDTH + SPACING_WIDTH) + BAR_WIDTH),
					BOTTOM);
			//right line
			g.drawLine(
					STARTING_POSITION + (b.theEndIndex * (BAR_WIDTH + SPACING_WIDTH) + BAR_WIDTH),
					BOTTOM,
					STARTING_POSITION + (b.theEndIndex * (BAR_WIDTH + SPACING_WIDTH)) + BAR_WIDTH + SPACING_WIDTH/2,
					height);
		}
	}	
	/* if an image is added it will be drawn in the top half and the bar
	 * graph will be drawn in the bottom half */
	private Image _additionalImage;
	/* Add an additional image to be drawn with the bar graph */
	public void addImage(Image i) {
		_additionalImage = i;
	}
	/* Set whether or not to draw buckets on this bar graph */
	public void drawBuckets(boolean b) {
		_drawBuckets = b;
	}
}
