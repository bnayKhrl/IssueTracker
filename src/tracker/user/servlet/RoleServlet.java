package tracker.user.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.http.HttpSession;

import com.sun.javafx.iio.ios.IosDescriptor;

import tracker.rolemenudetail.entity.RoleMenuDetail;
import tracker.user.dao.RoleDao;
import tracker.user.dao.RoleDaoImpl;
import tracker.user.entity.Role;
import tracker.user.entity.User;

@WebServlet("/roles/*")
public class RoleServlet extends HttpServlet {

	RoleDao roleDaoImpl = new RoleDaoImpl();

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
			if (menu.equalsIgnoreCase("Role") && action.equalsIgnoreCase("create")) {
				flag = true;
				request.setAttribute("datetoday", LocalDate.now());
				request.getRequestDispatcher("/WEB-INF/role/roleAdd.jsp").forward(request, response);
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
			if (menu.equalsIgnoreCase("Role") && action.equalsIgnoreCase("edit")) {
				flag = true;
				try {
					Role role = roleDaoImpl.findById(id);
					request.setAttribute("editRole", role);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				request.getRequestDispatcher("/WEB-INF/role/roleEdit.jsp").forward(request, response);
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
			if (menu.equalsIgnoreCase("Role") && action.equalsIgnoreCase("details")) {
				flag = true;
				try {
					Collection<Role> role = roleDaoImpl.findAll();
					request.setAttribute("roles", role);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				request.getRequestDispatcher("/WEB-INF/role/roleDetails.jsp").forward(request, response);
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
			if (menu.equalsIgnoreCase("Role") && action.equalsIgnoreCase("delete")) {
				flag = true;
				Role role = new Role();
				role.setId(id);
				try {
					roleDaoImpl.delete(role);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				response.sendRedirect(request.getServletContext().getContextPath() + "/roles");
			}
		}
		if (flag == false) {
			response.sendRedirect(request.getServletContext().getContextPath()+"/");
		}
	}

	void editProcess(HttpServletRequest request, HttpServletResponse response, int id)
			throws ServletException, IOException {
		System.out.println("Enters Edit Section");
		String roles = request.getParameter("Role");

		Role role = new Role();
		role.setId(id);
		role.setRole(roles);
		try {
			roleDaoImpl.edit(role);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		response.sendRedirect(request.getServletContext().getContextPath() + "/roles");
	}

	void createProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String roles = request.getParameter("Role");

		Role role = new Role();
		role.setRole(roles);
		try {
			roleDaoImpl.save(role);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		response.sendRedirect(request.getServletContext().getContextPath() + "/roles");
	}
}
