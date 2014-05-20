package interfacegraphique.movie;

import interfacegraphique.drawing.Drawable;
import interfacegraphique.tools.Translator;

import java.awt.Color;
import java.io.Serializable;
import java.util.Observable;
import java.util.Observer;

public class Project extends Observable implements Observer, Serializable {

	private static final long serialVersionUID = 1L;
	private Scenario scenario;
	private String name;
	private Color backgroundColor;
	private Library library;
	private String pathname;
	private boolean edited;
	private int width;
	private int height;

	public Project() {
		this.name = Translator.getString("project") + " 1";
		this.backgroundColor = Color.WHITE;
		this.scenario = new Scenario();
		this.library = new Library();
		this.width = 800;
		this.height = 600;

		this.initComponents();
	}

	public String getPathname() {
		return this.pathname;
	}

	public int getWidth() {
		return this.width;
	}

	public int getHeight() {
		return this.height;
	}

	public void initComponents() {
		this.edited = false;

		this.library.deleteObserver(this);
		this.library.addObserver(this);

		this.scenario.initComponents();
		this.scenario.deleteObserver(this);
		this.scenario.addObserver(this);
	}

	public String getName() {
		return this.name;
	}

	public Scenario getScenario() {
		return this.scenario;
	}

	public Color getBackgroundColor() {
		return this.backgroundColor;
	}

	public void setBackgroundColor(Color color) {
		this.backgroundColor = color;
		this.setEdited(true);
	}

	public void setName(String name) {
		this.name = name;
		this.setEdited(true);
	}

	public Library getLibrary() {
		return this.library;
	}

	public boolean isEdited() {
		return this.edited;
	}

	public void setEdited(boolean e) {
		this.edited = e;
		this.setChanged();
		this.notifyObservers();
	}

	@Override
	public void update(Observable o, Object arg) {
		this.setEdited(true);

		if (o instanceof Library) {
			if (arg != null && arg instanceof Drawable) {
				this.scenario.remove((Drawable) arg);
			}
		}
	}

	public void setPathname(String pathname) {
		this.pathname = pathname;
	}

}
