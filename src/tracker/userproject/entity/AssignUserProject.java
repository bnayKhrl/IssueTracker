package tracker.userproject.entity;

public class AssignUserProject {
	
	private int id;
	private UserProject userProject;
	private UserProjectDetail userProjectDetail;
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
	public UserProjectDetail getUserProjectDetail() {
		return userProjectDetail;
	}
	public void setUserProjectDetail(UserProjectDetail userProjectDetail) {
		this.userProjectDetail = userProjectDetail;
	}
	
	
}
