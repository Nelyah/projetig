package interfacegraphique.graphic;

import interfacegraphique.movie.KeyFrame;
import interfacegraphique.movie.Layer;
import interfacegraphique.movie.Scenario;
import interfacegraphique.tools.Translator;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Enumeration;

import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

public class GraphicScenario extends JPanel {

	protected JTable keyFramesTable;
	protected JTable layersTable;
	protected Player player;
	protected Scenario scenario;

	public GraphicScenario(Scenario s) {
		super();

		this.scenario = s;
		this.layersTable = new JTable();
		this.keyFramesTable = new JTable();
		this.player = new Player(s);

		this.initComponents();
		this.setScenario(s);
	}

	public void setScenario(Scenario s) {
		this.scenario = s;
		this.player.setScenario(s);

		((KeyFramesTableModel) this.keyFramesTable.getModel()).setScenario(s);
		((KeyFrameCellRenderer) this.keyFramesTable.getDefaultRenderer(KeyFrame.class)).setScenario(s);

		((LayerTableModel) this.layersTable.getModel()).setScenario(s);
		((LayerCellRenderer) this.layersTable.getDefaultRenderer(String.class)).setScenario(s);

		this.refreshKeyFramesTableColumns();
		this.repaint();
	}

	public Scenario getScenario() {
		return this.scenario;
	}

	private void initComponents() {
		this.initKeyFramesTable();
		this.initLayerTable();

		JPanel jpKeyFrameContainer = new JPanel();
		jpKeyFrameContainer.setLayout(new BorderLayout());
		jpKeyFrameContainer.add(this.keyFramesTable.getTableHeader(), BorderLayout.NORTH);
		jpKeyFrameContainer.add(this.keyFramesTable, BorderLayout.CENTER);

		JPanel jpLayerContainer = new JPanel();
		jpLayerContainer.setLayout(new BorderLayout());
		jpLayerContainer.add(this.layersTable.getTableHeader(), BorderLayout.NORTH);
		jpLayerContainer.add(this.layersTable, BorderLayout.CENTER);

		JScrollPane jScrollLayers = new JScrollPane(jpLayerContainer);
		JScrollPane jScrollKeyFrames = new JScrollPane(jpKeyFrameContainer);

		JSplitPane jSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, jScrollLayers, jScrollKeyFrames);
		jSplit.setResizeWeight(0.2);

		this.setLayout(new BorderLayout());
		this.add(jSplit, BorderLayout.CENTER);
		this.add(this.player, BorderLayout.NORTH);
	}

	private void initLayerTable() {
		this.layersTable.setModel(new LayerTableModel(this.scenario));
		this.layersTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.layersTable.setDefaultRenderer(String.class, new LayerCellRenderer(this.scenario));
		this.layersTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int cellHeight = GraphicScenario.this.layersTable.getRowHeight();
				final int row = (e.getY() / cellHeight);
				Scenario scenario = GraphicScenario.this.scenario;

				if (row < scenario.getLayers().size()) {
					scenario.selectLayer(row);
				}

				if (e.getButton() == MouseEvent.BUTTON3) {
					JPopupMenu popup = new JPopupMenu();

					JMenuItem menuItem = new JMenuItem(Translator.getString("scenario.insert_layer"));
					menuItem.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							GraphicScenario.this.scenario.add(new Layer());
						}
					});

					popup.add(menuItem);

					menuItem = new JMenuItem(Translator.getString("scenario.remove_layer"));
					menuItem.setEnabled(row < scenario.getLayers().size());
					menuItem.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							GraphicScenario.this.removeLayer(row);
						}
					});

					popup.add(menuItem);

					popup.show(GraphicScenario.this.layersTable, e.getX(), e.getY());
				}
			}
		});
	}

	private void removeLayer(int index) {
		if (this.scenario.getLayers().size() > 1) {
			try {
				this.scenario.remove(index);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			JOptionPane.showMessageDialog(null, Translator.getString("scenario.last_layer"), Translator.getString("message.error"), JOptionPane.ERROR_MESSAGE);
		}
	}

	private void initKeyFramesTable() {
		JTableHeader header = this.keyFramesTable.getTableHeader();
		header.setResizingAllowed(false);
		header.setReorderingAllowed(false);
		header.setFont(header.getFont().deriveFont(7.0f));

		this.keyFramesTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		this.keyFramesTable.setModel(new KeyFramesTableModel(this.scenario));
		this.keyFramesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.keyFramesTable.setDefaultRenderer(KeyFrame.class, new KeyFrameCellRenderer(this.scenario));
		this.keyFramesTable.getModel().addTableModelListener(new TableModelListener() {
			@Override
			public void tableChanged(TableModelEvent e) {
				GraphicScenario.this.refreshKeyFramesTableColumns();
			}
		});

		this.keyFramesTable.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				TableColumnModel tableModel = GraphicScenario.this.keyFramesTable.getColumnModel();
				int cellWidth = tableModel.getColumn(0).getWidth();
				int cellHeight = GraphicScenario.this.keyFramesTable.getRowHeight();

				final int col = (e.getX() / cellWidth);
				final int row = (e.getY() / cellHeight);

				GraphicScenario.this.scenario.selectLayer(row);
				GraphicScenario.this.scenario.setCurrentTime(col);

				if (e.getButton() == MouseEvent.BUTTON1) {
					if (e.getClickCount() == 2) {
						GraphicScenario.this.scenario.getCurrentLayer().copyAt(col);
					}

				} else if (e.getButton() == MouseEvent.BUTTON3) {
					JPopupMenu popup = new JPopupMenu();
					JMenuItem menuItem = new JMenuItem(Translator.getString("scenario.insert_keyframe"));
					menuItem.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							GraphicScenario.this.scenario.getCurrentLayer().copyAt(col);
						}
					});
					popup.add(menuItem);

					menuItem = new JMenuItem(Translator.getString("scenario.remove_keyframe"));
					menuItem.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							GraphicScenario.this.scenario.getCurrentLayer().remove(col);
						}
					});
					popup.add(menuItem);

					popup.add(new JSeparator());

					menuItem = new JMenuItem(Translator.getString("scenario.insert_layer"));
					menuItem.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							GraphicScenario.this.scenario.add(new Layer());
						}
					});
					popup.add(menuItem);

					menuItem = new JMenuItem(Translator.getString("scenario.remove_layer"));
					menuItem.setEnabled(row < scenario.getLayers().size());
					menuItem.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							GraphicScenario.this.removeLayer(row);
						}
					});

					popup.add(menuItem);

					popup.show(GraphicScenario.this.keyFramesTable, e.getX(), e.getY());
				}
			}
		});

		this.keyFramesTable.addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				TableColumnModel tableModel = GraphicScenario.this.keyFramesTable.getColumnModel();
				int cellWidth = tableModel.getColumn(0).getWidth();
				int col = (e.getX() / cellWidth);

				GraphicScenario.this.scenario.setCurrentTime(col);
			}
		});
	}

	private void refreshKeyFramesTableColumns() {
		int width = 15;

		Enumeration<TableColumn> columns = this.keyFramesTable.getColumnModel().getColumns();
		while (columns.hasMoreElements()) {
			TableColumn tc = columns.nextElement();
			tc.setMaxWidth(width);
		}
	}
}
