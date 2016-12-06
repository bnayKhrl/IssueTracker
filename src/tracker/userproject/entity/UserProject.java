package tracker.userproject.entity;

import tracker.project.entity.Project;
import tracker.user.entity.User;

public class UserProject {
	
	private int id;
	private User user;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
}
