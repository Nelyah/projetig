package interfacegraphique.movie.animation;

public class EaseInOutExponentialAnimation extends Animation {
	@Override
	public double getValue(double t, double b, double c, double d) {
		double tt = t / (d / 2);
		if (tt < 1) {
			return ((c - b) / 2) * Math.pow(2, 10 * (tt - 1)) + b;
		} else {
			tt--;
			return ((c - b) / 2) * (-Math.pow(2, -10 * tt) + 2) + b;
		}
	}
}
