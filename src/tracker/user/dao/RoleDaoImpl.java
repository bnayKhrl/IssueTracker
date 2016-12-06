package tracker.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import tracker.database.Database;
import tracker.user.entity.Role;

public class RoleDaoImpl implements RoleDao {

	@Override
	public void save(Role account) throws SQLException {
		String commandString = "insert into roles(role) values(?)";
		Connection conn = Database.getConnection();
		PreparedStatement stmt = conn.prepareStatement(commandString);
		stmt.setString(1, account.getRole());
		stmt.executeUpdate();
		stmt.close();
	}

	@Override
	public int delete(Role account) throws SQLException {
		String commandString = "delete from roles where id=?";
		Connection conn = Database.getConnection();
		PreparedStatement stmt = conn.prepareStatement(commandString);
		stmt.setInt(1, account.getId());
		stmt.executeUpdate();
		stmt.close();
		return 1;
	}

	@Override
	public int edit(Role account) throws SQLException {
		String commandString = "update roles set role=? where id=?";
		Connection conn = Database.getConnection();
		PreparedStatement stmt = conn.prepareStatement(commandString);
		stmt.setString(1, account.getRole());
		stmt.setInt(2, account.getId());
		stmt.executeUpdate();
		stmt.close();
		return 1;
	}

	@Override
	public Collection<Role> findAll() throws SQLException {
		List<Role> listRole = new ArrayList<>();
		String commandString = "select * from roles";
		Connection conn = Database.getConnection();
		PreparedStatement stmt = conn.prepareStatement(commandString);
		ResultSet rs = stmt.executeQuery();
		while(rs.next()){
			Role role = new Role();
			role.setId(rs.getInt("id"));
			role.setRole(rs.getString("role"));
			listRole.add(role);
		}
		return listRole;
	}

	@Override
	public Role findById(Integer id) throws SQLException {
		String commandString = "select * from roles where id=?";
		Connection conn = Database.getConnection();
		PreparedStatement stmt = conn.prepareStatement(commandString);
		stmt.setInt(1, id);
		ResultSet rs = stmt.executeQuery();
		Role role = new Role();
		if(rs.next()){
			role.setId(rs.getInt("id"));
			role.setRole(rs.getString("role"));
		}
		return role;
	}

}
