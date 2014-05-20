package interfacegraphique.graphic;

import interfacegraphique.movie.KeyFrame;
import interfacegraphique.movie.Scenario;

import java.awt.Color;
import java.awt.Component;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.border.Border;
import javax.swing.table.TableCellRenderer;

public class KeyFrameCellRenderer extends JLabel implements TableCellRenderer {

	public static final Color KF_UNSELECTED = Color.GRAY;
	public static final Color KF_ANIMATED = Color.BLACK;
	public static final Color KF_EMPTY = Color.LIGHT_GRAY;

	private static final Border B_SELECTED = BorderFactory.createMatteBorder(2, 2, 2, 2, Color.RED);
	private static final Border B_UNSELECTED = BorderFactory.createMatteBorder(2, 2, 2, 2, Color.ORANGE);

	protected Border border;
	protected Scenario scenario;

	public KeyFrameCellRenderer(Scenario s) {
		this.setScenario(s);
		this.setOpaque(true);
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object keyFrame, boolean isSelected, boolean hasFocus, int row, int col) {
		Color c = null;
		KeyFrame kf = (KeyFrame) keyFrame;

		if (kf != null) {
			if (kf.getAnimation() != null) {
				c = KF_ANIMATED;
			} else {
				c = KF_UNSELECTED;
			}
		} else {
			c = KF_EMPTY;
		}

		this.setBackground(c);

		if (col == this.scenario.getCurrentTime()) {
			if (row == this.scenario.getCurrentLayerIndex()) {
				this.setBorder(B_SELECTED);
			} else {
				this.setBorder(B_UNSELECTED);
			}
		} else {
			this.setBorder(null);
		}

		return this;
	}

	public void setScenario(Scenario s) {
		this.scenario = s;
	}
}
