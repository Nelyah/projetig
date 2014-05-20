package interfacegraphique.movie;

import interfacegraphique.drawing.Drawable;
import interfacegraphique.movie.animation.Animation;
import interfacegraphique.tools.Editor;
import interfacegraphique.tools.Translator;

import java.awt.Point;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class Scenario extends Observable implements Observer, Serializable {

	protected Layer currentLayer;
	protected int currentTime;
	protected int fps = 25;
	protected ArrayList<Layer> layers;
	private boolean busy = false;

	public Scenario() {
		this.currentTime = 0;
		this.layers = new ArrayList<Layer>();
		this.add(new Layer());
	}

	public boolean add(Drawable d) {
		KeyFrame kf = this.getCurrentKeyFrame();

		if (kf != null) {
			d.addObserver(this);
			kf.add(d);
		}

		return (kf != null);
	}

	public void initComponents() {
		for (Layer l : this.layers) {
			l.initComponents();
			l.deleteObserver(this);
			l.addObserver(this);
		}
	}

	public void add(Layer l) {
		l.setName(Translator.getString("scenario.layer") + " " + (this.layers.size() + 1));
		l.addObserver(this);

		this.layers.add(l);
		if (this.layers.size() == 1) {
			this.currentLayer = l;
		}

		this.setChanged();
		this.notifyObservers();
	}

	public synchronized int getCountMaxKeyFrames() {
		int max = 0;

		for (Layer l : this.layers) {
			max = Math.max(max, l.size());
		}

		return max;
	}

	public synchronized KeyFrame getCurrentKeyFrame() {
		return this.currentLayer.get(this.currentTime);
	}

	public Layer getCurrentLayer() {
		return this.currentLayer;
	}

	public int getCurrentLayerIndex() {
		return this.layers.indexOf(this.currentLayer);
	}

	public synchronized int getCurrentTime() {
		return this.currentTime;
	}

	public Drawable getDrawableOn(Point p) {
		Drawable d = null;
		KeyFrame kf = this.getCurrentKeyFrame();

		if (kf != null) {
			d = kf.getDrawableOn(p);
		}

		return d;
	}

	public synchronized int getFps() {
		return this.fps;
	}

	public ArrayList<Layer> getLayers() {
		return this.layers;
	}

	public synchronized Drawable getSelectedDrawable() {
		Drawable d = null;
		KeyFrame kf = this.getCurrentKeyFrame();

		if (kf != null) {
			d = kf.getSelectedDrawable();
		}

		return d;
	}

	public ArrayList<Drawable> getState() {
		this.busy = true;

		ArrayList<Drawable> state = new ArrayList<Drawable>();

		for (int i = this.layers.size() - 1; i >= 0; i--) {
			Layer layer = this.layers.get(i);
			KeyFrame frame = layer.get(this.currentTime);

			if (frame != null) {
				/* Recuperation de tous les Drawables */
				state.addAll(frame.getDrawables());

			} else {

				/*
				 * Recherche de la keyFrame suivante, si elle existe. Recherche
				 * de la keyFrame precedente, si elle existe Si la suivante
				 * existe, recherche de son animation.
				 */
				KeyFrame previous = null;
				int previousTime = this.currentTime;
				while (previousTime >= 0 && previous == null) {
					previous = layer.get(previousTime);
					previousTime--;
				}

				if (previous != null) {

					int layerSize = layer.size();

					if (this.currentTime < layerSize) {
						KeyFrame next = null;
						int nextTime = this.currentTime + 1;
						while (nextTime < layerSize && next == null) {
							next = layer.get(nextTime);
							nextTime++;
						}

						if (next != null) {
							Animation animation = next.getAnimation();

							if (animation != null) {
								previousTime = (previousTime < 0 ? 0 : previousTime + 1);
								nextTime--;

								double t = this.currentTime - previousTime;
								double duration = nextTime - previousTime;

								/*
								 * On anime tous les drawables existants avant
								 * et apres
								 */
								for (Drawable d : previous.getDrawables()) {
									Drawable nextD = next.get(d);

									if (nextD == null) {
										state.add(d);

									} else {

										Drawable drawable = (Drawable) nextD.clone();

										for (Editor editor : d.getEditors()) {
											double d1 = Double.parseDouble(editor.getter(d).toString());
											double d2 = Double.parseDouble(editor.getter(nextD).toString());
											double value = animation.getValue(t, d1, d2, duration);

											editor.setter(drawable, value);
										}

										state.add(drawable);
									}
								}

							} else {
								state.addAll(previous.getDrawables());
							}

						} else {
							state.addAll(previous.getDrawables());
						}

					} else {
						state.addAll(previous.getDrawables());
					}
				}

			}
		}

		this.busy = false;

		return state;
	}

	public boolean isOnKeyFrame() {
		return (this.getCurrentKeyFrame() != null);
	}

	public void remove(Drawable d) {
		for (Layer l : this.layers) {
			l.remove(d);
		}
	}

	/**
	 * Supprime le calque à l'index index
	 * 
	 * @param index l'index de du calque
	 * @throws Exception si le scenario ne possède qu'un calque
	 */
	public void remove(int index) throws Exception {
		if (this.layers.size() == 1) {
			throw new Exception("Le scenario doit contenir au moins 1 calque");
		}

		if (this.currentLayer == this.layers.get(index)) {
			this.selectLayer(index);
		}

		this.layers.remove(index);
		this.setChanged();
		this.notifyObservers();
	}

	public Drawable select(Drawable d) {
		Drawable drawable = null;
		KeyFrame kf = this.getCurrentKeyFrame();

		if (kf != null) {
			drawable = kf.select(d);
		}

		return drawable;
	}

	public void selectLayer(int index) {
		if (index < this.layers.size()) {
			this.unselect();
			this.currentLayer = this.layers.get(index);
			this.setChanged();
			this.notifyObservers();
		}
	}

	public Drawable selectOn(Point p) {
		Drawable d = null;
		KeyFrame kf = this.getCurrentKeyFrame();

		if (kf != null) {
			d = kf.selectOn(p);
		}

		return d;
	}

	public synchronized void setCurrentTime(int time) {
		if (time >= 0) {
			this.currentTime = time;
			this.setChanged();
			this.notifyObservers();
		}
	}

	public void setFps(int fps) {
		this.fps = fps;
		this.setChanged();
		this.notifyObservers();
	}

	public void unselect() {
		KeyFrame kf;
		for (Layer l : this.layers) {
			kf = l.get(this.currentTime);
			if (kf != null) {
				kf.unselect();
			}
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		if (!this.busy) {
			this.setChanged();
			this.notifyObservers(arg);
		}
	}
}