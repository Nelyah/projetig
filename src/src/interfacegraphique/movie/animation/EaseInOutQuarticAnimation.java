package interfacegraphique.movie.animation;

public class EaseInOutQuarticAnimation extends Animation {
	@Override
	public double getValue(double t, double b, double c, double d) {
		double tt = t / (d / 2);
		if (tt < 1) {
			return ((c - b) / 2) * Math.pow(tt, 4) + b;
		} else {
			tt -= 2;
			return (-(c - b) / 2) * (Math.pow(tt, 4) - 2) + b;
		}
	}
}
