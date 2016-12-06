package tracker.userproject.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
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

import com.google.gson.Gson;

import tracker.project.dao.ProjectDao;
import tracker.project.dao.ProjectDaoImpl;
import tracker.project.entity.Project;
import tracker.rolemenudetail.entity.RoleMenuDetail;
import tracker.user.dao.UserDao;
import tracker.user.dao.UserDaoImpl;
import tracker.user.entity.User;
import tracker.userproject.dao.AssignProjectDao;
import tracker.userproject.dao.AssignProjectDaoImpl;
import tracker.userproject.dao.UserProjectDao;
import tracker.userproject.dao.UserProjectDaoImpl;
import tracker.userproject.entity.AssignUserProject;
import tracker.userproject.entity.UserProject;
import tracker.userproject.entity.UserProjectDetail;
import tracker.userproject.utils.HttpDataReader;

@WebServlet("/assignprojects/*")
public class AssignProjectServlet extends HttpServlet {

	AssignProjectDao assignProjectDao = new AssignProjectDaoImpl();
	UserDao userDaoImpl = new UserDaoImpl();
	ProjectDao projectDaoImpl = new ProjectDaoImpl();
	UserProjectDao userProjectDao = new UserProjectDaoImpl();

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
			} else if (parts[1].equals("search")) {
				search(request, response);
			} else if (parts.length == 3 && parts[2].equals("details")) {
				details(request, response, Integer.parseInt(parts[1]));
			} else if (parts.length == 3 && "edit".equals(parts[2])) {
				edit(request, response, Integer.parseInt(parts[1]));
			} else if (parts.length == 3 && "getdata".equals(parts[2])) {
				getDataForEdit(request, response, Integer.parseInt(parts[1]));
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

	private void getDataForEdit(HttpServletRequest request, HttpServletResponse response, int id) {
		try {
			response.setContentType("application/json");
			Collection<AssignUserProject> assignUserProject = assignProjectDao.findAllUserProject(id);
			Gson gson = new Gson();
			response.getWriter().write(gson.toJson(assignUserProject));
		} catch (Exception ex) {
			ex.printStackTrace();
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
			if (menu.equalsIgnoreCase("Assign Project") && action.equalsIgnoreCase("create")) {
				flag = true;
				
				try {
					Collection<UserProject> users = assignProjectDao.findNotAssignedUsers();
					request.setAttribute("users", users);

					Collection<Project> projects = assignProjectDao.findProjects();
					request.setAttribute("projects", projects);
				} catch (SQLException e) {

					e.printStackTrace();
				}
			
				request.getRequestDispatcher("/WEB-INF/assignproject/assignprojectAdd.jsp").forward(request, response);
			}
		}
		if (flag == false) {
			response.sendRedirect(request.getServletContext().getContextPath()+"/");
		}
	}

	private void search(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String project = request.getParameter("title");
		Collection<Project> projects = new ArrayList<>();
		try {
			projects = assignProjectDao.findProjectsByName(project);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		Gson gsonData = new Gson();
		gsonData.toJson(projects);
		response.getWriter().write(new Gson().toJson(projects));
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
			if (menu.equalsIgnoreCase("Assign Project") && action.equalsIgnoreCase("edit")) {
				flag = true;
				try {
					AssignUserProject assignUserProject = assignProjectDao.findById(id);
					request.setAttribute("editAssignProject", assignUserProject);
					Collection<User> users = assignProjectDao.findUsers();
					request.setAttribute("users", users);

					/*
					 * Collection<Project> projects =
					 * assignProjectDao.findProjects();
					 * request.setAttribute("projects", projects);
					 */
				} catch (SQLException e) { // TODO Auto-generated catch block
					e.printStackTrace();
				}

				request.getRequestDispatcher("/WEB-INF/assignproject/assignprojectEdit.jsp").forward(request, response);
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
			if (menu.equalsIgnoreCase("Assign Project") && action.equalsIgnoreCase("details")) {
				flag = true;
				
				try {
					Collection<UserProject> assignUserProject = assignProjectDao.findUserProject();
					request.setAttribute("assignprojects", assignUserProject);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				request.getRequestDispatcher("/WEB-INF/assignproject/assignprojects.jsp").forward(request, response);
			}
		}
		if (flag == false) {
			response.sendRedirect(request.getServletContext().getContextPath()+"/");
		}
	}

	private void details(HttpServletRequest request, HttpServletResponse response, int id)
			throws ServletException, IOException {
		try {
			response.setContentType("application/json");
			Collection<AssignUserProject> userProjectDetails = assignProjectDao.findAllProjects(id);
			request.setAttribute("Assign Projects", userProjectDetails);

			response.getWriter().write(new Gson().toJson(userProjectDetails));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	private void deleteProcess(HttpServletRequest request, HttpServletResponse response, int id)
			throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		Collection<RoleMenuDetail> sessionScope = (Collection<RoleMenuDetail>) session.getAttribute("rolemenudetail");
		String menu = null;
		String action = null;
		boolean flag = false;
		for (RoleMenuDetail roleMenuDetail : sessionScope) {
			menu = roleMenuDetail.getMenuAction().getMenu().getItem();
			action = roleMenuDetail.getMenuAction().getAction().getPermission();
			if (menu.equalsIgnoreCase("Assign Project") && action.equalsIgnoreCase("delete")) {
				flag = true;
				UserProject userProject = new UserProject();
				userProject.setId(id);
				AssignUserProject assignUserProject = new AssignUserProject();
				assignUserProject.setUserProject(userProject);
				try {
					assignProjectDao.deletedata(assignUserProject, userProject);
				} catch (Exception ex) {
					ex.printStackTrace();
				}
				response.sendRedirect(request.getServletContext().getContextPath() + "/assignprojects");
			}
		}
		if (flag == false) {
			response.sendRedirect(request.getServletContext().getContextPath()+"/");
		}
	}

	private void editProcess(HttpServletRequest request, HttpServletResponse response, int id)

			throws ServletException, IOException {
		JSONObject jsonObj = HttpDataReader.convertRawInputToJsonObject(request);

		UserProject userProject = new UserProject();
		User user = new User();

		user.setId(Integer.parseInt(jsonObj.get("user").toString()));
		userProject.setUser(user);

		JSONArray jsonArray = jsonObj.getJSONArray("detail");
		List<UserProjectDetail> userProjectDetailList = new ArrayList<UserProjectDetail>();
		for (int i = 0; i < jsonArray.length(); i++) {
			String details = jsonArray.get(i).toString();
			JSONObject jsonObjDetail = null;
			try {
				jsonObjDetail = new JSONObject(details);
			} catch (Exception e) {
				e.printStackTrace();
			}
			UserProjectDetail userProjectDetail = new UserProjectDetail();
			Project project = new Project();
			System.out.println(jsonObjDetail.get("projectId").toString());
			project.setId(Integer.parseInt(jsonObjDetail.get("projectId").toString()));
			userProjectDetail.setProject(project);
			userProjectDetailList.add(userProjectDetail);
		}
		try {
			assignProjectDao.editData(userProject, userProjectDetailList);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		response.setStatus(HttpServletResponse.SC_CREATED);
	}

	void createProcess(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		JSONObject jsonObj = HttpDataReader.convertRawInputToJsonObject(request);

		UserProject userProject = new UserProject();
		User user = new User();
		user.setId(Integer.parseInt(jsonObj.get("user").toString()));
		userProject.setUser(user);

		System.out.println(jsonObj.get("user").toString());

		JSONArray jsonArray = jsonObj.getJSONArray("detail");
		List<UserProjectDetail> userProjectDetailList = new ArrayList<UserProjectDetail>();
		for (int i = 0; i < jsonArray.length(); i++) {
			String details = jsonArray.get(i).toString();
			JSONObject jsonObjDetail = null;
			try {
				jsonObjDetail = new JSONObject(details);
			} catch (Exception e) {
				e.printStackTrace();
			}
			UserProjectDetail userProjectDetail = new UserProjectDetail();
			Project project = new Project();
			project.setId(Integer.parseInt(jsonObjDetail.get("projectId").toString()));
			System.out.println(jsonObjDetail.get("projectId").toString());
			userProjectDetail.setProject(project);
			userProjectDetailList.add(userProjectDetail);
		}

		System.out.println(userProject.toString());
		try {
			assignProjectDao.saveData(userProject, userProjectDetailList);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		response.setStatus(HttpServletResponse.SC_CREATED);
	}

}
