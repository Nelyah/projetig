package interfacegraphique.movie;

import interfacegraphique.drawing.Drawable;
import interfacegraphique.drawing.DrawableGroup;
import interfacegraphique.movie.animation.Animation;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class KeyFrame extends Observable implements Observer, Cloneable, Serializable {

	protected Animation animation;
	protected ArrayList<DrawableGroup> groups;
	protected Drawable selectedDrawable;

	public KeyFrame() {
		this.groups = new ArrayList<DrawableGroup>();
		this.animation = null;
		this.selectedDrawable = null;
	}

	@Override
	public Object clone() {
		KeyFrame o = new KeyFrame();

		for (DrawableGroup dg : this.groups) {
			o.groups.add((DrawableGroup) dg.clone());
		}

		if (this.animation != null) {
			o.animation = (Animation) this.animation.clone();
		}

		return o;
	}

	public void initComponents() {
		for (Drawable d : this.getDrawables()) {
			d.deleteObserver(this);
			d.addObserver(this);
		}
	}

	public void add(Drawable d) {
		DrawableGroup dg = null;

		/*
		 * Si le Drawable est un DraawbleGroup alors on l'insere tel quel Sinon,
		 * c'est un Drawable simple et on l'encapusle dans un DrawableGroup.
		 */
		if (d instanceof DrawableGroup) {
			dg = (DrawableGroup) d;
		} else {
			dg = new DrawableGroup(d);
		}

		this.groups.add(dg);
		this.setChanged();
		this.notifyObservers();
	}

	public Drawable get(Drawable d) {
		Drawable drawable = null;

		for (int i = 0, l = this.groups.size(); i < l && drawable == null; i++) {
			if (this.groups.get(i).contains(d)) {
				drawable = this.groups.get(i).get(d);
			}
		}

		return drawable;
	}

	public Animation getAnimation() {
		return this.animation;
	}

	public Drawable select(Drawable d) {
		this.selectedDrawable = this.get(d);
		this.setChanged();
		this.notifyObservers();

		return this.selectedDrawable;
	}

	public ArrayList<Drawable> getDrawables() {
		ArrayList<Drawable> drawables = new ArrayList<Drawable>();

		for (DrawableGroup dg : this.groups) {
			drawables.addAll(dg.getDrawables());
		}

		return drawables;
	}

	public Drawable getDrawableOn(Point p) {
		Drawable drawable = null;
		ArrayList<Drawable> drawables = this.getDrawables();

		for (int i = 0, l = drawables.size(); i < l && drawable == null; i++) {
			Drawable d = drawables.get(i);
			if (d.isOn(p)) {
				drawable = d;
			}
		}

		return drawable;
	}

	public Drawable getSelectedDrawable() {
		return this.selectedDrawable;
	}

	public void unselect() {
		this.selectedDrawable = null;
		this.setChanged();
		this.notifyObservers();
	}

	public Drawable selectOn(Point p) {
		return this.select(this.getDrawableOn(p));
	}

	public ArrayList<DrawableGroup> getGroups() {
		return this.groups;
	}

	public void remove(Drawable d) {
		boolean removed = false;

		for (int i = 0, l = this.groups.size(); i < l && !removed; i++) {
			removed = this.groups.get(i).remove(d);
		}

		if (this.selectedDrawable == d) {
			this.selectedDrawable = null;
		}

		this.setChanged();
		this.notifyObservers();
	}

	public void removeAnimation() {
		this.setAnimation(null);
	}

	public void setAnimation(Animation a) {
		this.animation = a;
		this.setChanged();
		this.notifyObservers();
	}

	@Override
	public void update(Observable o, Object arg) {
		this.setChanged();
		this.notifyObservers(arg);
	}

	public boolean contains(Drawable d) {
		return this.getDrawables().contains(d);
	}

}
