package interfacegraphique.movie.animation;

public class EaseOutQuadraticAnimation extends Animation {
	@Override
	public double getValue(double t, double b, double c, double d) {
		return (-(c - b)) * ((t / d) * ((t / d) - 2)) + b;
	}
}
