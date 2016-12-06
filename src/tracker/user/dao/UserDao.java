package tracker.user.dao;

import java.sql.SQLException;
import java.util.Collection;

import tracker.dao.GenericDao;
import tracker.rolemenudetail.entity.RoleMenuDetail;
import tracker.user.entity.Role;
import tracker.user.entity.User;

public interface UserDao extends GenericDao<User>{

	public User findByUsernameAndPassword(String username, String password, String role) throws SQLException;
	
	public User checkExistingUsername(String username) throws SQLException;
	
	public Collection<Role> findRoles() throws SQLException;
	
	Collection<RoleMenuDetail> findAllRoleMenuDetail(int id) throws SQLException;

}
