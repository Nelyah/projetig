package interfacegraphique.tools;

import java.util.ResourceBundle;

public abstract class Translator {

	private static ResourceBundle R_BUNDLE = ResourceBundle.getBundle("text");

	public static String getString(String key) {
		return R_BUNDLE.getString(key);
	}

}
