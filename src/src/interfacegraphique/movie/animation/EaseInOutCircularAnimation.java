package interfacegraphique.movie.animation;

public class EaseInOutCircularAnimation extends Animation {
	@Override
	public double getValue(double t, double b, double c, double d) {
		double tt = t / (d / 2);
		if (tt < 1) {
			return (-(c - b) / 2) * (Math.sqrt(1 - tt * tt) - 1) + b;
		} else {
			tt -= 2;
			return ((c - b) / 2) * (Math.sqrt(1 - tt * tt) + 1) + b;
		}
	}
}
