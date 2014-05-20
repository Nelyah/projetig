package interfacegraphique.movie.animation;

public class EaseInCircularAnimation extends Animation {
	@Override
	public double getValue(double t, double b, double c, double d) {
		return (-(c - b)) * (Math.sqrt(1 - Math.pow(t / d, 2)) - 1) + b;
	}
}
