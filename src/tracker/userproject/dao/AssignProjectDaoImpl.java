package tracker.userproject.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.catalina.realm.UserDatabaseRealm;

import tracker.database.Database;
import tracker.project.entity.Project;
import tracker.user.entity.User;
import tracker.userproject.entity.AssignUserProject;
import tracker.userproject.entity.UserProject;
import tracker.userproject.entity.UserProjectDetail;

public class AssignProjectDaoImpl implements AssignProjectDao {

	@Override
	public void save(AssignUserProject account) throws SQLException {

	}

	@Override
	public int delete(AssignUserProject account) throws SQLException {
		String commandString = "delete from user_project_detail where project_id=? ";
		Connection conn = Database.getConnection();
		PreparedStatement stmt = conn.prepareStatement(commandString);
		stmt.setInt(1, account.getUserProjectDetail().getProject().getId());
		stmt.executeUpdate();
		return 1;
	}

	@Override
	public int edit(AssignUserProject account) throws SQLException {
		String commandString = "update user_project_detail set project_id=? where user_project_id=?";
		Connection conn = Database.getConnection();
		PreparedStatement stmt = conn.prepareStatement(commandString);
		stmt.setInt(1, account.getUserProjectDetail().getProject().getId());
		stmt.setInt(2, account.getUserProject().getId());
		stmt.executeUpdate();
		return 1;
	}

	@Override
	public Collection<AssignUserProject> findAll() throws SQLException {

		List<AssignUserProject> listAssignUserProject = new ArrayList<>();
		String commandString = "select up.id,up.user_id,u.id,u.username from users u inner join user_project up on up.user_id=u.id";
		Connection conn = Database.getConnection();
		PreparedStatement stmt = conn.prepareStatement(commandString);
		ResultSet rs = stmt.executeQuery();
		AssignUserProject assignUserProject = new AssignUserProject();
		while (rs.next()) {
			User user = new User();
			UserProject userProject = new UserProject();
			userProject.setId(rs.getInt("id"));
			user.setId(rs.getInt("id"));
			user.setUsername(rs.getString("username"));
			userProject.setUser(user);
			assignUserProject.setUserProject(userProject);
			listAssignUserProject.add(assignUserProject);
		}
		return listAssignUserProject;
	}

	@Override
	public AssignUserProject findById(Integer id) throws SQLException {
		String commandString = "select upd.id,up.id,u.id,u.username,up.user_id from user_project up inner join user_project_detail upd on up.id = upd.user_project_id inner join projects p on p.id=upd.project_id inner join users u on u.id=up.user_id where up.id=?";
		Connection conn = Database.getConnection();
		PreparedStatement stmt = conn.prepareStatement(commandString);
		stmt.setInt(1, id);
		ResultSet rs = stmt.executeQuery();
		AssignUserProject assignUserProject = new AssignUserProject();
		if(rs.next()) {
			User user = new User();
			UserProject userProject = new UserProject();
			userProject.setId(rs.getInt("id"));
			user.setId(rs.getInt("user_id"));
			user.setUsername(rs.getString("username"));
			userProject.setUser(user);
			assignUserProject.setUserProject(userProject);
		}
		return assignUserProject;
	}

	@Override
	public Collection<User> findUsers() throws SQLException {
		List<User> userList = new ArrayList<>();
		String commandString = "SELECT u.id,u.username FROM users u";
		Connection conn = Database.getConnection();
		PreparedStatement ps = conn.prepareStatement(commandString);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			User user = new User();
			user.setId(rs.getInt("id"));
			user.setUsername(rs.getString("username"));
			userList.add(user);
		}
		return userList;
	}

	@Override
	public Collection<Project> findProjects() throws SQLException {
		List<Project> projectList = new ArrayList<>();
		String commandString = "SELECT p.id,p.title FROM projects p";
		Connection conn = Database.getConnection();
		PreparedStatement ps = conn.prepareStatement(commandString);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			Project project = new Project();
			project.setId(rs.getInt("id"));
			project.setTitle(rs.getString("title"));
			projectList.add(project);
		}
		return projectList;
	}

	@Override
	public Collection<Project> findProjectsByName(String project) throws SQLException {
		List<Project> projectList = new ArrayList<>();
		try {
			String commandString = "SELECT p.id,p.title FROM projects p where p.title ilike ?";
			Connection conn = Database.getConnection();
			PreparedStatement ps = conn.prepareStatement(commandString);
			ps.setString(1, "%" + project + "%");
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Project projects = new Project();
				projects.setId(rs.getInt("id"));
				projects.setTitle(rs.getString("title"));
				projectList.add(projects);
			}
		} catch (SQLException ex) {
			System.out.println("in catch");
			ex.printStackTrace();
		}
		return projectList;
	}

	@Override
	public void saveData(UserProject userProject, List<UserProjectDetail> userProjectDetailList) throws SQLException {
		Connection conn = Database.getConnection();
		PreparedStatement stmt = conn.prepareStatement("insert into user_project(user_id) values(?)");
		stmt.setInt(1, userProject.getUser().getId());
		stmt.executeUpdate();
		for (UserProjectDetail userProjectDetail : userProjectDetailList) {
			stmt = conn.prepareStatement(
					"insert into user_project_detail(user_project_id,project_id) values((select id from user_project where user_id =?),?)");
			stmt.setInt(1, userProject.getUser().getId());
			stmt.setInt(2, userProjectDetail.getProject().getId());
			stmt.executeUpdate();
			stmt.close();
		}

	}

	@Override
	public Collection<UserProject> findUserProject() throws SQLException {
		List<UserProject> listUserProject = new ArrayList<>();
		String commandString = "select up.id,up.user_id,u.id,u.username from users u inner join user_project up on up.user_id=u.id";
		Connection conn = Database.getConnection();
		PreparedStatement stmt = conn.prepareStatement(commandString);
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			User user = new User();
			UserProject userProject = new UserProject();
			userProject.setId(rs.getInt("id"));
			user.setId(rs.getInt("user_id"));
			user.setUsername(rs.getString("username"));
			userProject.setUser(user);
			listUserProject.add(userProject);
		}
		return listUserProject;
	}

	@Override
	public Collection<AssignUserProject> findAllProjects(int id) throws SQLException {
		List<AssignUserProject> assignUserProjectList = new ArrayList<>();
		String commandString = "select up.id,upd.id,upd.user_project_id,upd.project_id,p.id,p.title from user_project up inner join user_project_detail upd on up.id=upd.user_project_id inner join projects p on p.id=upd.project_id where up.id=?";
		Connection conn = Database.getConnection();
		PreparedStatement stmt = conn.prepareStatement(commandString);
		stmt.setInt(1, id);
		System.out.println(id);
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			Project project = new Project();
			UserProject userProject = new UserProject();
			UserProjectDetail userProjectDetail = new UserProjectDetail();
			AssignUserProject assignUserProject = new AssignUserProject();
			userProjectDetail.setId(rs.getInt("id"));
			project.setId(rs.getInt("project_id"));
			project.setTitle(rs.getString("title"));
			userProjectDetail.setProject(project);
			assignUserProject.setUserProject(userProject);
			assignUserProject.setUserProjectDetail(userProjectDetail);
			assignUserProjectList.add(assignUserProject);
		}
		return assignUserProjectList;
	}

	@Override
	public void deletedata(AssignUserProject assignUserProject, UserProject userProject) throws SQLException {
		Connection conn = Database.getConnection();
		PreparedStatement stmt = conn.prepareStatement("delete from user_project_detail where user_project_id=?");
		stmt.setInt(1, assignUserProject.getUserProject().getId());
		stmt.executeUpdate();

		stmt = conn.prepareStatement("delete from user_project where id=?");
		stmt.setInt(1, userProject.getId());
		stmt.executeUpdate();
		stmt.close();
	}

	@Override
	public void editData(UserProject userProject,List<UserProjectDetail> userProjectDetailList) throws SQLException {
		Connection conn = Database.getConnection();
		PreparedStatement stmt = conn.prepareStatement("update user_project set user_id=? where id=(select id from user_project where user_id=?)");
		stmt.setInt(1, userProject.getUser().getId());
		stmt.setInt(2, userProject.getUser().getId());
		stmt.executeUpdate();
		stmt = conn.prepareStatement("delete from user_project_detail where  user_project_id=(select id from user_project where user_id=?)");
		stmt.setInt(1, userProject.getUser().getId());
		stmt.executeUpdate();
		for (UserProjectDetail userProjectDetail : userProjectDetailList) {
			stmt = conn.prepareStatement(
					"insert into user_project_detail(user_project_id,project_id) values((select id from user_project where user_id =?),?)");
			stmt.setInt(1, userProject.getUser().getId());
			stmt.setInt(2, userProjectDetail.getProject().getId());
			stmt.executeUpdate();
			stmt.close();
		}
	}

	@Override
	public Collection<AssignUserProject> findAllUserProject(int id) throws SQLException {
		List<AssignUserProject> listAssignUserProject = new ArrayList<>();
		String commandString = "select up.id,upd.id,upd.user_project_id,upd.project_id,p.id,p.title from user_project up inner join user_project_detail upd on up.id=upd.user_project_id inner join projects p on p.id=upd.project_id where up.id=?";
		Connection conn = Database.getConnection();
		PreparedStatement stmt = conn.prepareStatement(commandString);
		stmt.setInt(1, id);
		System.out.println(id);
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			Project project = new Project();
			UserProject userProject = new UserProject();
			UserProjectDetail userProjectDetail = new UserProjectDetail();
			AssignUserProject assignUserProject = new AssignUserProject();
			userProjectDetail.setId(rs.getInt("id"));
			userProject.setId(rs.getInt("user_project_id"));
			project.setId(rs.getInt("project_id"));
			project.setTitle(rs.getString("title"));
			userProjectDetail.setProject(project);
			userProjectDetail.setUserProject(userProject);
			assignUserProject.setUserProjectDetail(userProjectDetail);
			listAssignUserProject.add(assignUserProject);
		}
		return listAssignUserProject;
	}

	@Override
	public Collection<UserProject> findNotAssignedUsers() throws SQLException {
		List<UserProject> listUserProject = new ArrayList<>();
		String commandString = "select u.id,u.username from users u where u.id not in(select u.id from users u inner join user_project up on u.id=up.user_id)";
		Connection conn = Database.getConnection();
		PreparedStatement stmt = conn.prepareStatement(commandString);
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			User user = new User();
			UserProject userProject = new UserProject();
			user.setId(rs.getInt("id"));
			user.setUsername(rs.getString("username"));
			userProject.setUser(user);
			listUserProject.add(userProject);
		}
		return listUserProject;
	}
}
