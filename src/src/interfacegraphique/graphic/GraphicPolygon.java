package interfacegraphique.graphic;

import interfacegraphique.drawing.shape.Polygon;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

public class GraphicPolygon extends GraphicDrawable {

	public GraphicPolygon(Polygon p) {
		super(p);
	}

	@Override
	public void paintComponent(Graphics g) {
		Polygon polygon = (Polygon) this.drawable;
		ArrayList<Point> points = polygon.getPoints();

		Point origin = polygon.getOrigin();

		int countPoints = points.size();
		int[] xPoints = new int[countPoints];
		int[] yPoints = new int[countPoints];

		for (int i = 0; i < countPoints; i++) {
			Point p = points.get(i);
			xPoints[i] = p.x - origin.x;
			yPoints[i] = p.y - origin.y;
		}

		Graphics2D g2 = (Graphics2D) g;
		double opacity = polygon.getOpacity();

		g.setColor(super.alphaColor(polygon.getBackgroundColor(), opacity));
		g.fillPolygon(xPoints, yPoints, countPoints);

		g2.setStroke(new BasicStroke(polygon.getStrokeWidth()));
		g.setColor(super.alphaColor(polygon.getColor(), opacity));
		g.drawPolygon(xPoints, yPoints, countPoints);
	}

}
