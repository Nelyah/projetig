package interfacegraphique.drawing.shape;

import java.awt.Point;
import java.util.ArrayList;

public class Rectangle extends PredefinedPolygon {

	public Rectangle() {
		super(4);
	}

	public Rectangle(Point origin, int width, int height) {
		super(origin, width, height, 4);
	}

	@Override
	public boolean isOn(Point p) {
		return ((p.x >= this.origin.x - SELECT_MARGIN) && (p.x <= this.origin.x + this.width + SELECT_MARGIN) && (p.y >= this.origin.y - SELECT_MARGIN) && (p.y <= this.origin.y + this.height + SELECT_MARGIN));
	}

	@Override
	public void update() {
		Point o = this.origin;

		this.points.get(0).setLocation(o);
		this.points.get(1).setLocation(o.x + this.width, o.y);
		this.points.get(2).setLocation(o.x + this.width, o.y + this.height);
		this.points.get(3).setLocation(o.x, o.y + this.height);

		super.update();
	}

	@Override
	public Point getCenter() {
		ArrayList<Point> points = new ArrayList<Point>();
		points.add(this.points.get(0));
		points.add(this.points.get(2));

		Point a = this.points.get(0);
		Point b = this.points.get(2);

		return new Point((a.x + b.x) / 2, (a.y + b.y) / 2);
	}

}
