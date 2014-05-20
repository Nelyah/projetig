package interfacegraphique.movie.animation;

public class EaseInSinusoidalAnimation extends Animation {
	@Override
	public double getValue(double t, double b, double c, double d) {
		return (-(c - b)) * (Math.cos(Math.PI * (t / d)) - 1) + b;
	}
}
