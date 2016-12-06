package tracker.userproject.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

import tracker.database.Database;
import tracker.project.entity.Project;
import tracker.user.entity.User;
import tracker.userproject.entity.AssignUserProject;
import tracker.userproject.entity.UserProject;
import tracker.userproject.entity.UserProjectDetail;

public class UserProjectDaoImpl implements UserProjectDao {

	@Override
	public void save(UserProject account) throws SQLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int delete(UserProject account) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int edit(UserProject account) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Collection<UserProject> findAll() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserProject findById(Integer id) throws SQLException {
		String commandString = "select up.id,u.id,u.username,up.id,upd.id,p.id,p.title from user_project as up join user_project_detail as upd on up.id = upd.user_project_id inner join projects p on p.id=upd.project_id inner join users u on u.id=up.user_id where up.id=?";
		Connection conn = Database.getConnection();
		PreparedStatement stmt = conn.prepareStatement(commandString);
		stmt.setInt(1, id);
		ResultSet rs = stmt.executeQuery();
		
		User user = new User();
		Project project = new Project();
		UserProject userProject = new UserProject();
		if(rs.next()){
			userProject.setId(rs.getInt("id"));
			user.setId(rs.getInt("user_id"));
			user.setUsername(rs.getString("username"));
			userProject.setUser(user);
		}
			return userProject;
	}

}
