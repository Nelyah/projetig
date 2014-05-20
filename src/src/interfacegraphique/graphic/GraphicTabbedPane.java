package interfacegraphique.graphic;

import interfacegraphique.movie.Project;
import interfacegraphique.movie.Scenario;
import interfacegraphique.tools.Translator;

import java.util.Observable;
import java.util.Observer;

import javax.swing.JTabbedPane;

public class GraphicTabbedPane extends JTabbedPane implements Observer {

	private DrawableProperties drawPropPane;
	private GraphicLibrary libraryPane;
	private ProjectProperties propertiesPane;
	private GraphicAnimation graphAnimPane;
	private Scenario scenario;

	public GraphicTabbedPane(Project p) {
		this.propertiesPane = new ProjectProperties(p);
		this.libraryPane = new GraphicLibrary(p);
		this.drawPropPane = new DrawableProperties();
		this.graphAnimPane = new GraphicAnimation();

		this.addTab(Translator.getString("properties"), this.propertiesPane);
		this.addTab(Translator.getString("library"), this.libraryPane);
		this.addTab(Translator.getString("animation.tab"), this.graphAnimPane);
		this.setProject(p);
	}

	public DrawableProperties getDrawableProperties() {
		return this.drawPropPane;
	}

	public GraphicLibrary getGraphicLibrary() {
		return this.libraryPane;
	}

	public ProjectProperties getProjectProperties() {
		return this.propertiesPane;
	}

	public void setProject(Project p) {
		if (this.scenario != null) {
			this.scenario.deleteObserver(this);
		}

		if (p != null) {
			this.scenario = p.getScenario();
			this.scenario.addObserver(this);

			this.propertiesPane.setProject(p);
			this.drawPropPane.setDrawable(null);
			this.libraryPane.setProject(p);

		} else {
			this.scenario = null;
		}
	}

	public void update() {
		if (this.drawPropPane.getDrawable() == null) {
			this.setComponentAt(0, this.propertiesPane);
		} else {
			this.setComponentAt(0, this.drawPropPane);
		}

		this.repaint();
	}

	@Override
	public void update(Observable o, Object arg) {
		if (o != null && o instanceof Scenario) {
			this.drawPropPane.setDrawable(this.scenario.getSelectedDrawable());
			this.graphAnimPane.setKeyFrame(this.scenario.getCurrentKeyFrame());
			this.update();
		}
	}
}
