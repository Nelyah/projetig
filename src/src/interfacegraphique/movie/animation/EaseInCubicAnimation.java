package interfacegraphique.movie.animation;

public class EaseInCubicAnimation extends Animation {
	@Override
	public double getValue(double t, double b, double c, double d) {
		return (c - b) * Math.pow(t / d, 3) + b;
	}
}
