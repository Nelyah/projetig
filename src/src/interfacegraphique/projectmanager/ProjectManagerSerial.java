package interfacegraphique.projectmanager;

import interfacegraphique.movie.Project;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ProjectManagerSerial extends ProjectManager {

	public ProjectManagerSerial(Project p) {
		super(p);
	}

	@Override
	public boolean save(String pathname) {
		boolean saved = false;

		try {
			FileOutputStream f = new FileOutputStream(pathname);
			ObjectOutputStream oos = new ObjectOutputStream(f);
			oos.writeObject(this.project);
			oos.flush();
			oos.close();

			this.project.setPathname(pathname);
			this.setSaved();
			saved = true;

		} catch (IOException e) {
			e.printStackTrace();
		}

		return saved;
	}

	@Override
	public Project open(String pathname) {
		this.project = null;

		try {
			FileInputStream f = new FileInputStream(pathname);
			ObjectInputStream ois = new ObjectInputStream(f);
			this.project = (Project) ois.readObject();
			this.project.initComponents();
			this.project.setPathname(pathname);
			ois.close();

		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return this.project;
	}

}
