package interfacegraphique.movie.animation;

public class EaseInOutQuadraticAnimation extends Animation {
	@Override
	public double getValue(double t, double b, double c, double d) {
		double tt = t / (d / 2);
		if (tt < 1) {
			// a verifier :
			return (c - b) * Math.pow(tt, 2) + b;
		} else {
			tt--;
			return (-(c - b) / 2) * (tt * (tt - 2) - 1) + b;
		}
	}
}
