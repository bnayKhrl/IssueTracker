package tracker.userproject.entity;

import tracker.project.entity.Project;

public class UserProjectDetail {
	
	private int id;
	private UserProject userProject;
	private Project project;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public UserProject getUserProject() {
		return userProject;
	}
	public void setUserProject(UserProject userProject) {
		this.userProject = userProject;
	}
	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}
	
	
}
