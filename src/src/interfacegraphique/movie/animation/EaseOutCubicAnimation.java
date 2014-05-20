package interfacegraphique.movie.animation;

public class EaseOutCubicAnimation extends Animation {
	@Override
	public double getValue(double t, double b, double c, double d) {
		double tt = (t / d) - 1;
		return (c - b) * (Math.pow(tt, 3) + 1) + b;
	}
}
