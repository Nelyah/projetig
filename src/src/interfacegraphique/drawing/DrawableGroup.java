package interfacegraphique.drawing;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class DrawableGroup extends Drawable implements Observer, Cloneable {

	private ArrayList<Drawable> elements;

	public DrawableGroup() {
		super();

		this.elements = new ArrayList<Drawable>();
	}

	@Override
	public Object clone() {
		DrawableGroup o = (DrawableGroup) super.clone();
		o.elements = new ArrayList<Drawable>();

		for (Drawable d : this.elements) {
			Drawable dc = (Drawable) d.clone();
			o.elements.add(dc);
		}

		return o;
	}

	public DrawableGroup(Drawable d) {
		super();

		this.elements = new ArrayList<Drawable>();
		this.add(d);
	}

	public void add(Drawable d) {
		this.elements.add(d);
		this.setChanged();
		this.notifyObservers();

		d.addObserver(this);
	}

	public boolean contains(Drawable d) {
		return this.elements.contains(d);
	}

	public Drawable get(Drawable d) {
		return this.elements.get(this.elements.indexOf(d));
	}

	public ArrayList<Drawable> getDrawables() {
		return this.elements;
	}

	public boolean remove(Drawable d) {
		boolean removed = this.elements.remove(d);

		if (removed) {
			this.setChanged();
			this.notifyObservers();
		}

		return removed;
	}

	@Override
	public void update(Observable o, Object arg) {
		this.setChanged();
		this.notifyObservers();
	}

}
