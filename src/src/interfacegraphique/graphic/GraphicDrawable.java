package interfacegraphique.graphic;

import interfacegraphique.drawing.Drawable;

import java.awt.Color;
import java.awt.Graphics;

public abstract class GraphicDrawable {

	protected Drawable drawable;

	public GraphicDrawable(Drawable d) {
		this.drawable = d;
	}

	public Drawable getDrawable() {
		return this.drawable;
	}

	protected static Color alphaColor(Color c, double opacity) {
		return new Color(c.getRed(), c.getGreen(), c.getBlue(), (int) (c.getAlpha() * opacity));
	}

	public abstract void paintComponent(Graphics g);
}