package tracker.rolemenudetail.entity;

public class RoleMenuDetail{
	private int id;
	private RoleMenu roleMenu;
	private MenuAction menuAction;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public RoleMenu getRoleMenu() {
		return roleMenu;
	}
	public void setRoleMenu(RoleMenu roleMenu) {
		this.roleMenu = roleMenu;
	}
	public MenuAction getMenuAction() {
		return menuAction;
	}
	public void setMenuAction(MenuAction menuAction) {
		this.menuAction = menuAction;
	}
}
