package interfacegraphique.graphic;

import interfacegraphique.movie.Scenario;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

public class LayerCellRenderer extends JLabel implements TableCellRenderer {

	protected Scenario scenario;

	public LayerCellRenderer(Scenario s) {
		this.setScenario(s);
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object layer, boolean isSelected, boolean hasFocus, int row, int col) {
		if (layer != null) {
			if (row == this.scenario.getCurrentLayerIndex()) {
				this.setOpaque(true);
				this.setBackground(Color.LIGHT_GRAY);

			} else {
				this.setOpaque(false);
			}

			this.setText((String) layer);
		}

		return this;
	}

	public void setScenario(Scenario s) {
		this.scenario = s;
	}
}
