package interfacegraphique.drawing.shape;

import java.awt.Point;

public class Oval extends PredefinedPolygon {

	public Oval() {
		super(4);
	}

	public Oval(Point origin, int width, int height) {
		super(origin, width, height, 4);
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

}
