package tracker.rolemenudetail.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javafx.scene.chart.PieChart.Data;
import tracker.database.Database;
import tracker.rolemenudetail.entity.Action;
import tracker.rolemenudetail.entity.Menu;
import tracker.rolemenudetail.entity.MenuAction;
import tracker.rolemenudetail.entity.RoleMenu;
import tracker.rolemenudetail.entity.RoleMenuDetail;
import tracker.user.entity.Role;

public class RoleMenuDetailDaoImpl implements RoleMenuDetailDao {

	@Override
	public void save(RoleMenuDetail account) throws SQLException {
		// TODO Auto-generated method stub
	}

	@Override
	public int delete(RoleMenuDetail account) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int edit(RoleMenuDetail account) throws SQLException {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Collection<RoleMenuDetail> findAll() throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public RoleMenuDetail findById(Integer id) throws SQLException {
		String commandString = "select rmd.id,rm.id,r.id,r.role,rm.role_id from role_menu_detail rmd inner join role_menu rm on rmd.role_menu_id=rm.id inner join roles r on r.id=rm.role_id where rm.id=?";
		Connection conn = Database.getConnection();
		PreparedStatement stmt = conn.prepareStatement(commandString);
		stmt.setInt(1, id);
		ResultSet rs = stmt.executeQuery();
		RoleMenuDetail roleMenuDetail = new RoleMenuDetail();
		if(rs.next()){
			Role role = new Role();
			RoleMenu roleMenu = new RoleMenu();
			roleMenu.setId(rs.getInt("id"));
			role.setId(rs.getInt("role_id"));
			role.setRole(rs.getString("role"));
			roleMenu.setRole(role);
			roleMenuDetail.setRoleMenu(roleMenu);
		}
		return roleMenuDetail;
	}

	@Override
	public Collection<Menu> findAllMenu() throws SQLException {
		List<Menu> listMenu = new ArrayList<>();
		String commandString = "select m.id,m.item from menu m";
		Connection conn = Database.getConnection();
		PreparedStatement stmt = conn.prepareStatement(commandString);
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			Menu menu = new Menu();
			menu.setId(rs.getInt("id"));
			menu.setItem(rs.getString("item"));
			listMenu.add(menu);
		}
		return listMenu;
	}

	@Override
	public Collection<Action> findAllAction() throws SQLException {
		List<Action> listAction = new ArrayList<>();
		String commandString = "select a.id,a.permission from action a";
		Connection conn = Database.getConnection();
		PreparedStatement stmt = conn.prepareStatement(commandString);
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			Action action = new Action();
			action.setId(rs.getInt("id"));
			action.setPermission(rs.getString("permission"));
			listAction.add(action);
		}
		return listAction;
	}

	@Override
	public Collection<MenuAction> findAllMenuAction(List<Integer> menuIdList, List<Integer> actionIdList)
			throws SQLException {
		List<MenuAction> listMenuAction = new ArrayList<>();
		StringBuilder commandString = new StringBuilder();
		commandString
				.append("select ma.id,ma.menu_id,ma.action_id,m.item,a.permission from menu_action ma inner join menu m on ma.menu_id=m.id "
						+ " inner join action a on ma.action_id=a.id WHERE menu_id in (");
		for (int i = 0; i < menuIdList.size(); i++) {
			commandString.append("?,");
		}
		commandString.deleteCharAt(commandString.length() - 1).toString();
		commandString.append(") AND action_id IN (");
		for (int i = 0; i < actionIdList.size(); i++) {
			commandString.append("?,");
		}
		commandString.deleteCharAt(commandString.length() - 1).toString();
		commandString.append(")");
		Connection conn = Database.getConnection();
		PreparedStatement stmt = conn.prepareStatement(commandString.toString());
		int index = 1;
		for (Integer menuId : menuIdList) {
			stmt.setInt(index, menuId);
			index++;
		}
		for (Integer actionId : actionIdList) {
			stmt.setInt(index, actionId);
			index++;
		}
		ResultSet rs = stmt.executeQuery();
		while (rs.next()) {
			MenuAction menuAction = new MenuAction();
			Menu menu = new Menu();
			Action action = new Action();
			menuAction.setId(rs.getInt("id"));
			menu.setId(rs.getInt("menu_id"));
			menu.setItem(rs.getString("item"));
			action.setId(rs.getInt("action_id"));
			action.setPermission(rs.getString("permission"));
			menuAction.setMenu(menu);
			menuAction.setAction(action);
			listMenuAction.add(menuAction);
		}
		return listMenuAction;
	}

	@Override
	public void saveData(RoleMenu roleMenu, List<RoleMenuDetail> roleMenuDetailList) throws SQLException {
		Connection conn = Database.getConnection();
		PreparedStatement stmt = conn.prepareStatement("insert into role_menu(role_id) values (?)");
		stmt.setInt(1, roleMenu.getRole().getId());
		stmt.executeUpdate();
		for(RoleMenuDetail roleMenuDetail : roleMenuDetailList){
			stmt = conn.prepareStatement("insert into role_menu_detail(role_menu_id,menu_action_id) values ((select id from role_menu where role_id=?),?)");
			stmt.setInt(1, roleMenu.getRole().getId());
			stmt.setInt(2, roleMenuDetail.getMenuAction().getId());
			stmt.executeUpdate();
			stmt.close();
		}
	}

	@Override
	public Collection<RoleMenu> findNotAssignedRole() throws SQLException {
		List<RoleMenu> roleMenuList = new ArrayList<>();
		String commandString = "select r.id,r.role from roles r where r.id not in(select r.id from roles r inner join role_menu rm on r.id=rm.role_id)";
		Connection conn = Database.getConnection();
		PreparedStatement stmt = conn.prepareStatement(commandString);
		ResultSet rs = stmt.executeQuery();
		while(rs.next()){
			Role role = new Role();
			RoleMenu roleMenu = new RoleMenu();
			role.setId(rs.getInt("id"));
			role.setRole(rs.getString("role"));
			roleMenu.setRole(role);
			roleMenuList.add(roleMenu);
		}
		return roleMenuList;
	}

	@Override
	public Collection<RoleMenu> findRoleMenu() throws SQLException {
		List<RoleMenu> roleMenuList = new ArrayList<>();
		String commandString = "select rm.id,rm.role_id,r.id,r.role from role_menu rm inner join roles r on rm.role_id=r.id";
		Connection conn = Database.getConnection();
		PreparedStatement stmt = conn.prepareStatement(commandString);
		ResultSet rs = stmt.executeQuery();
		while(rs.next()){
			Role role = new Role();
			RoleMenu roleMenu = new RoleMenu();
			roleMenu.setId(rs.getInt("id"));
			role.setId(rs.getInt("role_id"));
			role.setRole(rs.getString("role"));
			roleMenu.setRole(role);
			roleMenuList.add(roleMenu);
					
		}
		return roleMenuList;
	}

	@Override
	public Collection<RoleMenuDetail> findAllMenuActionDetail(int id) throws SQLException {
	 List<RoleMenuDetail> roleMenuDetailList = new ArrayList<>();
	 String commandString = "select rmd.id,rmd.role_menu_id,rmd.menu_action_id,rm.id,rm.role_id,ma.id,ma.menu_id,ma.action_id,m.id,m.item,a.id,a.permission from role_menu_detail rmd inner join role_menu rm on rmd.role_menu_id=rm.id inner join menu_action ma on ma.id=rmd.menu_action_id inner join menu m on m.id=ma.menu_id inner join action a on a.id=ma.action_id where rm.id=?";
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

	@Override
	public void deleteData(RoleMenuDetail roleMenuDetail, RoleMenu roleMenu) throws SQLException {
		String commandString = "delete from role_menu_detail where role_menu_id=?";
		Connection conn = Database.getConnection();
		PreparedStatement stmt = conn.prepareStatement(commandString);
		stmt.setInt(1, roleMenuDetail.getRoleMenu().getId());
		stmt.executeUpdate();
		
		stmt = conn.prepareStatement("delete from role_menu where id=?");
		stmt.setInt(1, roleMenu.getId());
		stmt.executeUpdate();
		stmt.close();
	}

	@Override
	public void editData(RoleMenu roleMenu, List<RoleMenuDetail> roleMenuDetailList) throws SQLException {
		Connection conn = Database.getConnection();
		PreparedStatement stmt = conn.prepareStatement("update role_menu set role_id=? where id=(select id from role_menu where role_id=?)");
		stmt.setInt(1, roleMenu.getRole().getId());
		stmt.setInt(2, roleMenu.getRole().getId());
		stmt.executeUpdate();
		stmt = conn.prepareStatement("delete from role_menu_detail where role_menu_id=(select id from role_menu where role_id=?)");
		stmt.setInt(1, roleMenu.getRole().getId());
		stmt.executeUpdate();
		for(RoleMenuDetail roleMenuDetail : roleMenuDetailList){
			stmt = conn.prepareStatement("insert into role_menu_detail(role_menu_id,menu_action_id) values((select id from role_menu where role_id=?),?)");
			stmt.setInt(1, roleMenu.getRole().getId());
			stmt.setInt(2, roleMenuDetail.getMenuAction().getId());
			stmt.executeUpdate();
			stmt.close();
		}
	}

	@Override
	public Collection<RoleMenuDetail> findAllAssignedMenu(int id) throws SQLException {
	 List<RoleMenuDetail> allAssignedMenuList = new ArrayList<>();
	 String commandString = "SELECT DISTINCT m.id,m.item,rmd.role_menu_id FROM role_menu_detail rmd, role_menu rm, menu_action ma, menu m WHERE rmd.role_menu_id=rm.id AND rmd.menu_action_id=ma.id AND ma.menu_id=m.id AND rm.id=?";
	 Connection conn = Database.getConnection();
	 PreparedStatement stmt = conn.prepareStatement(commandString);
	 stmt.setInt(1, id);
	 ResultSet rs = stmt.executeQuery();
	 while(rs.next()){
		 Menu menu = new Menu();
		 MenuAction menuAction = new MenuAction();
		 RoleMenu roleMenu = new RoleMenu();
		 RoleMenuDetail roleMenuDetail = new RoleMenuDetail();
		 roleMenu.setId(rs.getInt("role_menu_id"));
		 menu.setId(rs.getInt("id"));
		 menu.setItem(rs.getString("item"));
		 menuAction.setMenu(menu);
		 roleMenuDetail.setRoleMenu(roleMenu);
		 roleMenuDetail.setMenuAction(menuAction);
		 allAssignedMenuList.add(roleMenuDetail);
	 }
	 return allAssignedMenuList;
	}

	@Override
	public Collection<RoleMenuDetail> findAllAssignedAction(int id) throws SQLException {
		List<RoleMenuDetail> allAssignedActionList = new ArrayList<>();
		String commandString = "SELECT DISTINCT a.id,a.permission,rmd.role_menu_id FROM role_menu_detail rmd, role_menu rm, menu_action ma, action a WHERE rmd.role_menu_id=rm.id AND rmd.menu_action_id=ma.id AND ma.action_id=a.id AND rm.id=?";
		Connection conn = Database.getConnection();
		PreparedStatement stmt = conn.prepareStatement(commandString);
		stmt.setInt(1, id);
		ResultSet rs = stmt.executeQuery();
		while(rs.next()){
			Action action = new Action();
			MenuAction menuAction = new MenuAction();
			RoleMenu roleMenu = new RoleMenu();
			RoleMenuDetail roleMenuDetail = new RoleMenuDetail();
			roleMenu.setId(rs.getInt("role_menu_id"));
			action.setId(rs.getInt("id"));
			action.setPermission(rs.getString("permission"));
			menuAction.setAction(action);
			roleMenuDetail.setRoleMenu(roleMenu);
			roleMenuDetail.setMenuAction(menuAction);
			allAssignedActionList.add(roleMenuDetail);
		}
		return allAssignedActionList;
	}
		
}
