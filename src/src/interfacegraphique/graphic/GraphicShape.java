package interfacegraphique.graphic;

import interfacegraphique.drawing.Drawable;
import interfacegraphique.drawing.shape.Shape;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

public class GraphicShape extends GraphicDrawable {

	public GraphicShape(Drawable d) {
		super(d);
	}

	@Override
	public void paintComponent(Graphics g) {
		Shape shape = (Shape) this.drawable;
		Point origin = shape.getOrigin();
		ArrayList<Point> points = shape.getPoints();

		int countPoints = points.size();
		int[] xPoints = new int[countPoints];
		int[] yPoints = new int[countPoints];

		for (int i = 0; i < countPoints; i++) {
			Point p = points.get(i);
			xPoints[i] = p.x - origin.x;
			yPoints[i] = p.y - origin.y;
		}

		Graphics2D g2 = (Graphics2D) g;
		double opacity = shape.getOpacity();

		g2.setStroke(new BasicStroke(shape.getStrokeWidth()));
		g.setColor(super.alphaColor(shape.getColor(), opacity));
		g.drawPolyline(xPoints, yPoints, countPoints);
	}

}
