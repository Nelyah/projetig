package interfacegraphique.drawing.shape;

import interfacegraphique.drawing.Drawable;
import interfacegraphique.tools.Editor;

import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;

public class Shape extends Drawable {

	/**
	 * Couleur du tracé
	 */
	protected Color color = new Color(0, 0, 0, 255);

	/**
	 * Point constituants le tracé
	 */
	protected ArrayList<Point> points;

	/**
	 * Taille du tracé
	 */
	protected int strokeWidth = 1;

	public Shape() {
		super();

		this.points = new ArrayList<Point>();
	}

	public Shape(ArrayList<Point> points) {
		if (points.size() < 1) {
			throw new IllegalArgumentException("Le tableau doit contenir au moins 2 points");
		}

		this.points = points;
		this.center = this.getWrapper().getCenter();
		this.origin = this.points.get(0);
	}

	public Shape(Point center, ArrayList<Point> points) {
		if (points.size() < 1) {
			throw new IllegalArgumentException("Le tableau doit contenir au moins 2 points");
		}

		this.points = points;
		this.center = center;
		this.origin = this.points.get(0);
	}

	protected Shape(Point origin, int width, int height) {
		super(origin, width, height);

		this.points = new ArrayList<Point>();
	}

	public void addPoint(Point p) {
		this.points.add(p);
		if (this.points.size() == 1) {
			this.origin = this.points.get(0);
		}

		this.setChanged();
		this.notifyObservers(null);
	}

	@Override
	public Object clone() {
		Shape o = (Shape) super.clone();
		Color c = this.color;
		o.color = new Color(c.getRed(), c.getGreen(), c.getBlue(), c.getAlpha());

		o.points = new ArrayList<Point>();
		for (Point p : this.points) {
			o.points.add(new Point(p));
		}

		return o;

	}

	public Color getColor() {
		return this.color;
	}

	@Override
	public ArrayList<Editor> getEditors() {
		ArrayList<Editor> editors = super.getEditors();

		editors.add(new Editor() {
			@Override
			public Object getter(Object s) {
				return ((Shape) s).getColor().getRed();
			}

			@Override
			public void setter(Object s, Object o) {
				Color c = ((Shape) s).getColor();
				int value = (new Double((double) o)).intValue();
				((Shape) s).setColor(new Color(value, c.getGreen(), c.getBlue(), c.getAlpha()));
			}
		});

		editors.add(new Editor() {
			@Override
			public Object getter(Object s) {
				return ((Shape) s).getColor().getGreen();
			}

			@Override
			public void setter(Object s, Object o) {
				Color c = ((Shape) s).getColor();
				int value = (new Double((double) o)).intValue();
				((Shape) s).setColor(new Color(c.getRed(), value, c.getBlue(), c.getAlpha()));
			}
		});

		editors.add(new Editor() {
			@Override
			public Object getter(Object s) {
				return ((Shape) s).getColor().getBlue();
			}

			@Override
			public void setter(Object s, Object o) {
				Color c = ((Shape) s).getColor();
				int value = (new Double((double) o)).intValue();
				((Shape) s).setColor(new Color(c.getRed(), c.getGreen(), value, c.getAlpha()));
			}
		});

		editors.add(new Editor() {
			@Override
			public Object getter(Object s) {
				return ((Shape) s).getColor().getAlpha();
			}

			@Override
			public void setter(Object s, Object o) {
				Color c = ((Shape) s).getColor();
				int value = (new Double((double) o)).intValue();
				((Shape) s).setColor(new Color(c.getRed(), c.getGreen(), c.getBlue(), value));
			}
		});

		editors.add(new Editor() {
			@Override
			public Object getter(Object s) {
				return ((Shape) s).getStrokeWidth();
			}

			@Override
			public void setter(Object s, Object o) {
				((Shape) s).setStrokeWidth((new Double((double) o)).intValue());
			}
		});

		return editors;
	}

	public ArrayList<Point> getPoints() {
		return this.points;
	}

	public int getStrokeWidth() {
		return this.strokeWidth;
	}

	@Override
	public Point getCenter() {
		return this.getWrapper().getCenter();
	}

	@Override
	public Rectangle getWrapper() {
		int o = this.origin.x;
		int n = this.origin.y;
		int e = 0;
		int s = 0;

		for (Point p : this.points) {
			if (p.x < o) {
				o = p.x;
			}

			if (p.x > e) {
				e = p.x;
			}

			if (p.y < n) {
				n = p.y;
			}

			if (p.y > s) {
				s = p.y;
			}
		}

		o--;
		n--;
		e++;
		s++;

		return new Rectangle(new Point(o, n), (e - o), (s - n));
	}

	public void movePoint(int index, Point p) {
		this.points.get(index).setLocation(p);
		this.setChanged();
		this.notifyObservers();
	}

	public void setColor(Color c) {
		if (c == null) {
			this.color = new Color(0, 0, 0, 0);
		} else {
			this.color = c;
		}

		this.setChanged();
		this.notifyObservers();
	}

	@Override
	public void setOrigin(Point o) {
		int dx = o.x - this.origin.x;
		int dy = o.y - this.origin.y;

		for (Point p : this.points) {
			p.translate(dx, dy);
		}

		super.setOrigin(o);
	}

	public void setStrokeWidth(int sw) {
		this.strokeWidth = sw;
		this.setChanged();
		this.notifyObservers();
	}
}
