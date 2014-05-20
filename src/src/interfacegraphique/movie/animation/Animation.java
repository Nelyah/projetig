package interfacegraphique.movie.animation;

import java.io.Serializable;

public abstract class Animation implements Cloneable, Serializable {

	/**
	 * Fonction qui en fonction du temps et des propriété d'origine et
	 * d'arrivée, renvoi la valeur courrante de la propriété observée.
	 * 
	 * Ex: une animation de position en x où au depart x vaut 50 et à la fin 200
	 * sur une durée de 1000ms <br>
	 * Depart : t = 0, b = 50, c = 200, d = 1000 <br>
	 * Milieu : t = 500, b = 50, c = 200, d = 1000 <br>
	 * Arrivee : t = 1000, b = 50, c = 200, d = 1000
	 * 
	 * t et d doivent utiliser la meme unité de temps
	 * 
	 * @param t le temps courrant dans l'animation
	 * @param b la valeur de depart de la propriété
	 * @param c la valeur d'arrive de la propriété
	 * @param d la duree de l'animation
	 * @return la valeur courrante de la propriété observée
	 */
	public abstract double getValue(double t, double b, double c, double d);

	/**
	 * Fonction qui en fonction du pourcentage d'avancement de l'animation
	 * retourne le pourcentage de la valeur de la propriété observée si la
	 * position d'origine n'est pas 0 il faudra par la suite incrementer la
	 * valeur de retour de cette fonction avec la valeur d'origine de la
	 * propriété
	 * 
	 * @param value le pourcentage d'avancement de l'animation
	 * @return le pourcentage de la valeur de la propriété observée
	 */
	public final double getValue(float value) {
		return this.getValue(value * 100, 0, 100, 100) / 100;
	}

	@Override
	public Object clone() {
		Object o = null;

		try {
			o = super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}

		return o;
	}

	@Override
	public String toString() {
		String name = this.getClass().getSimpleName().replaceAll("Animation$", "").replaceAll("(.)([A-Z])", "$1 $2");

		return name;
	}

}
