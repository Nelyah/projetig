package interfacegraphique.graphic;

import interfacegraphique.movie.KeyFrame;
import interfacegraphique.movie.Scenario;

import java.util.Observable;
import java.util.Observer;

import javax.swing.table.AbstractTableModel;

class KeyFramesTableModel extends AbstractTableModel implements Observer {

	public static final int BLANK_CELLS = 300;
	protected Scenario scenario;

	public KeyFramesTableModel(Scenario s) {
		this.setScenario(s);
	}

	@Override
	public int getRowCount() {
		return this.scenario.getLayers().size();
	}

	@Override
	public String getColumnName(int col) {
		String name;

		if (col == 0) {
			name = "0";

		} else if (col % 10 == 0 && col < 10000) {
			if (col % 1000 == 0) {
				name = (col / 1000) + "k";
			} else if (col % 100 == 0) {
				name = (col / 100) + "..";
			} else {
				name = (col % 100) + "";
			}

		} else {
			name = " ";
		}

		return name;
	}

	@Override
	public boolean isCellEditable(int row, int col) {
		return false;
	}

	@Override
	public Class getColumnClass(int c) {
		return KeyFrame.class;
	}

	@Override
	public int getColumnCount() {
		return this.scenario.getCountMaxKeyFrames() + BLANK_CELLS;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return this.scenario.getLayers().get(rowIndex).get(columnIndex);
	}

	public void setScenario(Scenario s) {
		if (this.scenario != null) {
			this.scenario.deleteObserver(this);
		}

		this.scenario = s;

		if (s != null) {
			this.scenario.addObserver(this);
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		this.fireTableDataChanged();
	}
}