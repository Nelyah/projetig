package interfacegraphique.movie.animation;

public class EaseOutCircularAnimation extends Animation {
	@Override
	public double getValue(double t, double b, double c, double d) {
		double tt = (t / d) - 1;
		return (c - b) * Math.sqrt(1 - tt * tt) + b;
	}
}
