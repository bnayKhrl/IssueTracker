package tracker.user.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import tracker.database.Database;
import tracker.rolemenudetail.entity.Action;
import tracker.rolemenudetail.entity.Menu;
import tracker.rolemenudetail.entity.MenuAction;
import tracker.rolemenudetail.entity.RoleMenu;
import tracker.rolemenudetail.entity.RoleMenuDetail;
import tracker.user.entity.Role;
import tracker.user.entity.User;

public class UserDaoImpl implements UserDao {

	@Override
	public void save(User account) throws SQLException {
		String commandString = "INSERT INTO users (username, password, role_id) VALUES (?,?,?)";
		Connection conn = Database.getConnection();
		PreparedStatement ps = conn.prepareStatement(commandString);
		ps.setString(1, account.getUsername());
		ps.setString(2, account.getPassword());
		ps.setInt(3, account.getRole().getId());
		ps.execute();
	}

	@Override
	public int delete(User account) throws SQLException {
		String commandString = "delete from users where id=?";
		Connection conn = Database.getConnection();
		PreparedStatement ps = conn.prepareStatement(commandString);
		ps.setInt(1, account.getId());
		ps.executeUpdate();
		return 1;
	}

	@Override
	public int edit(User account) throws SQLException {
		String commandString = "update users set username=?,password=?,role_id=? where id=?";
		Connection conn = Database.getConnection();
		PreparedStatement ps = conn.prepareStatement(commandString);
		ps.setString(1, account.getUsername());
		ps.setString(2, account.getPassword());
		ps.setInt(3, account.getRole().getId());
		ps.setInt(4, account.getId());
		ps.executeUpdate();
		return 1;
	}

	@Override
	public Collection<User> findAll() throws SQLException {
		List<User> list = new ArrayList<>();
		String commandString = "SELECT u.id,u.username,r.id as role_id,r.role FROM users u LEFT JOIN roles r ON u.role_id=r.id";
		Connection conn = Database.getConnection();
		PreparedStatement ps = conn.prepareStatement(commandString);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			User user = new User();
			Role roles = new Role();
			user.setId(rs.getInt("id"));
			user.setUsername(rs.getString("username"));
			roles.setId(rs.getInt("role_id"));
			roles.setRole(rs.getString("role"));
			user.setRole(roles);
			list.add(user);
		}
		return list;
	}

	@Override
	public User findById(Integer id)throws SQLException {
		String commandString = "select users.id,users.username,users.password,users.role_id,roles.role from users inner join roles on users.role_id=roles.id where users.id=?";
		Connection conn = Database.getConnection();
		PreparedStatement ps = conn.prepareStatement(commandString);
		ps.setInt(1, id);
		ResultSet rs = ps.executeQuery();
		User user = new User();
		Role roles = new Role();
		 if(rs.next()) {
			user.setId(rs.getInt("id"));
			user.setUsername(rs.getString("username"));
			user.setPassword(rs.getString("password"));
			roles.setId(rs.getInt("role_id"));
			roles.setRole(rs.getString("role"));
			user.setRole(roles);
		}
		return user;
	}

	@Override
	public User findByUsernameAndPassword(String username, String password, String role) throws SQLException {
		String commandString = "SELECT u.*, r.id rid, r.role as role FROM users u LEFT JOIN roles r ON u.role_id=r.id WHERE u.username=? AND u.password=?";
		Connection conn = Database.getConnection();
		PreparedStatement ps = conn.prepareStatement(commandString);
		ps.setString(1, username);
		ps.setString(2, password);

		ResultSet rs = ps.executeQuery();
		User user = null;

		while (rs.next()) {
			user = new User();
			user.setId(rs.getInt("id"));
			user.setUsername(rs.getString("username"));
			user.setPassword(rs.getString("password"));

			Role roles = new Role();
			roles.setId(rs.getInt("rid"));
			roles.setRole(rs.getString("role"));

			user.setRole(roles);
		}
		return user;
	}

	@Override
	public User checkExistingUsername(String username) throws SQLException {

		String commandString = "SELECT id FROM users WHERE username=?";
		Connection conn = Database.getConnection();

		PreparedStatement ps = conn.prepareStatement(commandString);
		ps.setString(1, username);

		User user = null;
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			user = new User();
			user.setId(rs.getInt("id"));
		}

		System.out.println("count...");

		return user;

	}
	
	@Override
	public Collection<Role> findRoles() throws SQLException {
		List<Role> list = new ArrayList<>();
		String commandString = "SELECT r.id,r.role FROM roles r";
		Connection conn = Database.getConnection();
		PreparedStatement ps = conn.prepareStatement(commandString);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			Role roles = new Role();
			roles.setId(rs.getInt("id"));
			roles.setRole(rs.getString("role"));
			list.add(roles);
		}
		return list;
	}

	@Override
	public Collection<RoleMenuDetail> findAllRoleMenuDetail(int id) throws SQLException {
		List<RoleMenuDetail> roleMenuDetailList = new ArrayList<>();
		 String commandString = "select rmd.id,rmd.role_menu_id,rmd.menu_action_id,rm.id,rm.role_id,ma.id,ma.menu_id,ma.action_id,m.id,m.item,a.id,a.permission from role_menu_detail rmd inner join role_menu rm on rmd.role_menu_id=rm.id inner join menu_action ma on ma.id=rmd.menu_action_id inner join menu m on m.id=ma.menu_id inner join action a on a.id=ma.action_id where rm.role_id=?";
		 Connection conn = Database.getConnection();
		 PreparedStatement stmt = conn.prepareStatement(commandString);
		 stmt.setInt(1, id);
		 ResultSet rs = stmt.executeQuery();
		 while(rs.next()){
			 RoleMenuDetail roleMenuDetail = new RoleMenuDetail();
			 RoleMenu roleMenu = new RoleMenu();
			 MenuAction menuAction = new MenuAction();
			 Menu menu = new Menu();
			 Action action = new Action();
			 roleMenuDetail.setId(rs.getInt("id"));
			 roleMenu.setId(rs.getInt("role_menu_id"));
			 menu.setId(rs.getInt("menu_id"));
			 menu.setItem(rs.getString("item"));
			 menuAction.setMenu(menu);
			 action.setId(rs.getInt("action_id"));
			 action.setPermission(rs.getString("permission"));
			 menuAction.setAction(action);
			 roleMenuDetail.setRoleMenu(roleMenu);
			 roleMenuDetail.setMenuAction(menuAction);
			 roleMenuDetailList.add(roleMenuDetail);
		 }
			return roleMenuDetailList;
	}
	
}
