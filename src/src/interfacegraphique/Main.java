package interfacegraphique;

import interfacegraphique.drawing.Drawable;
import interfacegraphique.drawing.shape.Polygon;
import interfacegraphique.drawing.shape.Shape;
import interfacegraphique.graphic.DrawableProperties;
import interfacegraphique.graphic.GraphicScenario;
import interfacegraphique.graphic.GraphicTabbedPane;
import interfacegraphique.graphic.LeftBarPane;
import interfacegraphique.graphic.Stage;
import interfacegraphique.movie.Project;
import interfacegraphique.projectmanager.ProjectManager;
import interfacegraphique.projectmanager.ProjectManagerSerial;
import interfacegraphique.projectmanager.ProjectManagerSvg;
import interfacegraphique.tools.Tools;
import interfacegraphique.tools.Translator;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.KeyStroke;

public class Main extends JFrame implements Observer {
	private final static String WINDOW_NAME = "Interface Graphique";
	private final static int WINDOW_WIDTH = 1200;
	private final static int WINDOW_HEIGHT = 800;
	private final static int RIGHT_PANEL_WIDTH = 330;
	private final static double VERTICAL_SPLITER_DIVIDER = 0.6;

	public static void errorMessage(String key) {
		JOptionPane.showMessageDialog(null, Translator.getString(key), Translator.getString("message.error"), JOptionPane.ERROR_MESSAGE);
	}

	public static void main(String[] args) {
		new Main();
	}

	private LeftBarPane leftBarPanel;
	private Project project;
	private JMenuItem projectNew, projectOpen, projectSave, projectSaveAs,
			exit, about;
	private GraphicScenario scenario;
	private Stage stage;
	private GraphicTabbedPane tabbedPanel;

	public Main() {
		super(Main.WINDOW_NAME);

		this.project = new Project();
		this.tabbedPanel = new GraphicTabbedPane(this.project);
		this.stage = new Stage(this.project.getScenario());
		this.scenario = new GraphicScenario(this.project.getScenario());
		this.leftBarPanel = new LeftBarPane(this);

		this.initMenuComponents();
		this.initComponents();
		this.projectNew();

		this.project.getScenario().notifyObservers();
		this.project.notifyObservers();

		Dimension size = Toolkit.getDefaultToolkit().getScreenSize();
		this.setPreferredSize(new Dimension(Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT));
		this.setLocation((size.width - Main.WINDOW_WIDTH) / 2, (size.height - Main.WINDOW_HEIGHT) / 2);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.pack();
		this.setVisible(true);
	}

	public boolean drawStart(final Drawable d) {
		boolean added = this.project.getScenario().add(d);

		if (added) {
			if (d instanceof Shape) {
				DrawableProperties dp = this.tabbedPanel.getDrawableProperties();

				((Shape) d).setColor(dp.getSelectedColor());
				if (d instanceof Polygon) {
					((Polygon) d).setBackgroundColor(dp.getSelectedBackgroundColor());
				}
			}

			this.project.getLibrary().add(d);
			this.stage.requestFocus();
			this.stage.drawStart(d);
			this.stage.addKeyListener(new KeyAdapter() {
				@Override
				public void keyReleased(KeyEvent e) {
					switch (e.getKeyCode()) {
					case KeyEvent.VK_ESCAPE:
						project.getLibrary().remove(d);
						stage.drawEnd();
						break;
					}
				}
			});
		}

		return added;
	}

	public void exit() {
		int choice = JOptionPane.showConfirmDialog(this, Translator.getString("message.confirm_exit"), Translator.getString("menu.file.exit"), JOptionPane.YES_NO_OPTION);
		if (choice == JOptionPane.YES_OPTION) {
			System.exit(0);
		}
	}

	private void initComponents() {
		Dimension dimension = this.tabbedPanel.getPreferredSize();
		dimension.width = Main.RIGHT_PANEL_WIDTH;
		this.tabbedPanel.setPreferredSize(dimension);

		JSplitPane jspMiddle = new JSplitPane(JSplitPane.VERTICAL_SPLIT, this.stage, this.scenario);
		jspMiddle.setDividerLocation((int) (Main.WINDOW_HEIGHT * Main.VERTICAL_SPLITER_DIVIDER));

		JPanel jpGlobal = new JPanel();
		jpGlobal.setLayout(new BorderLayout());
		jpGlobal.add(this.leftBarPanel, BorderLayout.WEST);
		jpGlobal.add(jspMiddle, BorderLayout.CENTER);

		this.getContentPane().setLayout(new BorderLayout());
		this.getContentPane().add(jpGlobal, BorderLayout.CENTER);
		this.getContentPane().add(this.tabbedPanel, BorderLayout.EAST);
	}

	private void initMenuComponents() {
		JMenuBar menuBar = new JMenuBar();
		JMenu file = new JMenu(Translator.getString("menu.file"));
		JMenu help = new JMenu(Translator.getString("menu.help"));

		this.setJMenuBar(menuBar);
		this.projectNew = new JMenuItem(Translator.getString("menu.file.new"));
		this.projectOpen = new JMenuItem(Translator.getString("menu.file.open") + "...");
		this.projectSave = new JMenuItem(Translator.getString("menu.file.save"));
		this.projectSave.setEnabled(false);
		this.projectSaveAs = new JMenuItem(Translator.getString("menu.file.save_as") + "...");
		this.exit = new JMenuItem(Translator.getString("menu.file.exit"));
		this.about = new JMenuItem(Translator.getString("menu.help.about") + "...");

		file.add(this.projectNew);
		file.add(this.projectOpen);
		file.add(this.projectSave);
		file.add(this.projectSaveAs);
		file.add(new JSeparator());
		file.add(this.exit);
		help.add(about);

		menuBar.add(file);
		menuBar.add(help);

		KeyStroke keyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_N, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());
		this.projectNew.setAccelerator(keyStroke);
		this.projectNew.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Main.this.projectNew();
			}
		});
		keyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_O, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());
		this.projectOpen.setAccelerator(keyStroke);
		this.projectOpen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Main.this.projectOpen();
			}
		});

		keyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_S, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());
		this.projectSave.setAccelerator(keyStroke);
		this.projectSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Main.this.projectSave();
			}
		});

		keyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_S, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask() + InputEvent.SHIFT_MASK);
		this.projectSaveAs.setAccelerator(keyStroke);
		this.projectSaveAs.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Main.this.projectSaveAs();
			}
		});

		keyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_Q, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask());
		this.exit.setAccelerator(keyStroke);
		this.exit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Main.this.exit();
			}
		});

		this.about.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Main.this.about();
			}
		});
	}

	public void about() {
		JOptionPane.showMessageDialog(this, Translator.getString("message.about"));
	}

	public void projectNew() {
		this.setProject(new Project());
	}

	public void projectOpen() {
		JFileChooser chooser = new JFileChooser();
		int status = chooser.showOpenDialog(this);

		if (status == JFileChooser.APPROVE_OPTION) {
			File f = chooser.getSelectedFile();
			String pathname = f.getAbsolutePath();
			String ext = Tools.getExtension(f);

			ProjectManager projectManager = null;

			switch (ext.toLowerCase()) {
			case "ig":
				projectManager = new ProjectManagerSerial(this.project);
				break;
			}

			if (projectManager != null) {
				this.setProject(projectManager.open(pathname));
			} else {
				Main.errorMessage("message.incorrect_extension");
			}
		}

	}

	public void projectSave() {
		if (this.project.getPathname() != null) {
			(new ProjectManagerSerial(this.project)).save();
		}
	}

	public void projectSaveAs() {
		JFileChooser chooser = new JFileChooser();

		int status = chooser.showSaveDialog(this);
		if (status == JFileChooser.APPROVE_OPTION) {
			File f = chooser.getSelectedFile();
			String pathname = f.getAbsolutePath();
			String ext = Tools.getExtension(f);

			if (ext == null) {
				ext = "ig";
				pathname += "." + ext;
			}

			ProjectManager projectManager = null;

			switch (ext.toLowerCase()) {
			case "ig":
				projectManager = new ProjectManagerSerial(this.project);
				break;
			case "svg":
				projectManager = new ProjectManagerSvg(this.project);
				break;
			}

			if (projectManager != null) {
				projectManager.save(pathname);
			} else {
				Main.errorMessage("message.incorrect_extension");
			}
		}
	}

	public void selectStart() {
		this.stage.selectStart();
	}

	public void setProject(Project p) {
		if (this.project != null) {
			p.deleteObserver(this);
		}

		this.project = p;
		this.project.addObserver(this);
		this.scenario.setScenario(this.project.getScenario());
		this.stage.setScenario(this.project.getScenario());
		this.tabbedPanel.setProject(this.project);

		this.update(this.project, null);
	}

	@Override
	public void update(Observable o, Object arg) {
		if (o != null) {
			if (o instanceof Project) {
				this.stage.setBackground(this.project.getBackgroundColor());
				this.setTitle(Main.WINDOW_NAME + " - " + this.project.getName());
			}
		}

		this.updateMenuComponents();
	}

	private void updateMenuComponents() {
		this.projectSave.setEnabled(this.project.getPathname() != null && this.project.isEdited());
	}

}
