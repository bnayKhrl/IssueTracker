package tracker.rolemenudetail.dao;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import tracker.dao.GenericDao;
import tracker.rolemenudetail.entity.Action;
import tracker.rolemenudetail.entity.Menu;
import tracker.rolemenudetail.entity.MenuAction;
import tracker.rolemenudetail.entity.RoleMenu;
import tracker.rolemenudetail.entity.RoleMenuDetail;

public interface RoleMenuDetailDao extends GenericDao<RoleMenuDetail> {
	
	Collection<Menu> findAllMenu() throws SQLException;
	Collection<Action> findAllAction() throws SQLException;
	Collection<RoleMenu> findNotAssignedRole() throws SQLException;
	Collection<RoleMenu> findRoleMenu() throws SQLException;
	public void saveData(RoleMenu roleMenu,List<RoleMenuDetail> roleMenuDetailList) throws SQLException;
	Collection<MenuAction> findAllMenuAction(List<Integer> menuIdList, List<Integer> actionIdList) throws SQLException; 
	Collection<RoleMenuDetail> findAllMenuActionDetail(int id) throws SQLException;
	public void editData(RoleMenu roleMenu,List<RoleMenuDetail> roleMenuDetailList) throws SQLException;
	public void deleteData(RoleMenuDetail roleMenuDetail,RoleMenu roleMenu) throws SQLException;
	Collection<RoleMenuDetail> findAllAssignedMenu(int id) throws SQLException;
	Collection<RoleMenuDetail> findAllAssignedAction(int id) throws SQLException;
}
