package tracker.rolemenudetail.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;

import tracker.rolemenudetail.dao.RoleMenuDetailDao;
import tracker.rolemenudetail.dao.RoleMenuDetailDaoImpl;
import tracker.rolemenudetail.entity.Action;
import tracker.rolemenudetail.entity.Menu;
import tracker.rolemenudetail.entity.MenuAction;
import tracker.rolemenudetail.entity.RoleMenu;
import tracker.rolemenudetail.entity.RoleMenuDetail;
import tracker.user.dao.UserDao;
import tracker.user.dao.UserDaoImpl;
import tracker.user.entity.Role;
import tracker.userproject.utils.HttpDataReader;

import com.google.gson.Gson;

@WebServlet("/rolemenudetails/*")
public class RoleMenuDetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	RoleMenuDetailDao roleMenuDetailDaoImpl = new RoleMenuDetailDaoImpl();
	UserDao userDaoImpl = new UserDaoImpl();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String path = request.getPathInfo();
		path = path == null ? "" : path;
		String[] parts = path.split("/");
		if (parts.length <= 1) {
			list(request, response);
		} else if (parts.length == 2 && "search".equals(parts[1])) {
			search(request, response);
		} else if (parts.length == 2 && "editdata".equals(parts[1])) {
			editData(request, response);
		} else if (parts.length <= 3 && parts.length > 1) {
			if ("create".equals(parts[1])) {
				create(request, response);
			} else if ("details".equals(parts[2])) {
				details(request, response, Integer.parseInt(parts[1]));
			} else if ("delete".equals(parts[2])) {
				deleteProcess(request, response, Integer.parseInt(parts[1]));
			} else if ("edit".equals(parts[2])) {
				edit(request, response, Integer.parseInt(parts[1]));
			} else if ("getdata".equals(parts[2])) {
				getDataForEdit(request, response, Integer.parseInt(parts[1]));
			} else if ("menus".equals(parts[2])) {
				getMenuData(request, response, Integer.parseInt(parts[1]));
			} else if ("actions".equals(parts[2])) {
				getActionData(request, response, Integer.parseInt(parts[1]));
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String path = request.getPathInfo();
		path = path == null ? "" : path;
		String[] parts = path.split("/");

		if ("create".equals(parts[1])) {
			createProcess(request, response);
		} else if ("edit".equals(parts[2])) {
			editProcess(request, response, Integer.parseInt(parts[1]));
		}
	}

	private void editData(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json");
		String jsonString = request.getParameter("jsonData");
		JSONObject jsonObject = HttpDataReader.convertStringToJSONObject(jsonString);
		List<Integer> menuIdList = new ArrayList<Integer>();
		JSONArray menuArray = jsonObject.getJSONArray("menuId");
		for (int i = 0; i < menuArray.length(); i++) {
			menuIdList.add(Integer.parseInt(menuArray.get(i).toString()));
		}
		List<Integer> actionIdList = new ArrayList<Integer>();
		JSONArray actionArray = jsonObject.getJSONArray("actionId");
		for (int i = 0; i < actionArray.length(); i++) {
			actionIdList.add(Integer.parseInt(actionArray.get(i).toString()));
		}
		try {
			Collection<MenuAction> menuAction = roleMenuDetailDaoImpl.findAllMenuAction(menuIdList, actionIdList);
			Gson gson = new Gson();
			response.getWriter().write(gson.toJson(menuAction));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void edit(HttpServletRequest request, HttpServletResponse response, int id)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		Collection<RoleMenuDetail> sessionScope = (Collection<RoleMenuDetail>) session.getAttribute("rolemenudetail");
		String menus = null;
		String actions = null;
		boolean flag = false;
		for (RoleMenuDetail roleMenuDetail : sessionScope) {
			menus = roleMenuDetail.getMenuAction().getMenu().getItem();
			actions = roleMenuDetail.getMenuAction().getAction().getPermission();
			if (menus.equalsIgnoreCase("Role Menu") && actions.equalsIgnoreCase("edit")) {
				flag = true;

				try {
					RoleMenuDetail roleMenuDetail1 = roleMenuDetailDaoImpl.findById(id);
					request.setAttribute("editRoleMenuDetail", roleMenuDetail1);

					Collection<Role> role = userDaoImpl.findRoles();
					request.setAttribute("roles", role);

					Collection<Menu> menu = roleMenuDetailDaoImpl.findAllMenu();
					request.setAttribute("menus", menu);

					Collection<Action> action = roleMenuDetailDaoImpl.findAllAction();
					request.setAttribute("actions", action);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				request.getRequestDispatcher("/WEB-INF/rolemenudetail/rolemenudetailEdit.jsp").forward(request,
						response);
			}
		}
		if (flag == false) {
			response.sendRedirect(request.getServletContext().getContextPath() + "/");
		}
	}

	private void getDataForEdit(HttpServletRequest request, HttpServletResponse response, int id)
			throws ServletException, IOException {
		try {
			response.setContentType("application/json");
			Collection<RoleMenuDetail> roleMenuDetail = roleMenuDetailDaoImpl.findAllMenuActionDetail(id);
			Gson gson = new Gson();
			response.getWriter().write(gson.toJson(roleMenuDetail));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void create(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		Collection<RoleMenuDetail> sessionScope = (Collection<RoleMenuDetail>) session.getAttribute("rolemenudetail");
		String menus = null;
		String actions = null;
		boolean flag = false;
		for (RoleMenuDetail roleMenuDetail : sessionScope) {
			menus = roleMenuDetail.getMenuAction().getMenu().getItem();
			actions = roleMenuDetail.getMenuAction().getAction().getPermission();
			if (menus.equalsIgnoreCase("Role Menu") && actions.equalsIgnoreCase("create")) {
				flag = true;

				try {
					Collection<Menu> menu = roleMenuDetailDaoImpl.findAllMenu();
					request.setAttribute("menus", menu);
					Collection<Action> action = roleMenuDetailDaoImpl.findAllAction();
					request.setAttribute("actions", action);
					
					System.out.println("inside servlet create");
					Collection<RoleMenu> role = roleMenuDetailDaoImpl.findNotAssignedRole();
					request.setAttribute("roles", role);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				request.getRequestDispatcher("/WEB-INF/rolemenudetail/rolemenudetailAdd.jsp").forward(request, response);
			}
			
		}
		if (flag == false) {
			response.sendRedirect(request.getServletContext().getContextPath() + "/");
		}
	}

	private void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		try {
			Collection<RoleMenu> roleMenu = roleMenuDetailDaoImpl.findRoleMenu();
			request.setAttribute("roleMenu", roleMenu);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		request.getRequestDispatcher("/WEB-INF/rolemenudetail/rolemenudetails.jsp").forward(request, response);
	}

	private void getActionData(HttpServletRequest request, HttpServletResponse response, int id)
			throws ServletException, IOException {
		try {
			response.setContentType("application/json");
			Collection<RoleMenuDetail> roleMenu = roleMenuDetailDaoImpl.findAllAssignedAction(id);
			Gson gson = new Gson();
			response.getWriter().write(gson.toJson(roleMenu));
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	private void getMenuData(HttpServletRequest request, HttpServletResponse response, int id)
			throws ServletException, IOException {
		try {
			response.setContentType("application/json");
			Collection<RoleMenuDetail> roleMenu = roleMenuDetailDaoImpl.findAllAssignedMenu(id);
			response.getWriter().write(new Gson().toJson(roleMenu));
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	private void details(HttpServletRequest request, HttpServletResponse response, int id)
			throws ServletException, IOException {

		try {
			response.setContentType("application/json");
			Collection<RoleMenuDetail> roleMenuDetail = roleMenuDetailDaoImpl.findAllMenuActionDetail(id);
			request.setAttribute("roleMenuDetail", roleMenuDetail);

			response.getWriter().write(new Gson().toJson(roleMenuDetail));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void search(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		String jsonString = request.getParameter("jsonData");
		JSONObject jsonObject = HttpDataReader.convertStringToJSONObject(jsonString);
		List<Integer> menuIdList = new ArrayList<Integer>();
		JSONArray menuArray = jsonObject.getJSONArray("menuId");
		for (int i = 0; i < menuArray.length(); i++) {
			menuIdList.add(Integer.parseInt(menuArray.get(i).toString()));
		}
		List<Integer> actionIdList = new ArrayList<Integer>();
		JSONArray actionArray = jsonObject.getJSONArray("actionId");
		for (int i = 0; i < actionArray.length(); i++) {
			actionIdList.add(Integer.parseInt(actionArray.get(i).toString()));
		}
		try {
			Collection<MenuAction> menuAction = roleMenuDetailDaoImpl.findAllMenuAction(menuIdList, actionIdList);
			Gson gson = new Gson();
			response.getWriter().write(gson.toJson(menuAction));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void deleteProcess(HttpServletRequest request, HttpServletResponse response, int id)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		Collection<RoleMenuDetail> sessionScope = (Collection<RoleMenuDetail>) session.getAttribute("rolemenudetail");
		String menus = null;
		String actions = null;
		boolean flag = false;
		for (RoleMenuDetail roleMenuDetail : sessionScope) {
			menus = roleMenuDetail.getMenuAction().getMenu().getItem();
			actions = roleMenuDetail.getMenuAction().getAction().getPermission();
			if (menus.equalsIgnoreCase("Role Menu") && actions.equalsIgnoreCase("delete")) {
				flag = true;

				RoleMenu roleMenu = new RoleMenu();
				roleMenu.setId(id);
				RoleMenuDetail roleMenuDetail1 = new RoleMenuDetail();
				roleMenuDetail1.setRoleMenu(roleMenu);
				try {
					roleMenuDetailDaoImpl.deleteData(roleMenuDetail1, roleMenu);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				response.sendRedirect(request.getServletContext().getContextPath() + "/rolemenudetails");
			}
		}
		if (flag == false) {
			response.sendRedirect(request.getServletContext().getContextPath() + "/");
		}
	}

	private void createProcess(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, NumberFormatException {

		JSONObject jsonObj = HttpDataReader.convertRawInputToJsonObject(request);
		RoleMenu roleMenu = new RoleMenu();
		Role role = new Role();
		role.setId(Integer.parseInt(jsonObj.get("role").toString()));
		roleMenu.setRole(role);

		JSONArray jsonArray = jsonObj.getJSONArray("roleMenuActionDetail");
		List<RoleMenuDetail> rolemenuDetailList = new ArrayList<RoleMenuDetail>();
		for (int i = 0; i < jsonArray.length(); i++) {
			String details = jsonArray.get(i).toString();
			JSONObject jsonObjDetail = null;
			try {
				jsonObjDetail = new JSONObject(details);
			} catch (Exception e) {
				e.printStackTrace();
			}
			RoleMenuDetail rolemenuDetail = new RoleMenuDetail();
			MenuAction menuAction = new MenuAction();
			menuAction.setId(Integer.parseInt(jsonObjDetail.get("menuActionId").toString()));
			rolemenuDetail.setMenuAction(menuAction);
			rolemenuDetailList.add(rolemenuDetail);
		}

		try {
			roleMenuDetailDaoImpl.saveData(roleMenu, rolemenuDetailList);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		response.setStatus(HttpServletResponse.SC_CREATED);
	}

	private void editProcess(HttpServletRequest request, HttpServletResponse response, int id)
			throws ServletException, IOException {
		StringBuffer jb = new StringBuffer();
		String line = null;
		BufferedReader reader = null;
		try {
			reader = request.getReader();

			while ((line = reader.readLine()) != null)
				jb.append(line);
		} catch (Exception e) {
			e.printStackTrace();

		}
		String jsonData = jb.toString();

		JSONObject jsonObj = null;
		try {
			jsonObj = new JSONObject(jsonData);
		} catch (Exception e) {
			e.printStackTrace();
		}

		RoleMenu roleMenu = new RoleMenu();
		Role role = new Role();
		role.setId(Integer.parseInt(jsonObj.get("role").toString()));
		roleMenu.setRole(role);

		JSONArray jsonArray = jsonObj.getJSONArray("roleMenuActionDetail");
		List<RoleMenuDetail> roleMenuDetailList = new ArrayList<RoleMenuDetail>();
		for (int i = 0; i < jsonArray.length(); i++) {
			String details = jsonArray.get(i).toString();
			JSONObject jsonObjDetail = null;
			try {
				jsonObjDetail = new JSONObject(details);
			} catch (Exception e) {
				e.printStackTrace();
			}
			RoleMenuDetail roleMenuDetail = new RoleMenuDetail();
			MenuAction menuAction = new MenuAction();
			menuAction.setId(Integer.parseInt(jsonObjDetail.get("menuActionId").toString()));
			roleMenuDetail.setMenuAction(menuAction);
			roleMenuDetailList.add(roleMenuDetail);
		}

		try {
			roleMenuDetailDaoImpl.editData(roleMenu, roleMenuDetailList);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		request.getRequestDispatcher("WEB-INF/rolemenudetail/rolemenudetails.jsp").forward(request, response);
	}
}
