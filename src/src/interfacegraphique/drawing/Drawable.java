package interfacegraphique.drawing;

import interfacegraphique.drawing.shape.Rectangle;
import interfacegraphique.tools.Editor;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Observable;

public abstract class Drawable extends Observable implements Cloneable, Serializable {

	/**
	 * @var SELECT_MARGIN la marge de selection au clique. en pixel.
	 */
	public static final int SELECT_MARGIN = 10;
	private static int uid = 0;

	protected double angle = 0;
	protected Point center;
	protected int height = 0;
	protected int id = 0;
	protected String name = "";
	protected double opacity = 1.0;
	protected Point origin;
	protected int width = 0;

	protected Drawable() {
		this.id = uid++;
		this.center = new Point(0, 0);
		this.origin = new Point(0, 0);
	}

	protected Drawable(Point origin, int width, int height) {
		this.id = uid++;
		this.origin = origin;
		this.width = width;
		this.height = height;
		this.center = new Point(0, 0);
	}

	@Override
	public Object clone() {
		Drawable o = null;

		try {
			o = (Drawable) super.clone();
			o.origin = (Point) this.origin.clone();
			o.center = (Point) this.center.clone();

		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}

		return o;
	}

	@Override
	public boolean equals(Object d) {
		return (d != null && d instanceof Drawable && ((Drawable) d).id == this.id);
	}

	public double getAngle() {
		return this.angle;
	}

	public Point getCenter() {
		return this.center;
	}

	public ArrayList<Editor> getEditors() {
		ArrayList<Editor> editors = new ArrayList<Editor>();

		editors.add(new Editor() {
			@Override
			public Object getter(Object s) {
				return ((Drawable) s).getAngle();
			}

			@Override
			public void setter(Object s, Object o) {
				((Drawable) s).setAngle((double) o);
			}
		});

		editors.add(new Editor() {
			@Override
			public Object getter(Object s) {
				return ((Drawable) s).getOpacity();
			}

			@Override
			public void setter(Object s, Object o) {
				((Drawable) s).setOpacity((double) o);
			}
		});

		editors.add(new Editor() {
			@Override
			public Object getter(Object s) {
				return ((Drawable) s).getHeight();
			}

			@Override
			public void setter(Object s, Object o) {
				int value = (new Double((double) o)).intValue();
				((Drawable) s).resize(((Drawable) s).getWidth(), value);
			}
		});

		editors.add(new Editor() {
			@Override
			public Object getter(Object s) {
				return ((Drawable) s).getWidth();
			}

			@Override
			public void setter(Object s, Object o) {
				int value = (new Double((double) o)).intValue();
				((Drawable) s).resize(value, ((Drawable) s).getHeight());
			}
		});

		editors.add(new Editor() {
			@Override
			public Object getter(Object s) {
				return ((Drawable) s).getOrigin().x;
			}

			@Override
			public void setter(Object s, Object o) {
				Drawable d = (Drawable) s;
				Point p = new Point((new Double((double) o)).intValue(), d.getOrigin().y);

				((Drawable) s).setOrigin(p);
			}
		});

		editors.add(new Editor() {
			@Override
			public Object getter(Object s) {
				return ((Drawable) s).getOrigin().y;
			}

			@Override
			public void setter(Object s, Object o) {
				Drawable d = (Drawable) s;
				Point p = new Point(d.getOrigin().x, (new Double((double) o)).intValue());

				((Drawable) s).setOrigin(p);
			}
		});

		return editors;
	}

	public int getHeight() {
		return this.height;
	}

	public int getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public double getOpacity() {
		return this.opacity;
	}

	public Point getOrigin() {
		return this.origin;
	}

	public int getWidth() {
		return this.width;
	}

	public Rectangle getWrapper() {
		return new Rectangle(this.origin, this.width, this.height);
	}

	@Override
	public int hashCode() {
		return this.id + 1;
	}

	public boolean isOn(Point p) {
		return this.getWrapper().isOn(p);
	}

	public void resize(int width, int height) {
		this.width = width;
		this.height = height;

		this.update();
		this.setChanged();
		this.notifyObservers();
	}

	public void setAngle(double a) {
		this.angle = a;
		this.setChanged();
		this.notifyObservers(null);
	}

	public void setName(String n) {
		this.name = n;
		this.setChanged();
		this.notifyObservers(null);
	}

	public void setOpacity(double o) {
		this.opacity = o;
		this.setChanged();
		this.notifyObservers(null);
	}

	public void setOrigin(Point p) {
		this.origin = p;
		this.setChanged();
		this.notifyObservers(null);
	}

	@Override
	public String toString() {
		return this.name;
	}

	public void translate(int dx, int dy) {
		this.setOrigin(new Point(dx + this.origin.x, dy + this.origin.y));
		this.update();
	}

	public void update() {
		this.setChanged();
		this.notifyObservers();
	}

}
