package interfacegraphique.graphic;

import interfacegraphique.drawing.Drawable;
import interfacegraphique.drawing.DrawableGroup;
import interfacegraphique.drawing.shape.Oval;
import interfacegraphique.drawing.shape.Polygon;
import interfacegraphique.drawing.shape.Shape;

public class GraphicDrawableFactory {

	public static GraphicDrawable create(Drawable d) throws Exception {
		GraphicDrawable gd = null;

		if (d instanceof Oval) {
			gd = new GraphicOval((Oval) d);
		} else if (d instanceof Polygon) {
			gd = new GraphicPolygon((Polygon) d);
		} else if (d instanceof Shape) {
			gd = new GraphicShape(d);
		} else if (d instanceof DrawableGroup) {
			gd = new GraphicDrawableGroup(d);
		}

		if (gd == null) {
			throw new Exception("Class \"" + d.getClass().getSimpleName() + "\" : Not yet available !");
		}

		return gd;
	}

}
