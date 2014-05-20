package interfacegraphique.movie.animation;

public class EaseInQuadraticAnimation extends Animation {
	@Override
	public double getValue(double t, double b, double c, double d) {
		return (c - b) * Math.pow(t / d, 2) + b;
	}
}
