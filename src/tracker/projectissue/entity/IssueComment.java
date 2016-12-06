package tracker.projectissue.entity;

import tracker.user.entity.User;

public class IssueComment {
	private int id;
	private User user;
	private ProjectIssue issue;
	private String comment;

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

	public ProjectIssue getIssue() {
		return issue;
	}

	public void setIssue(ProjectIssue issue) {
		this.issue = issue;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
