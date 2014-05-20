package interfacegraphique.graphic;

import interfacegraphique.movie.Scenario;
import interfacegraphique.tools.Translator;

import java.util.Observable;
import java.util.Observer;

import javax.swing.table.AbstractTableModel;

class LayerTableModel extends AbstractTableModel implements Observer {

	protected Scenario scenario;

	public LayerTableModel(Scenario scenario) {
		this.setScenario(scenario);
	}

	@Override
	public int getRowCount() {
		return this.scenario.getLayers().size();
	}

	@Override
	public int getColumnCount() {
		return 1;
	}

	@Override
	public void setValueAt(Object value, int row, int col) {
		this.scenario.getLayers().get(row).setName((String) value);
	}

	@Override
	public String getColumnName(int col) {
		return Translator.getString("scenario.layers");
	}

	@Override
	public Class getColumnClass(int c) {
		return String.class;
	}

	@Override
	public boolean isCellEditable(int row, int col) {
		return true;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		return this.scenario.getLayers().get(rowIndex).getName();
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
