package interfacegraphique.graphic;

import interfacegraphique.drawing.Drawable;
import interfacegraphique.drawing.shape.Polygon;
import interfacegraphique.drawing.shape.PredefinedPolygon;
import interfacegraphique.drawing.shape.Shape;
import interfacegraphique.drawing.shape.Symmetric;
import interfacegraphique.tools.DocumentAdapter;
import interfacegraphique.tools.Translator;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;

public class DrawableProperties extends JPanel implements Observer {

	protected Drawable drawable;

	protected JTextField jtfName;
	protected JLabel jlName;
	protected JTextField jtfAngle;
	protected JLabel jlAngle;
	protected JTextField jtfOpacity;
	protected JLabel jlOpacity;
	protected JTextField jtfX;
	protected JLabel jlX;
	protected JTextField jtfY;
	protected JLabel jlY;
	protected JTextField jtfW;
	protected JLabel jlW;
	protected JTextField jtfH;
	protected JLabel jlH;
	protected JTextField jtfStrokeW;
	protected JLabel jlStrokeW;
	protected JTextField jtfBgColor;
	protected JLabel jlBgColor;
	protected JTextField jtfColor;
	protected JLabel jlColor;

	protected Color bgColor;
	protected Color color;
	protected int strokeWidth;

	public DrawableProperties() {
		super();

		this.color = Color.BLACK;
		this.bgColor = null;
		this.strokeWidth = 1;

		this.initComponents();
	}

	public int getSelectedStrokeWidth() {
		return this.strokeWidth;
	}

	public Color getSelectedBackgroundColor() {
		return this.bgColor;
	}

	public Color getSelectedColor() {
		return this.color;
	}

	public void setDrawable(Drawable d) {
		if (this.drawable != d) {
			if (this.drawable != null) {
				this.drawable.deleteObserver(this);
			}

			this.drawable = d;

			if (d != null) {
				this.drawable.addObserver(this);

				boolean b = (d instanceof Shape);
				this.jtfColor.setEnabled(b);
				this.jlColor.setEnabled(b);
				this.jtfStrokeW.setEnabled(b);
				this.jlStrokeW.setEnabled(b);

				b = (d instanceof Polygon);
				this.jtfBgColor.setEnabled(b);
				this.jlBgColor.setEnabled(b);

				b = (d instanceof PredefinedPolygon && (d instanceof Symmetric) == false);
				this.jtfH.setEnabled(b);
				this.jlH.setEnabled(b);

				b = (d instanceof PredefinedPolygon);
				this.jtfW.setEnabled(b);
				this.jlW.setEnabled(b);

				this.update(this.drawable, null);
			}
		}
	}

	public void setColor(Color c) {
		this.color = c;

		if (this.drawable instanceof Shape) {
			((Shape) (this.drawable)).setColor(c);
		}
	}

	public void setBackgroundColor(Color c) {
		this.bgColor = c;

		if (this.drawable instanceof Polygon) {
			((Polygon) (this.drawable)).setBackgroundColor(c);
		}
	}

	public void setStrokeWidth(int sw) {
		this.strokeWidth = sw;

		if (this.drawable instanceof Shape) {
			((Shape) this.drawable).setStrokeWidth(sw);
		}
	}

	private void initComponents() {
		this.jtfX = new JTextField();
		this.jtfY = new JTextField();
		this.jtfW = new JTextField();
		this.jtfH = new JTextField();
		this.jtfOpacity = new JTextField();
		this.jtfAngle = new JTextField();
		this.jtfStrokeW = new JTextField();
		this.jtfName = new JTextField();
		this.jtfColor = new JTextField();
		this.jtfBgColor = new JTextField();

		this.jtfColor.setEditable(false);
		this.jtfBgColor.setEditable(false);

		this.jlX = new JLabel(Translator.getString("drawProp.jlX") + " :");
		this.jlY = new JLabel(Translator.getString("drawProp.jlY") + " :");
		this.jlW = new JLabel(Translator.getString("drawProp.jlW") + " :");
		this.jlH = new JLabel(Translator.getString("drawProp.jlH") + " :");
		this.jlOpacity = new JLabel(Translator.getString("drawProp.jlOpacity") + " :");
		this.jlAngle = new JLabel(Translator.getString("drawProp.jlAngle") + " :");
		this.jlStrokeW = new JLabel(Translator.getString("drawProp.jlStrokeW") + " :");
		this.jlName = new JLabel(Translator.getString("drawProp.jlName") + " :");
		this.jlColor = new JLabel(Translator.getString("drawProp.color") + " :");
		this.jlBgColor = new JLabel(Translator.getString("drawProp.bg_color") + " :");

		this.setLayout(new BorderLayout());

		JPanel container = new JPanel();
		container.setLayout(new GridBagLayout());

		GridBagConstraints gbc = new GridBagConstraints();

		/* X */
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(10, 10, 0, 0);
		container.add(this.jlX, gbc);

		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1;
		gbc.weighty = 0;
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(5, 5, 0, 10);
		container.add(this.jtfX, gbc);

		/* Y */
		gbc.gridx = 2;
		gbc.gridy = 0;
		gbc.gridwidth = GridBagConstraints.RELATIVE;
		gbc.gridheight = 1;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(10, 10, 0, 0);
		container.add(this.jlY, gbc);

		gbc.gridx = 3;
		gbc.gridy = 0;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.gridheight = 1;
		gbc.weightx = 1;
		gbc.weighty = 0;
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(5, 5, 0, 10);
		container.add(this.jtfY, gbc);

		/* W */
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(10, 10, 0, 0);
		container.add(this.jlW, gbc);

		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1;
		gbc.weighty = 0;
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(5, 5, 0, 10);
		container.add(this.jtfW, gbc);

		/* H */
		gbc.gridx = 2;
		gbc.gridy = 1;
		gbc.gridwidth = GridBagConstraints.RELATIVE;
		gbc.gridheight = 1;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(10, 10, 0, 0);
		container.add(this.jlH, gbc);

		gbc.gridx = 3;
		gbc.gridy = 1;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.gridheight = 1;
		gbc.weightx = 1;
		gbc.weighty = 0;
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(5, 5, 0, 10);
		container.add(this.jtfH, gbc);

		/* Opacity */
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(10, 10, 0, 0);
		container.add(this.jlOpacity, gbc);

		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1;
		gbc.weighty = 0;
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(5, 5, 0, 10);
		container.add(this.jtfOpacity, gbc);

		/* Angle */
		gbc.gridx = 2;
		gbc.gridy = 2;
		gbc.gridwidth = GridBagConstraints.RELATIVE;
		gbc.gridheight = 1;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(10, 10, 0, 0);
		container.add(this.jlAngle, gbc);

		gbc.gridx = 3;
		gbc.gridy = 2;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.gridheight = 1;
		gbc.weightx = 1;
		gbc.weighty = 0;
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(5, 5, 0, 10);
		container.add(this.jtfAngle, gbc);

		/* StrokeWidth */
		gbc.gridx = 0;
		gbc.gridy = 3;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(10, 10, 0, 0);
		container.add(this.jlStrokeW, gbc);

		gbc.gridx = 1;
		gbc.gridy = 3;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.anchor = GridBagConstraints.BASELINE_LEADING;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(5, 5, 0, 10);
		container.add(this.jtfStrokeW, gbc);

		/* Color */
		gbc.gridx = 0;
		gbc.gridy = 4;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(10, 10, 0, 0);
		container.add(this.jlColor, gbc);

		gbc.gridx = 1;
		gbc.gridy = 4;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;
		gbc.weightx = 1;
		gbc.weighty = 0;
		gbc.anchor = GridBagConstraints.BASELINE_LEADING;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(5, 5, 0, 10);
		container.add(this.jtfColor, gbc);

		/* BG Color */
		gbc.gridx = 2;
		gbc.gridy = 4;
		gbc.gridwidth = GridBagConstraints.RELATIVE;
		gbc.gridheight = 1;
		gbc.weightx = 0;
		gbc.weighty = 0;
		gbc.anchor = GridBagConstraints.LINE_START;
		gbc.fill = GridBagConstraints.NONE;
		gbc.insets = new Insets(10, 10, 0, 0);
		container.add(this.jlBgColor, gbc);

		gbc.gridx = 3;
		gbc.gridy = 4;
		gbc.gridwidth = GridBagConstraints.REMAINDER;
		gbc.gridheight = 1;
		gbc.weightx = 1;
		gbc.weighty = 0;
		gbc.anchor = GridBagConstraints.BASELINE_LEADING;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.insets = new Insets(5, 5, 0, 10);
		container.add(this.jtfBgColor, gbc);

		this.add(container, BorderLayout.NORTH);

		this.jtfName.getDocument().addDocumentListener(new DocumentAdapter() {
			@Override
			public void performUpdate(DocumentEvent e) {
				Drawable d = DrawableProperties.this.drawable;
				d.setName(jtfName.getText());
			}
		});

		this.jtfAngle.getDocument().addDocumentListener(new DocumentAdapter() {
			@Override
			public void performUpdate(DocumentEvent e) {
				Drawable d = DrawableProperties.this.drawable;

				if (jtfAngle.getText().isEmpty() == false) {
					try {
						d.setAngle(Double.parseDouble(jtfAngle.getText()));
						jtfAngle.setForeground(Color.BLACK);
					} catch (NumberFormatException ex) {
						jtfAngle.setForeground(Color.RED);
					}
				}
			}
		});

		this.jtfX.getDocument().addDocumentListener(new DocumentAdapter() {
			@Override
			public void performUpdate(DocumentEvent e) {
				Drawable d = DrawableProperties.this.drawable;

				if (jtfX.getText().isEmpty() == false) {
					try {
						d.setOrigin(new Point(Integer.parseInt(jtfX.getText()), d.getOrigin().y));
						jtfX.setForeground(Color.BLACK);
					} catch (NumberFormatException ex) {
						jtfX.setForeground(Color.RED);
					}
				}
			}
		});

		this.jtfY.getDocument().addDocumentListener(new DocumentAdapter() {
			@Override
			public void performUpdate(DocumentEvent e) {
				Drawable d = DrawableProperties.this.drawable;

				if (jtfY.getText().isEmpty() == false) {
					try {
						d.setOrigin(new Point(d.getOrigin().x, Integer.parseInt(jtfY.getText())));
						jtfY.setForeground(Color.BLACK);
					} catch (NumberFormatException ex) {
						jtfY.setForeground(Color.RED);
					}
				}
			}
		});

		this.jtfOpacity.getDocument().addDocumentListener(new DocumentAdapter() {
			@Override
			public void performUpdate(DocumentEvent e) {
				Drawable d = DrawableProperties.this.drawable;

				if (jtfOpacity.getText().isEmpty() == false) {
					try {
						double value = Double.parseDouble(jtfOpacity.getText());
						jtfOpacity.setForeground(Color.BLACK);
						if (value >= 0 && value <= 1) {
							d.setOpacity(Double.parseDouble(jtfOpacity.getText()));
						} else {
							jtfOpacity.setForeground(Color.RED);
						}
					} catch (NumberFormatException ex) {
						jtfOpacity.setForeground(Color.RED);
					}
				}
			}
		});

		this.jtfW.getDocument().addDocumentListener(new DocumentAdapter() {
			@Override
			public void performUpdate(DocumentEvent e) {
				Drawable d = DrawableProperties.this.drawable;

				if (jtfW.getText().isEmpty() == false) {
					try {
						int value = Integer.parseInt(jtfW.getText());
						jtfW.setForeground(Color.BLACK);
						if (value > 0) {
							d.resize(value, d.getHeight());
						} else {
							jtfW.setForeground(Color.RED);
						}
					} catch (NumberFormatException ex) {
						jtfW.setForeground(Color.RED);
					}
				}
			}
		});

		this.jtfH.getDocument().addDocumentListener(new DocumentAdapter() {
			@Override
			public void performUpdate(DocumentEvent e) {
				Drawable d = DrawableProperties.this.drawable;

				if (jtfH.getText().isEmpty() == false) {
					try {
						int value = Integer.parseInt(jtfH.getText());
						jtfH.setForeground(Color.BLACK);
						if (value > 0) {
							d.resize(d.getWidth(), value);
						} else {
							jtfH.setForeground(Color.RED);
						}

					} catch (NumberFormatException ex) {
						jtfH.setForeground(Color.RED);
					}
				}
			}
		});

		this.jtfStrokeW.getDocument().addDocumentListener(new DocumentAdapter() {
			@Override
			public void performUpdate(DocumentEvent e) {
				Drawable d = DrawableProperties.this.drawable;

				if (d instanceof Shape) {
					if (jtfStrokeW.getText().isEmpty() == false) {
						try {
							int value = Integer.parseInt(jtfStrokeW.getText());
							jtfStrokeW.setForeground(Color.BLACK);
							if (value >= 0) {
								((Shape) d).setStrokeWidth(value);
							} else {
								jtfStrokeW.setForeground(Color.RED);
							}
						} catch (NumberFormatException ex) {
							jtfStrokeW.setForeground(Color.RED);
						}
					}
				}
			}
		});

		this.jtfColor.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (DrawableProperties.this.drawable instanceof Shape) {
					Shape s = (Shape) DrawableProperties.this.drawable;
					Color color = JColorChooser.showDialog(DrawableProperties.this, Translator.getString("choose_color"), s.getColor());
					if (color != null) {
						s.setColor(color);
					}
				}
			}
		});

		this.jtfBgColor.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (DrawableProperties.this.drawable instanceof Polygon) {
					Polygon p = (Polygon) DrawableProperties.this.drawable;
					Color color = JColorChooser.showDialog(DrawableProperties.this, Translator.getString("choose_color"), p.getBackgroundColor());
					if (color != null) {
						p.setBackgroundColor(color);
					}
				}
			}
		});
	}

	@Override
	public void update(Observable o, Object arg) {
		this.drawable.deleteObserver(this);

		try {
			this.jtfAngle.setText(this.drawable.getAngle() + "");
			this.jtfOpacity.setText(this.drawable.getOpacity() + "");
			this.jtfX.setText(this.drawable.getOrigin().x + "");
			this.jtfY.setText(this.drawable.getOrigin().y + "");
			this.jtfW.setText(this.drawable.getWidth() + "");
			this.jtfH.setText(this.drawable.getHeight() + "");
			this.jtfName.setText(this.drawable.getName());

			if (this.drawable instanceof Shape) {
				this.jtfStrokeW.setText(((Shape) this.drawable).getStrokeWidth() + "");
				this.jtfColor.setBackground(((Shape) this.drawable).getColor());

				if (this.drawable instanceof Polygon) {
					this.jtfBgColor.setBackground(((Polygon) this.drawable).getBackgroundColor());
				}
			}
		} catch (java.lang.IllegalStateException e) {

		}

		this.drawable.addObserver(this);
	}

	public Drawable getDrawable() {
		return this.drawable;
	}

}
