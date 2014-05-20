package interfacegraphique.graphic;

import interfacegraphique.movie.Project;
import interfacegraphique.tools.DocumentAdapter;
import interfacegraphique.tools.Translator;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;

public class ProjectProperties extends JPanel {

	private JTextField jtfProjectName;
	private JTextField jtfFps;
	private JTextField jtfColorChooser;
	private Project project;

	public ProjectProperties(Project project) {
		super();

		this.jtfColorChooser = new JTextField();
		this.jtfFps = new JTextField();
		this.jtfProjectName = new JTextField();

		this.initComponents();
		this.setProject(project);
	}

	public void initComponents() {
		this.setLayout(new BorderLayout());
		JLabel jlName = new JLabel(Translator.getString("properties.project.name") + " :");
		JLabel jlFps = new JLabel(Translator.getString("properties.project.fps") + " :");
		JLabel jlColorChooser = new JLabel(Translator.getString("properties.project.background_color") + " :");

		this.jtfColorChooser.setPreferredSize(new Dimension(45, 23));
		this.jtfColorChooser.setEditable(false);

		JPanel container = new JPanel();
		container.setLayout(new GridBagLayout());

		GridBagConstraints gbc = new GridBagConstraints();

		/* Name */
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(10, 10, 0, 0);
		container.add(jlName, gbc);

		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.gridheight = 1;
		gbc.weightx = 1;
		gbc.weighty = 0;
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(5, 5, 0, 10);
		container.add(this.jtfProjectName, gbc);

		/* Color */
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(5, 10, 0, 0);
		container.add(jlColorChooser, gbc);

		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.gridheight = 1;
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(5, 5, 0, 10);
		container.add(this.jtfColorChooser, gbc);

		/* FPS */
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.gridheight = 1;
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(5, 10, 0, 0);
		container.add(jlFps, gbc);

		gbc.gridx = 1;
		gbc.gridy = 3;
		gbc.weightx = 1;
		gbc.weighty = 0;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.gridheight = GridBagConstraints.REMAINDER;
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(5, 5, 0, 10);
		container.add(this.jtfFps, gbc);

		this.add(container, BorderLayout.NORTH);

		this.jtfProjectName.getDocument().addDocumentListener(new DocumentAdapter() {
			@Override
			public void performUpdate(DocumentEvent e) {
				ProjectProperties.this.project.setName(jtfProjectName.getText());
			}
		});

		this.jtfFps.getDocument().addDocumentListener(new DocumentAdapter() {
			@Override
			public void performUpdate(DocumentEvent e) {
				try {
					ProjectProperties.this.project.getScenario().setFps(Integer.parseInt(jtfFps.getText()));
				} catch (NumberFormatException ex) {

				}
			}
		});

		this.jtfColorChooser.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Color oldColor = ProjectProperties.this.project.getBackgroundColor();
				Color color = JColorChooser.showDialog(ProjectProperties.this, Translator.getString("choose_color"), oldColor);
				if (color != null) {
					ProjectProperties.this.project.setBackgroundColor(color);
					ProjectProperties.this.jtfColorChooser.setBackground(color);
				}

			}
		});
	}

	public void setProject(Project p) {
		this.project = p;

		this.jtfProjectName.setText(this.project.getName());
		this.jtfColorChooser.setBackground(this.project.getBackgroundColor());
		this.jtfFps.setText(this.project.getScenario().getFps() + "");
	}
}
