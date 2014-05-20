package interfacegraphique.movie.animation;

public class EaseOutSinusoidalAnimation extends Animation {
	@Override
	public double getValue(double t, double b, double c, double d) {
		return (c - b) * Math.sin((t / d) * (Math.PI / 2)) + b;
	}
}
