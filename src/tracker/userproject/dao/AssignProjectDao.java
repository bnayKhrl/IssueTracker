package tracker.userproject.dao;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import tracker.dao.GenericDao;
import tracker.project.entity.Project;
import tracker.user.entity.User;
import tracker.userproject.entity.AssignUserProject;
import tracker.userproject.entity.UserProject;
import tracker.userproject.entity.UserProjectDetail;

public interface AssignProjectDao extends GenericDao<AssignUserProject> {
	
    public Collection<User> findUsers() throws SQLException;
	
	public Collection<Project> findProjects() throws SQLException;
	
	public Collection<Project> findProjectsByName(String project) throws SQLException;
	
	public void saveData(UserProject userProject,List<UserProjectDetail> userProjectDetailList) throws SQLException;
	
	public Collection<UserProject> findUserProject() throws SQLException;
	
	public Collection<UserProject> findNotAssignedUsers() throws SQLException;
	
	public Collection<AssignUserProject> findAllProjects(int id) throws SQLException;
	
	public Collection<AssignUserProject> findAllUserProject(int id) throws SQLException;
	
	public void deletedata(AssignUserProject assignUserProject,UserProject userProject) throws SQLException;
	
	public void editData(UserProject userProject,List<UserProjectDetail> userProjectDetailList) throws SQLException;

}
