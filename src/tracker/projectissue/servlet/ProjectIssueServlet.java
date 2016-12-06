package tracker.projectissue.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import tracker.project.entity.Project;
import tracker.projectissue.dao.ProjectIssueDao;
import tracker.projectissue.dao.ProjectIssueDaoImpl;
import tracker.projectissue.entity.IssueComment;
import tracker.projectissue.entity.ProjectIssue;
import tracker.user.entity.User;
import tracker.userproject.dao.UserProjectDao;
import tracker.userproject.dao.UserProjectDaoImpl;
import tracker.userproject.entity.UserProject;
import tracker.userproject.entity.UserProjectDetail;

@WebServlet("/projectissue/*")
public class ProjectIssueServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	ProjectIssueDao projectissuedao = new ProjectIssueDaoImpl();

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String path = request.getPathInfo();
		path = path == null ? "" : path;
		String[] parts = path.split("/");

		HttpSession session = request.getSession();
		User userdto = (User) session.getAttribute("user");
		int id = userdto.getId();

		if (parts.length <= 1) {
			list(request, response, id);
		} else if (parts.length <= 3 && parts.length > 1) {
			if (parts[1].equals("create")) {
				create(request, response);
			} else if (parts[1].equals("search")) {
				search(request, response, id);
			} else if (parts.length == 3 && parts[2].equals("edit")) {
				edit(request, response);
			} else if (parts.length == 3 && parts[2].equals("delete")) {
				delete(request, response, Integer.parseInt(parts[1]));
			} else if (parts.length == 3 && parts[2].equals("comment")) {
				System.out.println("aayo doget ko comment ma");
				comment(request, response, Integer.parseInt(parts[1]));
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		User userdto = (User) session.getAttribute("user");
		int id = userdto.getId();

		String path = request.getPathInfo();
		String[] parts = path.split("/");

		if (parts[1].equals("create")) {
			createProcess(request, response, id);
		} else if (parts[2].equals("comment")) {
			commentProcess(request, response, Integer.parseInt(parts[1]));
		}

	}

	private void list(HttpServletRequest req, HttpServletResponse res, int id) throws ServletException, IOException {

		try {
			Collection<ProjectIssue> projectIssue = projectissuedao.findIssueAssignedProject(id);
			req.setAttribute("projectissue", projectIssue);

			System.out.println("list ma aayo ");
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		req.getRequestDispatcher("/WEB-INF/projectissue/projectissue.jsp").forward(req, res);
	}

	private void create(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		
		
		req.getRequestDispatcher("/WEB-INF/projectissue/projectissueadd.jsp").forward(req, res);
	}

	private void edit(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.getRequestDispatcher("/WEB-INF/projectissue/projectissueedit.jsp").forward(req, res);
	}

	private void delete(HttpServletRequest req, HttpServletResponse res, int id) throws ServletException, IOException {
		ProjectIssue projissue = new ProjectIssue();
		projissue.setId(id);

		try {
			projectissuedao.delete(projissue);
		} catch (Exception e) {
			e.printStackTrace();
		}
		res.sendRedirect(req.getServletContext().getContextPath() + "/projectissue");

	}

	private void comment(HttpServletRequest req, HttpServletResponse res, int id) throws ServletException, IOException {
			try {
				Collection<IssueComment> issuecomment = projectissuedao.issueComment(id);
				req.setAttribute("issuecomment", issuecomment);
				Collection<ProjectIssue> projectissue = projectissuedao.titleAndUser(id);
				req.setAttribute("projectissue", projectissue);
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		req.getRequestDispatcher("/WEB-INF/projectissue/projectcomment.jsp").forward(req, res);

	}

	private void search(HttpServletRequest req, HttpServletResponse res, int id) throws ServletException, IOException {
		Collection<UserProjectDetail> userAssignedProject = new ArrayList<>();
		try {
			res.setContentType("application/json");
			userAssignedProject = projectissuedao.findUserAssignedProject(id);

		} catch (Exception e) {
			e.printStackTrace();
		}
		res.getWriter().write(new Gson().toJson(userAssignedProject));
	}

	private void createProcess(HttpServletRequest req, HttpServletResponse res, int id) throws IOException {

		int pid = Integer.parseInt(req.getParameter("project").toString());
		String issue = req.getParameter("issue");

		Project project1 = new Project();
		ProjectIssue issue1 = new ProjectIssue();

		project1.setId(pid);
		issue1.setIssue(issue);
		issue1.setProject(project1);
		try {
			projectissuedao.saveData(issue1, id);
		} catch (Exception e) {
			// TODO: handle exception
		}

		res.sendRedirect(req.getServletContext().getContextPath() + "/projectissue");
	}
	
	private void commentProcess (HttpServletRequest req, HttpServletResponse res, int id){
		
		
	}

}
