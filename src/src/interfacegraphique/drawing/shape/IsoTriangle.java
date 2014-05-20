package interfacegraphique.drawing.shape;

import java.awt.Point;

public class IsoTriangle extends PredefinedPolygon {

	public IsoTriangle() {
		super(3);
	}

	public IsoTriangle(Point origin, int width, int height) {
		super(origin, width, height, 3);
	}

	@Override
	public void update() {
		Point o = this.origin;

		this.points.get(0).setLocation(o.x + (this.width / 2), o.y);
		this.points.get(1).setLocation(o.x + this.width, o.y + this.height);
		this.points.get(2).setLocation(o.x, o.y + this.height);

		super.update();
	}

}
