package interfacegraphique.graphic;

import interfacegraphique.drawing.Drawable;
import interfacegraphique.drawing.shape.Polygon;
import interfacegraphique.drawing.shape.PredefinedPolygon;
import interfacegraphique.drawing.shape.Rectangle;
import interfacegraphique.drawing.shape.Shape;
import interfacegraphique.movie.Scenario;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

public class Stage extends JPanel implements Observer {
	protected Scenario scenario;

	public Stage(Scenario s) {
		super();

		this.setScenario(s);
	}

	public void drawEnd() {
		this.selectStart();
	}

	private void drawPolygon(final Polygon p) {
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				p.movePoint(p.getPoints().size() - 1, e.getPoint());
			}

			@Override
			public void mousePressed(MouseEvent e) {
				if (p.getPoints().size() == 0) {
					p.addPoint(e.getPoint());
				}

				p.addPoint(e.getPoint());
				Stage.this.addMouseMotionListener(this);
			}
		});
	}

	private void drawPredefinedPolygon(final PredefinedPolygon p) {
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				p.relativeResize(e.getX() - p.getOrigin().x, e.getY() - p.getOrigin().y);
			}

			@Override
			public void mousePressed(MouseEvent e) {
				p.setOrigin(e.getPoint());
				Stage.this.addMouseMotionListener(this);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				Stage.this.drawEnd();
				Stage.this.scenario.select(p);
			}
		});
	}

	private void drawShape(final Shape s) {
		this.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDragged(MouseEvent e) {
				s.addPoint(e.getPoint());
			}

			@Override
			public void mousePressed(MouseEvent e) {
				s.addPoint(e.getPoint());
				Stage.this.addMouseMotionListener(this);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				Stage.this.drawEnd();
				Stage.this.scenario.select(s);
			}
		});
	}

	public void drawStart(final Drawable d) {
		this.removeEventListeners();

		if (d instanceof PredefinedPolygon) {
			this.drawPredefinedPolygon((PredefinedPolygon) d);
		} else if (d instanceof Polygon) {
			this.drawPolygon((Polygon) d);
		} else if (d instanceof Shape) {
			this.drawShape((Shape) d);
		}

		this.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_ENTER:
					Stage.this.drawEnd();
					Stage.this.scenario.select(d);
					break;
				}
			}
		});
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		for (Drawable d : this.scenario.getState()) {
			try {
				this.paintDrawable(g, d);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		Drawable selected = this.scenario.getSelectedDrawable();

		if (selected != null) {
			try {
				Rectangle wrapper = selected.getWrapper();
				wrapper.setColor(Color.CYAN);
				this.paintDrawable(g, wrapper);

				ArrayList<Point> points = wrapper.getPoints();
				points.add(selected.getCenter());

				for (Point p : points) {
					Rectangle rc = new Rectangle(p, 4, 4);
					rc.translate(-2, -2);
					rc.setColor(Color.RED);
					this.paintDrawable(g, rc);
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void paintDrawable(Graphics g, Drawable d) throws Exception {
		Graphics2D g2 = (Graphics2D) g;
		AffineTransform transform = g2.getTransform();

		Point origin = d.getOrigin();
		Point center = d.getCenter();
		double angle = Math.toRadians(d.getAngle());

		int dx = center.x - origin.x;
		int dy = center.y - origin.y;

		g2.translate(origin.x, origin.y);
		g2.rotate(angle, dx, dy);

		GraphicDrawableFactory.create(d).paintComponent(g);
		g2.setTransform(transform);
	}

	public void setScenario(Scenario s) {
		if (this.scenario != null) {
			this.scenario.deleteObserver(this);
		}

		this.scenario = s;
		this.scenario.addObserver(this);
		this.removeEventListeners();

		this.repaint();
	}

	public void removeEventListeners() {
		for (MouseMotionListener mml : this.getMouseMotionListeners()) {
			this.removeMouseMotionListener(mml);
		}

		for (MouseListener ml : this.getMouseListeners()) {
			this.removeMouseListener(ml);
		}

		for (KeyListener kl : this.getKeyListeners()) {
			this.removeKeyListener(kl);
		}
	}

	public void selectEnd() {
		this.removeEventListeners();
	}

	public void selectStart() {
		this.removeEventListeners();
		this.addMouseListener(new MouseAdapter() {
			Point last = null;
			Drawable selected = null;

			@Override
			public void mouseDragged(MouseEvent e) {
				if (this.selected != null) {
					Point p = e.getPoint();
					int dx = p.x - this.last.x;
					int dy = p.y - this.last.y;

					this.last = p;
					this.selected.translate(dx, dy);
				}
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON3) {
					if (this.selected != null) {
						JPopupMenu popup = new JPopupMenu();
						JMenuItem menuItem = new JMenuItem("Remove");
						menuItem.addActionListener(new ActionListener() {
							@Override
							public void actionPerformed(ActionEvent e) {
								Stage.this.scenario.getCurrentKeyFrame().remove(selected);
								Stage.this.scenario.select(null);
								selected = null;
							}
						});

						popup.add(menuItem);
						popup.show(Stage.this, e.getX(), e.getY());
					}
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {
				this.last = e.getPoint();
				this.selected = Stage.this.scenario.selectOn(this.last);

				Stage.this.removeMouseMotionListener(this);
				if (this.selected != null) {
					Stage.this.addMouseMotionListener(this);
				}
			}
		});
	}

	@Override
	public void update(Observable o, Object arg) {
		this.repaint();
	}
}
