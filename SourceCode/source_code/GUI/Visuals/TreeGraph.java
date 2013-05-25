package GUI.Visuals;

import steps.*;
import values.constant.*;
import java.util.*;
import java.awt.*;

import javax.swing.*;

public class TreeGraph implements VisualizationGraph {

	private Step<byte[][]>[][] _steps;
	private VisualizationPanel _panel;
	private int HEIGHT;
	private int WIDTH;
	private int LEFT_EDGE;
	private int TOP_EDGE;
	private int NUM_LEVELS;
	private treeNode[] NODES;
	private BarGraph BAR_GRAPH;
	
	private final int HEAP_FONT_SIZE = 14;
	private final int ARRAY_FONT_SIZE = 18;
	private final float LINE_THICKNESS = 3;
	private final int SPACING_HEIGHT = 10;
	private final int NODE_WIDTH = 20;
	private final int NODE_HEIGHT = 20;
	private final int SPACING_WIDTH = 20;

	public TreeGraph(Step<byte[][]>[][] steps, VisualizationPanel panel, GUI.SimulationFrame.SpeedAccessor s) {
		_steps = steps;
		_panel = panel;
		BAR_GRAPH = new BarGraph(utils.Convert.toArrayStep(steps), panel, s);
	}

	public void draw(Graphics g, int index1, int index2) {
		byte[] values = _steps[index1][index2].theValues[1];
		byte[] colors = _steps[index1][index2].theColors[1];

		Image buffer = _panel.createImage(_panel.getWidth()-6, _panel.getHeight()/2-6);
		Graphics2D graphicsBuffer = (Graphics2D)buffer.getGraphics();
		
		graphicsBuffer.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		graphicsBuffer.setColor(Color.white);
		graphicsBuffer.fillRect(0, 0, buffer.getWidth(null), buffer.getHeight(null));
		
		calculateConstants(index1, index2);
		
		drawLines(graphicsBuffer, NODES);
		
		for (int i=1; i<NODES.length; i++) {
			drawNode(
					graphicsBuffer,
					values[i],
					colors[i],
					NODES[i].CenterX-(NODE_WIDTH/2),
					NODES[i].CenterY-(NODE_HEIGHT/2));
		}
		
		BAR_GRAPH.addImage(buffer);
		BAR_GRAPH.draw(g, index1, index2);
	}
	
	private void calculateConstants(int index1, int index2) {
		HEIGHT = _panel.getHeight();
		WIDTH = _panel.getWidth();
		
		/* Find the number of levels (height) of the tree */
		int num_nodes = _steps[index1][index2].theValues[1].length-1;
		NUM_LEVELS = (int)(Math.ceil((Math.log10((double)num_nodes+1)/Math.log10(2.0))));
		
		/* Find the horizontal and vertical starting points for the heap */
		int widest_row = (int)(Math.pow(2.0, NUM_LEVELS-1) * NODE_WIDTH + (Math.pow(2.0, NUM_LEVELS-1)-1) * SPACING_WIDTH);
		TOP_EDGE = HEIGHT / 4 - (NUM_LEVELS * (SPACING_HEIGHT + NODE_HEIGHT)) / 2;
		LEFT_EDGE = (int)(WIDTH / 2 - widest_row / 2);

		/* Build an array of node screen locations */		
		NODES = new treeNode[num_nodes+1];
		int row = 0, rowIndex = 0, lastX = 0;
		for (int i=1; i<=num_nodes; i++) {
			/* Build the array of nodes */
			int x, y = TOP_EDGE + row * (SPACING_HEIGHT + NODE_HEIGHT);
			if (rowIndex == 0) {
				x = (int)(LEFT_EDGE + (Math.pow(2.0, NUM_LEVELS - 1 - row) - 1) * SPACING_WIDTH);
			}
			else {
				x = (int)(lastX + (Math.pow(2.0, NUM_LEVELS - row) - 1) * SPACING_WIDTH);
			}
			lastX = x + NODE_WIDTH;
			NODES[i] = new treeNode(x + NODE_WIDTH/2, y+NODE_HEIGHT/2);
			rowIndex++;
			if (rowIndex >= Math.pow(2.0, (double)row)) {
				row++;
				rowIndex = 0;
			}
		}
	}

	private void drawNode(Graphics g, byte value, byte color, int x, int y) {
		/* Draw a filled circle for the inside of the node */	
		GradientPaint p = new GradientPaint(
                x-NODE_WIDTH/2, y, Colors.COLORS[color].darker(),
                x+NODE_WIDTH/2, y, Colors.COLORS[color].brighter(),
                true);
		Graphics2D g2d = (Graphics2D)g;
		g2d.setPaint(p);
		g2d.fillOval(x, y, NODE_WIDTH, NODE_HEIGHT);
		
		/* Draw the outline of the node */
		g2d.setStroke(new BasicStroke(LINE_THICKNESS));
		g2d.setColor(Colors.COLORS[color].darker());
		g2d.drawOval(x, y, NODE_WIDTH, NODE_HEIGHT);
		g.setColor(Color.black);
		
		/* Draw the value of the node centered inside it.  The text will be colored
		 * appropriately to show on whatever node color is given. */
		double darkness = Math.sqrt(
	    		Math.pow(Colors.COLORS[color].getRed()/255.0f - 1,2) +
	    		Math.pow(Colors.COLORS[color].getBlue()/255.0f- 1,2) +
	    		Math.pow(Colors.COLORS[color].getGreen()/255.0f - 1,2));
		double lightness = Math.sqrt(
	    		Math.pow(Colors.COLORS[color].getRed()/255.0f,2) +
	    		Math.pow(Colors.COLORS[color].getBlue()/255.0f,2) +
	    		Math.pow(Colors.COLORS[color].getGreen()/255.0f,2));
	    if (darkness < lightness) g.setColor(Color.black);
	    else g.setColor(Color.white);
		g.setFont(new Font("Arial", Font.BOLD, HEAP_FONT_SIZE));
		FontMetrics fm = g.getFontMetrics(g.getFont());
		java.awt.geom.Rectangle2D rect = fm.getStringBounds(""+value, g);
		int textHeight = (int)(rect.getHeight()); 
		int textWidth  = (int)(rect.getWidth());
		g.drawString(""+value, x+(NODE_WIDTH-textWidth)/2, y+(NODE_HEIGHT-textHeight)/2 + fm.getAscent());
	}
	
	/* Draw the lines connecting the nodes */
	private void drawLines(Graphics g, treeNode[] nodes) {
		Graphics2D g2d = (Graphics2D)g;
		g2d.setColor(Color.black);
		g2d.setStroke(new BasicStroke(LINE_THICKNESS));
		for (int i=1; i<nodes.length; i++) {
			try {
				g2d.drawLine(
						nodes[i].CenterX,
						nodes[i].CenterY,
						nodes[2*i].CenterX,
						nodes[2*i].CenterY);
			} catch (ArrayIndexOutOfBoundsException ex) {}
			try {
				g2d.drawLine(
						nodes[i].CenterX,
						nodes[i].CenterY,
						nodes[2*i+1].CenterX,
						nodes[2*i+1].CenterY);
			} catch (ArrayIndexOutOfBoundsException ex) {}
		}
	}
	
	/* Class used to make drawing the tree easier. */
	private class treeNode {
		public int CenterX;
		public int CenterY;
		
		public treeNode(int x, int y) {
			CenterX = x;
			CenterY = y;
		}
	}

}
