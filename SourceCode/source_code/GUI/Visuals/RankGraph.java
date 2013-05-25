package GUI.Visuals;

import GUI.*;
import steps.*;
import steps.array.rank.*;
import javax.swing.*;
import java.awt.*;
import java.awt.image.*;

public class RankGraph implements VisualizationGraph {

	private Step<byte[]>[][] _steps;
	private VisualizationPanel _panel;
	private SimulationFrame.SpeedAccessor _speed;
	private BarGraph TOP_BAR_GRAPH;
	private BarGraph BOTTOM_BAR_GRAPH;
	
	public RankGraph(Step<byte[]>[][] steps, VisualizationPanel panel, SimulationFrame.SpeedAccessor s) {
		_steps = steps;
		_panel = panel;
		_speed = s;
		BOTTOM_BAR_GRAPH = new BarGraph(_steps, _panel, _speed);
		TOP_BAR_GRAPH = new BarGraph(createTopSteps(), _panel, _speed);
		TOP_BAR_GRAPH.addImage(_panel.createImage(1,1));
	}
	
	@Override
	public void draw(Graphics g, int index1, int index2) {
	
		/* Create a buffer to prevent flicker */
		Image buffer = _panel.createImage(_panel.getWidth(), _panel.getHeight());
		Graphics2D bufferGraphics = (Graphics2D)buffer.getGraphics();
		
		/* Draw the bars and ranks */
		int topX = 3, bottomX = 0;
		int topY=3, bottomY=0;
		Image topGraph = _panel.createImage(_panel.getWidth(), _panel.getHeight());
		Image bottomGraph = _panel.createImage(_panel.getWidth(), _panel.getHeight());
		
		
		/* If the sorted ranks array is ready, then handle showing that */
		if (((RankStep)_steps[index1][index2]).getSortedRanks() != null) {
			bottomX = 3;
			bottomY = _panel.getHeight()/2;
			drawBackground(bufferGraphics);
			/* Create the top bar graph, passing a step array whose theValues equal
			 *  the ones in ((RankStep)_steps[index1][index2]).getSortedValues() */
			TOP_BAR_GRAPH.drawToGraphics(topGraph.getGraphics(), index1, index2);
			/* Make the bottom bar graph fit in the top half of the panel */
			BOTTOM_BAR_GRAPH.addImage(_panel.createImage(1,1));
			BOTTOM_BAR_GRAPH.drawToGraphics(bottomGraph.getGraphics(), index1, index2);

			/* Cut out only the bar graph part from the images, excluding the background decorations */
			Image croppedTop = _panel.createImage(new FilteredImageSource(topGraph.getSource(),
			        new CropImageFilter(3, _panel.getHeight()/2, _panel.getWidth()-3, _panel.getHeight()/2-3)));
			Image croppedBottom = _panel.createImage(new FilteredImageSource(bottomGraph.getSource(),
			        new CropImageFilter(3, _panel.getHeight()/2, _panel.getWidth()-3, _panel.getHeight()/2-3)));
			topGraph = croppedTop;
			bottomGraph = croppedBottom;
		}
		else {
			BOTTOM_BAR_GRAPH.drawToGraphics(bottomGraph.getGraphics(), index1, index2);
		}
		
		/* Integrate the two separate bar graph images into one image in the buffer */
		bufferGraphics.drawImage(topGraph, topX, topY, null);
		bufferGraphics.drawImage(bottomGraph, bottomX, bottomY, null);
		
		/* Reset the additional image to null so that there are no problems when navigating backwards. */
		BOTTOM_BAR_GRAPH.addImage(null);
		
		/* Draw the buffer containing the completed image to the panel */
		drawToPanel(g, buffer);
	}
	
	/* Create a Step array with the bottom array values for theValues */
	private RankBlankStep[][] createTopSteps() {
		RankBlankStep[][] steps = new RankBlankStep[_steps.length][_steps[_steps.length-1].length];
		for (int i=0; i<_steps.length; i++) {
			for (int j=0; j<_steps[_steps.length-1].length; j++) {
				steps[i][j] = new RankBlankStep(
						values.constant.PaintTimes.BAR_RANKED,
						((RankStep)_steps[i][j]).getSortedValues(),
						((RankStep)_steps[i][j]).getSortedColors(), 
						((RankStep)_steps[i][j]).getSortedRanks()); 
			}
		}
		return steps;
	}
	
	/* Redraw the background since we cropped it out */
	private void drawBackground(Graphics g) {
		/* Paint the background white */
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, _panel.getWidth(), _panel.getHeight());

		/* Draw the frame */
		g.setColor(Color.DARK_GRAY);
		g.draw3DRect(0, 0, _panel.getWidth() - 1, _panel.getHeight() - 1, true);
		g.draw3DRect(2, 2, _panel.getWidth() - 5, _panel.getHeight() - 5, true);
	}
	
	/* Draw Image i to _panel */
	private void drawToPanel(Graphics g, Image i) {
		g.drawImage(i, 0, 0, null);
	}

}
