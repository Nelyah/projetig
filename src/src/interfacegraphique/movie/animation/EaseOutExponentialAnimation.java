package interfacegraphique.movie.animation;

public class EaseOutExponentialAnimation extends Animation {
	@Override
	public double getValue(double t, double b, double c, double d) {
		return (c - b) * (-Math.pow(2, -10 * (t / d)) + 1) + b;
	}
}
