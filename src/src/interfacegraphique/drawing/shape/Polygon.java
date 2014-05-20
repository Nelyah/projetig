package interfacegraphique.drawing.shape;

import interfacegraphique.tools.Editor;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;

public class Polygon extends Shape {

	protected Color backgroundColor = new Color(255, 255, 255, 0);

	public Polygon() {
		super();
	}

	public Polygon(ArrayList<Point> points) {
		super(points);
	}

	public Polygon(Point center, ArrayList<Point> points) {
		super(center, points);
	}

	protected Polygon(Point origin, int width, int height) {
		super(origin, width, height);
	}

	@Override
	public Object clone() {
		Polygon o = (Polygon) super.clone();

		Color c = this.backgroundColor;
		o.backgroundColor = new Color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha());

		return o;
	}

	public Color getBackgroundColor() {
		return this.backgroundColor;
	}

	@Override
	public ArrayList<Editor> getEditors() {
		ArrayList<Editor> editors = super.getEditors();

		editors.add(new Editor() {
			@Override
			public Object getter(Object s) {
				return ((Polygon) s).getBackgroundColor().getRed();
			}

			@Override
			public void setter(Object s, Object o) {
				Color c = ((Polygon) s).getBackgroundColor();
				int value = (new Double((double) o)).intValue();
				((Polygon) s).setBackgroundColor(new Color(value, c.getGreen(), c.getBlue(), c.getAlpha()));
			}
		});

		editors.add(new Editor() {
			@Override
			public Object getter(Object s) {
				return ((Polygon) s).getBackgroundColor().getGreen();
			}

			@Override
			public void setter(Object s, Object o) {
				Color c = ((Polygon) s).getBackgroundColor();
				int value = (new Double((double) o)).intValue();
				((Polygon) s).setBackgroundColor(new Color(c.getRed(), value, c.getBlue(), c.getAlpha()));
			}
		});

		editors.add(new Editor() {
			@Override
			public Object getter(Object s) {
				return ((Polygon) s).getBackgroundColor().getBlue();
			}

			@Override
			public void setter(Object s, Object o) {
				Color c = ((Polygon) s).getBackgroundColor();
				int value = (new Double((double) o)).intValue();
				((Polygon) s).setBackgroundColor(new Color(c.getRed(), c.getGreen(), value, c.getAlpha()));
			}
		});

		editors.add(new Editor() {
			@Override
			public Object getter(Object s) {
				return ((Polygon) s).getBackgroundColor().getAlpha();
			}

			@Override
			public void setter(Object s, Object o) {
				Color c = ((Polygon) s).getBackgroundColor();
				int value = (new Double((double) o)).intValue();
				((Polygon) s).setBackgroundColor(new Color(c.getRed(), c.getGreen(), c.getBlue(), value));
			}
		});

		return editors;
	}

	public void setBackgroundColor(Color c) {
		if (c == null) {
			this.backgroundColor = new Color(0, 0, 0, 0);
		} else {
			this.backgroundColor = c;
		}

		this.setChanged();
		this.notifyObservers(null);
	}
}
