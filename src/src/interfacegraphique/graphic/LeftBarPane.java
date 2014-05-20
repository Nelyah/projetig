package interfacegraphique.graphic;

import interfacegraphique.Main;
import interfacegraphique.drawing.shape.Circle;
import interfacegraphique.drawing.shape.EquilateralTriangle;
import interfacegraphique.drawing.shape.IsoTriangle;
import interfacegraphique.drawing.shape.Oval;
import interfacegraphique.drawing.shape.Polygon;
import interfacegraphique.drawing.shape.Rectangle;
import interfacegraphique.drawing.shape.RectangleTriangle;
import interfacegraphique.drawing.shape.Shape;
import interfacegraphique.drawing.shape.Square;
import interfacegraphique.drawing.shape.Star;
import interfacegraphique.tools.Tools;

import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;

public class LeftBarPane extends JToolBar {
	private JButton selectBut, handDrawBut, polygonBut, isoTriangleBut,
			eqTriangleBut, rectTriangleBut, rectangleBut, squareBut, ovalBut,
			roundBut, starBut;

	private Main controller;

	public LeftBarPane(Main controller) {
		this.controller = controller;

		try {
			this.squareBut = this.createJButton("resources/square.png");
			this.ovalBut = this.createJButton("resources/oval.png");
			this.roundBut = this.createJButton("resources/circle.png");
			this.rectangleBut = this.createJButton("resources/rectangle.png");
			this.rectTriangleBut = this.createJButton("resources/triangle_rect.png");
			this.isoTriangleBut = this.createJButton("resources/triangle_iso.png");
			this.eqTriangleBut = this.createJButton("resources/triangle_eq.png");
			this.polygonBut = this.createJButton("resources/polygon.png");
			this.handDrawBut = this.createJButton("resources/pencil.png");
			this.selectBut = this.createJButton("resources/cursor.png");
			this.starBut = this.createJButton("resources/star.png");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.setOrientation(SwingConstants.VERTICAL);
		this.initComponents();
	}

	private void initComponents() {
		this.selectBut.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				LeftBarPane.this.controller.selectStart();
			}
		});

		this.handDrawBut.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				LeftBarPane.this.controller.drawStart(new Shape());
			}
		});

		this.polygonBut.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				LeftBarPane.this.controller.drawStart(new Polygon());
			}
		});

		this.isoTriangleBut.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				LeftBarPane.this.controller.drawStart(new IsoTriangle());
			}
		});

		this.rectTriangleBut.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				LeftBarPane.this.controller.drawStart(new RectangleTriangle());
			}
		});

		this.eqTriangleBut.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				LeftBarPane.this.controller.drawStart(new EquilateralTriangle());
			}
		});

		this.rectangleBut.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				LeftBarPane.this.controller.drawStart(new Rectangle());
			}
		});

		this.squareBut.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				LeftBarPane.this.controller.drawStart(new Square());
			}
		});

		this.ovalBut.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				LeftBarPane.this.controller.drawStart(new Oval());
			}
		});

		this.roundBut.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				LeftBarPane.this.controller.drawStart(new Circle());
			}
		});

		this.starBut.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				LeftBarPane.this.controller.drawStart(new Star());
			}
		});

		this.add(this.selectBut);
		this.add(this.handDrawBut);
		this.add(this.polygonBut);
		this.add(this.starBut);
		this.add(this.isoTriangleBut);
		this.add(this.eqTriangleBut);
		this.add(this.rectTriangleBut);
		this.add(this.rectangleBut);
		this.add(this.squareBut);
		this.add(this.ovalBut);
		this.add(this.roundBut);
	}

	private JButton createJButton(String iconPathname) throws IOException {
		JButton jb = new JButton();
		jb.setIcon(Tools.getIcon(iconPathname));
		jb.setMargin(new Insets(0, 0, 0, 0));
		jb.setMinimumSize(new Dimension(32, 32));

		return jb;
	}

}
