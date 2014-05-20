package interfacegraphique.graphic;

import interfacegraphique.drawing.Drawable;
import interfacegraphique.movie.Library;
import interfacegraphique.movie.Project;
import interfacegraphique.tools.Translator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

public class GraphicLibrary extends JTable {

	private Project project;
	private Library library;

	public GraphicLibrary(Project project) {
		super();

		this.initComponents();
		this.setProject(project);
	}

	public void initComponents() {
		this.setModel(new DrawableTableModel(this.library));
		this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int cellHeight = GraphicLibrary.this.getRowHeight();
				final int row = (e.getY() / cellHeight);

				if (row < GraphicLibrary.this.library.size()) {
					Drawable selected = GraphicLibrary.this.library.get(row);
					GraphicLibrary.this.project.getScenario().select(selected);
				}

				if (e.getButton() == MouseEvent.BUTTON3) {
					JPopupMenu popup = new JPopupMenu();

					if (row < GraphicLibrary.this.library.size()) {
						JMenuItem menuItem = new JMenuItem(Translator.getString("library.delete_drawable"));
						menuItem.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								GraphicLibrary.this.library.remove(row);
							}
						});

						popup.add(menuItem);
					}

					popup.show(GraphicLibrary.this, e.getX(), e.getY());
				}
			}
		});
	}

	public void setProject(Project project) {
		this.project = project;
		this.library = project.getLibrary();

		((DrawableTableModel) this.getModel()).setLibrary(this.library);
	}

}
