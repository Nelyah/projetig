package interfacegraphique.movie.animation;

public class EaseInQuarticAnimation extends Animation {
	@Override
	public double getValue(double t, double b, double c, double d) {
		return (c - b) * Math.pow(t / d, 4) + b;
	}
}
