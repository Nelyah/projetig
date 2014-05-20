package interfacegraphique.projectmanager;

import interfacegraphique.movie.Project;
import interfacegraphique.svg.Svg;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

public class ProjectManagerSvg extends ProjectManager {

	public ProjectManagerSvg(Project p) {
		super(p);
	}

	@Override
	public boolean save(String pathname) {
		boolean saved = false;

		try {
			Svg svg = new Svg(this.project);

			BufferedWriter bw = new BufferedWriter(new FileWriter(pathname));
			bw.write(svg.toXMLString());
			bw.flush();
			bw.close();

			this.setSaved();
			saved = true;

		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return saved;
	}

	@Override
	public Project open(String pathname) {
		// TODO Auto-generated method stub
		return null;
	}

}
