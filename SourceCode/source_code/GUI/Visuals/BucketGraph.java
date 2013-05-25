package GUI.Visuals;

import java.awt.FontMetrics;
import java.awt.Image;
import java.awt.RenderingHints;
import steps.*;
import steps.array.bucket.*;
import steps.*;
import javax.swing.*;
import GUI.*;
import java.awt.*;
import java.util.*;

public class BucketGraph implements VisualizationGraph {

	private VisualizationPanel _panel;
	private Step<byte[]>[][] _steps;
	private SimulationFrame.SpeedAccessor _speed;
	private BarGraph BAR_GRAPH;
	
	private final String CONTAINER_VALUE_SPACING = "  ";
	private final int CONTAINER_SPACING = 5;
	private final int CONTAINER_HEIGHT_SPACING = 5;
	private final int ARC_RADIUS = 8;
	
	public BucketGraph(Step<byte[]>[][] steps, VisualizationPanel panel, SimulationFrame.SpeedAccessor s) {
		_panel = panel;
		_steps = steps;
		_speed = s;
		BAR_GRAPH = new BarGraph(_steps, _panel, _speed);
	}
		
	@Override
	public void draw(Graphics g, int index1, int index2) {
		//draw containers
		if (hasContainers(index1, index2)) {
			System.out.println("Has containers");
			
			Image buffer = _panel.createImage(_panel.getWidth()-6, _panel.getHeight()/2-6);
			Graphics2D bufferGraphics = (Graphics2D)buffer.getGraphics();
			bufferGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			
			bufferGraphics.setColor(Color.white);
			bufferGraphics.fillRect(0, 0, _panel.getWidth(), _panel.getHeight()/2);
			drawContainers(bufferGraphics, index1, index2);
			BAR_GRAPH.addImage(buffer);
		}
		
		BAR_GRAPH.drawBuckets(true);
		BAR_GRAPH.draw(g, index1, index2);
		BAR_GRAPH.addImage(null);
	}

	private boolean hasContainers(int index1, int index2) {
		if (_steps[index1][index2] instanceof Containers) {
			return true;
		}
		return false;
	}
	
	private class ContainerLocation {
		public int X, Y;
		
		ContainerLocation (int x, int y) {
			X = x;
			Y = y;
		}
	}
	
	private ContainerLocation[] createLocations(Graphics g, steps.array.bucket.Container[] containers) {
		ContainerLocation[] locs = new ContainerLocation[containers.length];
		
		ArrayList<ArrayList<steps.array.bucket.Container>> table = 
			new ArrayList<ArrayList<steps.array.bucket.Container>>();
		table.add(new ArrayList<steps.array.bucket.Container>());
		int totalWidth = CONTAINER_SPACING;
		for (int i=0; i<containers.length; i++) {
			totalWidth += getContainerDimensions(g, containers[i]).width + CONTAINER_SPACING;
			if (totalWidth > _panel.getWidth()) {
				totalWidth = getContainerDimensions(g, containers[i]).width + 2*CONTAINER_SPACING;
				table.add(new ArrayList<steps.array.bucket.Container>());
			}
			table.get(table.size()-1).add(containers[i]);
		}
		
		int locsIndex = 0;
		int height = 0;
		int y = (_panel.getHeight()/2 - 
				(table.size() * (getContainerDimensions(g, table.get(0).get(0)).height + CONTAINER_HEIGHT_SPACING)))/2;
		for (int i=0; i<table.size(); i++) {
			int rowWidth = CONTAINER_SPACING;
			for (int j=0; j<table.get(i).size(); j++) {
				Dimension dim = getContainerDimensions(g, table.get(i).get(j));
				rowWidth += dim.width + CONTAINER_SPACING;
				if (dim.height > 0)	height = dim.height;
			}
			int x = (_panel.getWidth() - rowWidth)/2;
			for (int j=0; j<table.get(i).size(); j++) {
				if (table.get(i).get(j).theValues.length > 0) {
					locs[locsIndex] = new ContainerLocation(x, y);
					x += getContainerDimensions(g, table.get(i).get(j)).width + CONTAINER_SPACING;
					
				}
				locsIndex++;
			}
			y += (height + CONTAINER_HEIGHT_SPACING);
		}
		return locs;
	}
	
	private void drawContainers(Graphics g, int index1, int index2) {
		// calculate dimensions and starting points for drawing
		steps.array.bucket.Container[] containers = ((Containers)_steps[index1][index2]).getContainers();
		int totalWidth = CONTAINER_SPACING;
		int totalHeight = 0;
		for (int i=0; i<containers.length; i++) {
			totalWidth += getContainerDimensions(g, containers[i]).width + CONTAINER_SPACING;
			totalHeight = getContainerDimensions(g, containers[i]).height;
		}
		int x = (_panel.getWidth() - totalWidth)/2, y = (_panel.getHeight()/2 - totalHeight)/2;
		
		// draw the containers
		g.setColor(Color.black);
		ContainerLocation[] locs = createLocations(g, containers);
		for (int i=0; i<locs.length; i++) {
			if (containers[i].theValues.length > 0 && locs[i] != null)
				drawContainer(containers[i], g, locs[i].X, locs[i].Y);
		}
	}
	
	private Dimension getContainerDimensions(Graphics g, steps.array.bucket.Container c) {
		int spacingWidth = (int)g.getFontMetrics(g.getFont()).getStringBounds(CONTAINER_VALUE_SPACING, g).getHeight();
		int totalWidth = spacingWidth;
		int totalHeight = 0;
		for (int i=0; i<c.theValues.length; i++) {
			totalHeight = (int)g.getFontMetrics(g.getFont()).getStringBounds(""+c.theValues[0], g).getHeight() * 2;
			totalWidth += (int)g.getFontMetrics(g.getFont()).getStringBounds(""+c.theValues[i], g).getWidth();
			totalWidth += spacingWidth;
		}
		return new Dimension(totalWidth, totalHeight);
	}
	
	private void drawContainer(steps.array.bucket.Container c, Graphics g, int x, int y) {
		// calculate the size of all the printed values
		Dimension dims = getContainerDimensions(g, c);
		int totalWidth = dims.width, totalHeight = dims.height;
		String allValues = CONTAINER_VALUE_SPACING;
		for (int i=0; i<c.theValues.length; i++) {
			allValues += c.theValues[i] + CONTAINER_VALUE_SPACING;
		}
		int textWidth = (int)g.getFontMetrics(g.getFont()).getStringBounds(""+allValues, g).getWidth();
		
		// draw a rectangle to fit them all
		g.drawRoundRect(x, y, totalWidth, totalHeight, ARC_RADIUS, ARC_RADIUS);
		
		// print the values in it
		g.drawString(allValues, x+(totalWidth - textWidth)/2, y+3*totalHeight/4);
	}

}
