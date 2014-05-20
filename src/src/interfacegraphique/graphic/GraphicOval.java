package interfacegraphique.graphic;

import interfacegraphique.drawing.shape.Oval;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class GraphicOval extends GraphicDrawable {

	public GraphicOval(Oval o) {
		super(o);
	}

	@Override
	public void paintComponent(Graphics g) {
		Oval oval = (Oval) this.drawable;

		((Graphics2D) g).setStroke(new BasicStroke(oval.getStrokeWidth()));

		g.setColor(oval.getBackgroundColor());
		g.fillOval(0, 0, oval.getWidth(), oval.getHeight());

		g.setColor(oval.getColor());
		g.drawOval(0, 0, oval.getWidth(), oval.getHeight());
	}

}
