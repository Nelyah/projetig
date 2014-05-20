package interfacegraphique.drawing.shape;

import java.awt.Point;

public class EquilateralTriangle extends IsoTriangle implements Symmetric {

	public EquilateralTriangle() {
		super();
	}

	public EquilateralTriangle(Point origin, int width) {
		super(origin, width, width);
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, this.getMedian(width));
	}

	@Override
	public void relativeResize(int width, int height) {
		super.relativeResize(width, this.getMedian(width));
	}

	private int getMedian(int width) {
		return (int) (width * Math.sqrt(3) / 2);
	}

	@Override
	public Point getCenter() {
		Point center = super.getCenter();
		center.y = center.y - this.width / 2 + 2 * this.width / 3;

		return center;
	}

}
