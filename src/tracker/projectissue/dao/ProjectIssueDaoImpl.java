package tracker.projectissue.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import tracker.database.Database;
import tracker.project.entity.Project;
import tracker.projectissue.entity.IssueComment;
import tracker.projectissue.entity.ProjectIssue;
import tracker.user.entity.User;
import tracker.userproject.entity.UserProjectDetail;

public class ProjectIssueDaoImpl implements ProjectIssueDao {

	@Override
	public void save(ProjectIssue account) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int delete(ProjectIssue account) throws SQLException {
		String sql = "delete from project_issue where id=?";
		Connection conn = Database.getConnection();
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, account.getId());
		ps.executeUpdate();
		return 1;
	}
	

	@Override
	public int edit(ProjectIssue account) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Collection<ProjectIssue> findAll() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ProjectIssue findById(Integer id) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<ProjectIssue> findIssueAssignedProject(int id) throws SQLException {
		List<ProjectIssue> listAssignedProject = new ArrayList<>();
		String sql = "select pi.project_id, p.title, pi.issue, pi.id,u.username from projects p inner join project_issue pi on pi.project_id=p.id inner "
				+ "join users u on pi.user_id = u.id inner join user_project_detail upd on pi.project_id = upd.project_id "
				+ "inner join user_project up on upd.user_project_id = up.id where up.user_id = ?";
		Connection conn = Database.getConnection();
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, id);
		ResultSet rs = stmt.executeQuery();
		while (rs.next()){
			User user = new User();
			Project project = new Project();
			ProjectIssue projectissue = new ProjectIssue();
			projectissue.setId(rs.getInt("id"));	
			project.setId(rs.getInt("project_id"));
			project.setTitle(rs.getString("title"));
			projectissue.setIssue(rs.getString("issue"));
			user.setUsername(rs.getString("username"));
			projectissue.setUser(user);
			projectissue.setProject(project);
			listAssignedProject.add(projectissue);
			
		}
		return listAssignedProject;
	}

	@Override
	public Collection<UserProjectDetail> findUserAssignedProject(int id) throws SQLException {
		List<UserProjectDetail> listAssignedUserProject = new ArrayList<>();
		String sql = "select p.id, p.title from user_project_detail upd inner join user_project up on upd.user_project_id = up.id inner "
				+ "join users u on up.user_id = u.id inner join projects p on p.id= upd.project_id where u.id=?";
		Connection conn = Database.getConnection();
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, id);
		ResultSet rs = stmt.executeQuery();
		while(rs.next()){
			Project project = new Project();
			UserProjectDetail userProjectDetail = new UserProjectDetail();
			project.setId(rs.getInt("id"));
			project.setTitle(rs.getString("title"));
			userProjectDetail.setProject(project);
			listAssignedUserProject.add(userProjectDetail);	
		}
		return listAssignedUserProject;
	}

	@Override
	public void saveData(ProjectIssue projectissue, int id) throws SQLException {
		String sql = "insert into project_issue (project_id, user_id, issue) values (?,?,?)";
		Connection conn = Database.getConnection();
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, projectissue.getProject().getId());
		stmt.setInt(2, id);
		stmt.setString(3, projectissue.getIssue());
		stmt.executeUpdate();
		stmt.close();
		
	}

	@Override
	public Collection<IssueComment> issueComment(int id) throws SQLException {
		List<IssueComment> issuecomment = new ArrayList<>();
		String sql = " select pi.issue, u.username, ic.comment from issue_comment ic inner join project_issue pi on pi.id = ic.project_issue_id inner join users u on ic.user_id = u.id where pi.id = ?";
		Connection conn = Database.getConnection();
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, id);
		ResultSet rs = stmt.executeQuery();
		while (rs.next()){
			ProjectIssue projectissue = new ProjectIssue();
			User user = new User();
			IssueComment issuecomm = new IssueComment();
			projectissue.setIssue(rs.getString("issue"));
			user.setUsername(rs.getString("username"));
			issuecomm.setComment(rs.getString("comment"));
			issuecomm.setIssue(projectissue);
			issuecomm.setUser(user);
			issuecomment.add(issuecomm);
		}
		return issuecomment;
	}

	@Override
	public Collection<ProjectIssue> titleAndUser(int id) throws SQLException {
		List<ProjectIssue> titleanduser = new ArrayList<>();
		String sql = "select pi.id, pi.issue,pi.user_id, u.username from project_issue pi inner join users u on pi.user_id = u.id where pi.id = ?";
		Connection conn = Database.getConnection();
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, id);
		ResultSet rs = stmt.executeQuery();
		while(rs.next()){
			ProjectIssue projectissue = new ProjectIssue();
			User user = new User();
			projectissue.setId(rs.getInt("id"));
			projectissue.setIssue(rs.getString("issue"));
			user.setId(rs.getInt("user_id"));
			user.setUsername(rs.getString("username"));
			projectissue.setUser(user);
			titleanduser.add(projectissue);
		}
		return titleanduser;
	}


}
