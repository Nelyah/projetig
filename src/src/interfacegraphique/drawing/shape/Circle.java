package interfacegraphique.drawing.shape;

import java.awt.Point;

public class Circle extends Oval implements Symmetric {

	public Circle() {
		super();
	}

	public Circle(Point origin, int radius) {
		super(origin, radius * 2, radius * 2);
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
