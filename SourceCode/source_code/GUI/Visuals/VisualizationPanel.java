package GUI.Visuals;

import java.awt.*;
import javax.swing.JPanel;

public class VisualizationPanel extends JPanel {

	private VisualizationGraph _graph;
	private int _index1, _index2;
	
	public VisualizationPanel(VisualizationGraph graph) {
		_graph = graph;
		_index1 = 0;
		_index2 = 0;
	}
	
	public void draw(int index1, int index2) {
		_index1 = index1;
		_index2 = index2;
		repaint();
	}
	
	public void paintComponent(Graphics gr)
	{
		if (_graph != null) {
			_graph.draw(gr, _index1, _index2);
		}
	}

	public void paint(Graphics gr) {
		_graph.draw(gr, _index1, _index2);
	}
	
	public void setGraph(VisualizationGraph g) {
		_graph = g;
	}
	
}
