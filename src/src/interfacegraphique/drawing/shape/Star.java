package interfacegraphique.drawing.shape;

import java.awt.Point;

public class Star extends PredefinedPolygon implements Symmetric {

	public static final int DEFAULT_VERTICES = 5;
	protected int vertices = DEFAULT_VERTICES;

	public Star() {
		super(DEFAULT_VERTICES * 2);
	}

	public Star(int vertices) {
		super(vertices * 2);

		this.vertices = vertices;
		this.update();
	}

	public Star(Point origin, int width, int height) {
		super(origin, width, height, DEFAULT_VERTICES * 2);
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, width);
	}

	@Override
	public void relativeResize(int width, int height) {
		super.relativeResize(width, width);
	}

	@Override
	public void update() {
		if (this.vertices > 0) {
			Point o = this.origin;

			double rOut = this.width;
			double rIn = (rOut / 3);
			int vertices = this.vertices * 2;
			double rCoef = (this.vertices % 2 == 0 ? -1 : 0.5);
			double hWidth = (this.width / 2);

			for (int i = 0; i < vertices; i++) {
				double r = (i % 2 == 0 ? rOut : rIn);
				double t = 2 * Math.PI * ((i - rCoef) / vertices);

				Point p = this.points.get(i);
				p.x = (int) (Math.round(o.x + r * Math.cos(t)) + hWidth);
				p.y = (int) (Math.round(o.y + r * Math.sin(t)) + hWidth);
			}

			super.update();
		}
	}

}
