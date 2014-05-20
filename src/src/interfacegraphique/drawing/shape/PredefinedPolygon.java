package interfacegraphique.drawing.shape;

import java.awt.Point;
import java.util.ArrayList;

public abstract class PredefinedPolygon extends Polygon {

	private int dHeight = 0;
	private int dWidth = 0;

	protected PredefinedPolygon() {
		super(new Point(0, 0), 0, 0);

		this.update();
	}

	protected PredefinedPolygon(int countPoints) {
		super(new Point(0, 0), 0, 0);

		this.points = new ArrayList<Point>(countPoints);
		for (int i = 0; i < countPoints; i++) {
			this.points.add(new Point(0, 0));
		}

		this.update();
	}

	protected PredefinedPolygon(Point origin, int width, int height) {
		super(origin, width, height);

		this.center = new Point((width / 2), (height / 2));
		this.update();
	}

	protected PredefinedPolygon(Point origin, int width, int height, int countPoints) {
		super(origin, width, height);

		this.points = new ArrayList<Point>(countPoints);
		for (int i = 0; i < countPoints; i++) {
			this.points.add(new Point(0, 0));
		}

		this.update();
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public int getWidth() {
		return width;
	}

	public void relativeResize(int width, int height) {
		if (width <= 0 || this.dWidth > 0) {
			this.dWidth -= width;
			this.width -= width;
			this.origin.x += width;

		} else {
			this.width = width;
			this.dWidth = 0;
		}

		if (height <= 0 || this.dHeight > 0) {
			this.dHeight -= height;
			this.height -= height;
			this.origin.y += height;

		} else {
			this.height = height;
			this.dHeight = 0;
		}

		width = this.width;
		height = this.height;

		super.resize(width, height);
		this.update();
	}

	@Override
	public void resize(int width, int height) {
		this.dHeight = 0;
		this.dWidth = 0;

		super.resize(width, height);
		this.update();
	}

	@Override
	public void setOrigin(Point o) {
		super.setOrigin(o);

		if (this.points.isEmpty() == false) {
			this.update();
		}
	}

}
