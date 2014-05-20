package interfacegraphique.graphic;

import interfacegraphique.movie.Library;
import interfacegraphique.tools.Translator;

import java.util.Observable;
import java.util.Observer;

import javax.swing.table.AbstractTableModel;

class DrawableTableModel extends AbstractTableModel implements Observer {

	protected Library library;

	public DrawableTableModel(Library library) {
		this.setLibrary(library);
	}

	public void setLibrary(Library l) {
		if (this.library != null) {
			this.library.deleteObserver(this);
		}

		this.library = l;
		if (l != null) {
			this.library.addObserver(this);
		}
	}

	@Override
	public int getRowCount() {
		return this.library.size();
	}

	@Override
	public int getColumnCount() {
		return 1;
	}

	@Override
	public void setValueAt(Object value, int row, int col) {
		this.library.getElements().get(row).setName((String) value);
	}

	@Override
	public String getColumnName(int col) {
		return Translator.getString("library");
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
		return this.library.getElements().get(rowIndex).getName();
	}

	@Override
	public void update(Observable o, Object arg) {
		this.fireTableDataChanged();
	}
}
