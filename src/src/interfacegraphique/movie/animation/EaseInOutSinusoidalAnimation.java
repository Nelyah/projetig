package interfacegraphique.movie.animation;

public class EaseInOutSinusoidalAnimation extends Animation {
	@Override
	public double getValue(double t, double b, double c, double d) {
		return (-(c - b) / 2) * (Math.cos(Math.PI * t / d) - 1) + b;
	}
}
