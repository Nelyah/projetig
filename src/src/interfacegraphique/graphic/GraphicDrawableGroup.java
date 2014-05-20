package interfacegraphique.graphic;

import interfacegraphique.drawing.Drawable;
import interfacegraphique.drawing.DrawableGroup;

import java.awt.Graphics;

public class GraphicDrawableGroup extends GraphicDrawable {

	public GraphicDrawableGroup(Drawable d) {
		super(d);
	}

	@Override
	public void paintComponent(Graphics g) {
		for (Drawable d : ((DrawableGroup) this.drawable).getDrawables()) {
			try {
				GraphicDrawableFactory.create(d).paintComponent(g);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
