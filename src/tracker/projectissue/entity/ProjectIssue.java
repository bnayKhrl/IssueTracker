package tracker.projectissue.entity;

import tracker.project.entity.Project;
import tracker.user.entity.User;

public class ProjectIssue {
	private int id;
	private Project project;
	private User user;
	private String issue;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Project getProject() {
		return project;
	}
	public void setProject(Project project) {
		this.project = project;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public String getIssue() {
		return issue;
	}
	public void setIssue(String issue) {
		this.issue = issue;
	}
}
