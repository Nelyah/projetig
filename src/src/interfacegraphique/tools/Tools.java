package interfacegraphique.tools;

import interfacegraphique.graphic.LeftBarPane;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public abstract class Tools {

	public static String getExtension(File f) {
		return Tools.getExtension(f.getName());
	}

	public static String getExtension(String s) {
		String ext = null;
		int i = s.lastIndexOf('.');

		if (i > 0 && i < s.length() - 1) {
			ext = s.substring(i + 1).toLowerCase();
		}

		return ext;
	}

	public static ImageIcon getIcon(String src) throws IOException {
		InputStream is = LeftBarPane.class.getClassLoader().getResourceAsStream(src);
		Image image = ImageIO.read(is);
		ImageIcon icon = new ImageIcon(image);

		return icon;
	}

	public static double round(double number, int precision) {
		return Math.round(number * Math.pow(10, precision)) / Math.pow(10, precision);
	}

}
