package interfacegraphique.movie;

import interfacegraphique.drawing.Drawable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class Layer extends Observable implements Observer, Serializable {

	protected ArrayList<KeyFrame> keyframes;
	protected String name;

	public Layer() {
		this.name = null;
		this.keyframes = new ArrayList<KeyFrame>();
		this.add(new KeyFrame());
	}

	public void copyAt(int index) {
		KeyFrame last = null;

		for (int i = index; i >= 0 && last == null; i--) {
			last = this.get(i);
		}

		KeyFrame kf = null;

		if (last != null) {
			kf = (KeyFrame) last.clone();
		} else {
			kf = new KeyFrame();
		}

		this.add(index, kf);
	}

	public void initComponents() {
		for (KeyFrame kf : this.keyframes) {
			if (kf != null) {
				kf.initComponents();
				kf.deleteObserver(this);
				kf.addObserver(this);
			}
		}
	}

	public ArrayList<Drawable> getDrawables() {
		ArrayList<Drawable> drawables = new ArrayList<Drawable>();

		for (KeyFrame kf : this.keyframes) {
			if (kf != null) {
				for (Drawable d : kf.getDrawables()) {
					if (drawables.contains(d) == false) {
						drawables.add(d);
					}
				}
			}
		}

		return drawables;
	}

	public void add(int index, KeyFrame k) {
		if (index >= this.keyframes.size()) {
			for (int i = this.keyframes.size(); i < index; i++) {
				this.keyframes.add(null);
			}
			this.keyframes.add(index, k);

		} else {
			this.keyframes.set(index, k);
		}

		this.setChanged();
		this.notifyObservers();

		k.addObserver(this);
	}

	public void add(KeyFrame k) {
		this.add(this.keyframes.size(), k);
	}

	public KeyFrame get(int index) {
		KeyFrame kf = null;

		if (index >= 0 && index < this.keyframes.size()) {
			kf = this.keyframes.get(index);
		}

		return kf;
	}

	public String getName() {
		return name;
	}

	public void remove(Drawable d) {
		for (KeyFrame kf : this.keyframes) {
			if (kf != null) {
				kf.remove(d);
			}
		}
	}

	public void remove(int index) {
		if (index < this.keyframes.size()) {
			boolean hasAfter = false;
			for (int i = index + 1, l = this.keyframes.size(); i < l && !hasAfter; i++) {
				if (this.keyframes.get(i) != null) {
					hasAfter = true;
				}
			}

			if (hasAfter) {
				this.keyframes.set(index, null);

			} else {
				this.keyframes.remove(index);
				for (int i = index - 1; this.keyframes.get(i) == null; i--) {
					this.keyframes.remove(i);
				}
			}

			this.setChanged();
			this.notifyObservers();
		}
	}

	public void setName(String name) {
		this.name = name;
	}

	public int size() {
		return this.keyframes.size();
	}

	@Override
	public void update(Observable o, Object arg) {
		this.setChanged();
		this.notifyObservers();
	}

}
