package tracker.user.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sun.org.apache.regexp.internal.recompile;

import tracker.rolemenudetail.entity.RoleMenuDetail;
import tracker.user.dao.RoleDao;
import tracker.user.dao.RoleDaoImpl;
import tracker.user.dao.UserDao;
import tracker.user.dao.UserDaoImpl;
import tracker.user.entity.Role;
import tracker.user.entity.User;

@WebServlet("/users/*")
public class UserServlet extends HttpServlet {

	UserDao userDaoImpl = new UserDaoImpl();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String path = request.getPathInfo();
		path = path == null ? "" : path;
		String[] parts = path.split("/");

		if (parts.length <= 1) {
			list(request, response);
		} else if (parts.length <= 3 && parts.length > 1) {
			if (parts[1].equals("create")) {
				create(request, response);
			} else if (parts.length == 3 && parts[2].equals("edit")) {
				edit(request, response, Integer.parseInt(parts[1]));
			} else if (parts.length == 3 && parts[2].equals("delete")) {
				deleteProcess(request, response, Integer.parseInt(parts[1]));
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String path = request.getPathInfo();
		String[] parts = path.split("/");
		if (parts[1].equals("create")) {
			createProcess(request, response);
		} else if (parts[2].equals("edit")) {
			editProcess(request, response, Integer.parseInt(parts[1]));
		}
	}

	private void create(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		Collection<RoleMenuDetail> sessionScope = (Collection<RoleMenuDetail>) session.getAttribute("rolemenudetail");
		String menu = null;
		String action = null;
		boolean flag = false;
		for (RoleMenuDetail roleMenuDetail : sessionScope) {
			menu = roleMenuDetail.getMenuAction().getMenu().getItem();
			action = roleMenuDetail.getMenuAction().getAction().getPermission();
			if (menu.equalsIgnoreCase("User") && action.equalsIgnoreCase("create")) {
				flag = true;
				
				try {
					Collection<Role> roles = userDaoImpl.findRoles();
					request.setAttribute("roles", roles);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				request.getRequestDispatcher("/WEB-INF/user/userAdd.jsp").forward(request, response);
			}
		}
		if (flag == false) {
			response.sendRedirect(request.getServletContext().getContextPath()+"/");
		}
	}

	private void edit(HttpServletRequest request, HttpServletResponse response, int id)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		Collection<RoleMenuDetail> sessionScope = (Collection<RoleMenuDetail>) session.getAttribute("rolemenudetail");
		String menu = null;
		String action = null;
		boolean flag = false;
		for (RoleMenuDetail roleMenuDetail : sessionScope) {
			menu = roleMenuDetail.getMenuAction().getMenu().getItem();
			action = roleMenuDetail.getMenuAction().getAction().getPermission();
			if (menu.equalsIgnoreCase("User") && action.equalsIgnoreCase("edit")) {
				flag = true;
				try {
					User user = userDaoImpl.findById(id);
					request.setAttribute("editUser", user);
					Collection<Role> roles = userDaoImpl.findRoles();
					request.setAttribute("roles", roles);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				request.getRequestDispatcher("/WEB-INF/user/userEdit.jsp").forward(request, response);
			}
		}
		if (flag == false) {
			response.sendRedirect(request.getServletContext().getContextPath()+"/");
		}
	}

	private void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		Collection<RoleMenuDetail> sessionScope = (Collection<RoleMenuDetail>) session.getAttribute("rolemenudetail");
		String menu = null;
		String action = null;
		boolean flag = false;
		for (RoleMenuDetail roleMenuDetail : sessionScope) {
			menu = roleMenuDetail.getMenuAction().getMenu().getItem();
			action = roleMenuDetail.getMenuAction().getAction().getPermission();
			if (menu.equalsIgnoreCase("User") && action.equalsIgnoreCase("details")) {
				flag = true;
				try {
					Collection<User> user = userDaoImpl.findAll();
					request.setAttribute("users", user);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				request.getRequestDispatcher("/WEB-INF/user/userDetails.jsp").forward(request, response);
			}
		}
		if (flag == false) {
			response.sendRedirect(request.getServletContext().getContextPath()+"/");
		}
	}

	void deleteProcess(HttpServletRequest request, HttpServletResponse response, int id)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		Collection<RoleMenuDetail> sessionScope = (Collection<RoleMenuDetail>) session.getAttribute("rolemenudetail");
		String menu = null;
		String action = null;
		boolean flag = false;
		for (RoleMenuDetail roleMenuDetail : sessionScope) {
			menu = roleMenuDetail.getMenuAction().getMenu().getItem();
			action = roleMenuDetail.getMenuAction().getAction().getPermission();
			if (menu.equalsIgnoreCase("User") && action.equalsIgnoreCase("delete")) {
				flag = true;
				User user = new User();
				user.setId(id);
				
				try {
					userDaoImpl.delete(user);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				response.sendRedirect(request.getServletContext().getContextPath() + "/users");
			}
		}
		if (flag == false) {
			response.sendRedirect(request.getServletContext().getContextPath()+"/");
		}
	}

	void editProcess(HttpServletRequest request, HttpServletResponse response, int id)
			throws ServletException, IOException {
		System.out.println("Enters Edit Section");
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		int roles = Integer.parseInt(request.getParameter("role").toString());
		System.out.println(roles);

		System.out.println(username);
		System.out.println(password);

		User user = new User();
		Role role = new Role();

		user.setId(id);
		user.setUsername(username);
		user.setPassword(password);
		role.setId(roles);
		user.setRole(role);
		try {
			userDaoImpl.edit(user);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		response.sendRedirect(request.getServletContext().getContextPath() + "/users");
	}

	void createProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String username = request.getParameter("username");
		String password = request.getParameter("password");
		int roles = Integer.parseInt(request.getParameter("role").toString());
		System.out.println(roles);

		User user = new User();
		Role role = new Role();

		user.setUsername(username);
		user.setPassword(password);
		role.setId(roles);
		user.setRole(role);

		try {
			userDaoImpl.save(user);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		response.sendRedirect(request.getServletContext().getContextPath() + "/users");
	}
}
