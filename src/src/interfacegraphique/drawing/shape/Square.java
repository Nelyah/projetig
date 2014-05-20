package interfacegraphique.drawing.shape;

import java.awt.Point;

public class Square extends Rectangle implements Symmetric {

	public Square() {
		super();
	}

	public Square(Point origin, int width) {
		super(origin, width, width);
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, width);
	}

	@Override
	public void relativeResize(int width, int height) {
		super.relativeResize(width, width);
	}

}
