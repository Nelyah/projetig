package interfacegraphique.movie.animation;

public class EaseInOutCubicAnimation extends Animation {
	@Override
	public double getValue(double t, double b, double c, double d) {
		double tt = t / (d / 2);
		if (tt < 1) {
			return ((c - b) / 2) * Math.pow(tt, 3) + b;
		} else {
			tt -= 2;
			return ((c - b) / 2) * (Math.pow(tt, 3) + 2) + b;
		}

	}
}
