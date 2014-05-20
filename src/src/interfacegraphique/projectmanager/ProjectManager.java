package interfacegraphique.projectmanager;

import interfacegraphique.movie.Project;

public abstract class ProjectManager {

	protected Project project;

	public ProjectManager(Project p) {
		this.project = p;
	}

	public abstract boolean save(String pathname);

	public abstract Project open(String pathname);

	public Project getProject() {
		return this.project;
	}

	public void setSaved() {
		this.project.setEdited(false);
	}

	public boolean save() {
		boolean saved = false;

		if (this.project.getPathname() != null) {
			saved = this.save(this.project.getPathname());
		}

		return saved;
	}
}
