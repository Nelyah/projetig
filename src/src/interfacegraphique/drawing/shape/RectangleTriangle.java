package interfacegraphique.drawing.shape;

import java.awt.Point;

public class RectangleTriangle extends PredefinedPolygon {

	public RectangleTriangle() {
		super(3);
	}

	public RectangleTriangle(Point origin, int width, int height) {
		super(origin, width, height, 3);
	}

	@Override
	public void update() {
		Point o = this.origin;

		this.points.get(0).setLocation(o);
		this.points.get(1).setLocation(o.x + this.width, o.y + this.height);
		this.points.get(2).setLocation(o.x, o.y + this.height);

		super.update();
	}

}
