package interfacegraphique.movie.animation;

public class LinearAnimation extends Animation {
	@Override
	public double getValue(double t, double b, double c, double d) {
		return (c - b) * t / d + b;
	}
}
