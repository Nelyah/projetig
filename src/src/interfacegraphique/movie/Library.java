package interfacegraphique.movie;

import interfacegraphique.drawing.Drawable;
import interfacegraphique.drawing.shape.Shape;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Observable;

public class Library extends Observable implements Serializable {

	protected ArrayList<Drawable> elements;

	public Library() {
		this.elements = new ArrayList<Drawable>();
	}

	public void add(Drawable d) {
		if (d.getName().isEmpty()) {
			Class dClass = d.getClass();
			int count = 0;

			for (Drawable e : this.elements) {
				if (e.getClass().equals(dClass)) {
					count++;
				}
			}

			d.setName(d.getClass().getSimpleName() + " " + (count + 1));
		}

		this.optimize();
		this.elements.add(d);
		this.setChanged();
		this.notifyObservers();
	}

	public Drawable get(int index) {
		return this.elements.get(index);
	}

	public ArrayList<Drawable> getElements() {
		return this.elements;
	}

	public void optimize() {
		for (int i = 0; i < this.elements.size();) {
			Drawable d = this.elements.get(i);
			if (d instanceof Shape && ((Shape) d).getPoints().size() < 2) {
				this.remove(i);
			} else {
				i++;
			}
		}
	}

	public void remove(Drawable d) {
		int index = this.elements.indexOf(d);
		if (index >= 0) {
			this.remove(index);
		}
	}

	public void remove(int index) {
		Drawable d = this.elements.remove(index);
		this.optimize();
		this.setChanged();
		this.notifyObservers(d);
	}

	public int size() {
		return this.elements.size();
	}

}
