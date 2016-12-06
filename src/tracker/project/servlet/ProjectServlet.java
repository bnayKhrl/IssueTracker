package tracker.project.servlet;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import tracker.project.dao.ProjectDao;
import tracker.project.dao.ProjectDaoImpl;
import tracker.project.entity.Project;
import tracker.rolemenudetail.entity.RoleMenuDetail;
import tracker.user.entity.User;

@WebServlet("/projects/*")

public class ProjectServlet extends HttpServlet {

	ProjectDao projectDaoImpl = new ProjectDaoImpl();

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
			} else if (parts[1].equals("report")) {
				try {
					report(request, response);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else if (parts.length == 3 && parts[2].equals("edit")) {
				edit(request, response, Integer.parseInt(parts[1]));
			} else if (parts.length == 3 && parts[2].equals("delete")) {
				deleteProcess(request, response, Integer.parseInt(parts[1]));
			}
		}
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String path = req.getPathInfo();
		String[] parts = path.split("/");
		if (parts[1].equals("create")) {
			createProcess(req, resp);
		} else if (parts[2].equals("edit")) {
			editProcess(req, resp, Integer.parseInt(parts[1]));
		}
	}

	private void create(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		HttpSession session = req.getSession();
		Collection<RoleMenuDetail> sessionScope = (Collection<RoleMenuDetail>) session.getAttribute("rolemenudetail");
		String menus = null;
		String actions = null;
		boolean flag = false;
		for (RoleMenuDetail roleMenuDetail : sessionScope) {
			menus = roleMenuDetail.getMenuAction().getMenu().getItem();
			actions = roleMenuDetail.getMenuAction().getAction().getPermission();
			if (menus.equalsIgnoreCase("Project") && actions.equalsIgnoreCase("create")) {
				flag = true;
				req.getRequestDispatcher("/WEB-INF/project/projectAdd.jsp").forward(req, resp);
			}
		}
		if (flag == false) {
			resp.sendRedirect(req.getServletContext().getContextPath() + "/");
		}
	}

	private void list(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			Collection<Project> project = projectDaoImpl.findAll();
			req.setAttribute("projects", project);
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		req.getRequestDispatcher("/WEB-INF/project/projectDetails.jsp").forward(req, resp);
	}

	private void report(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException, SQLException {

		try {

			PdfPTable table = projectDaoImpl.reportGenerator();
			Document pdfReport = new Document(PageSize.A4);
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			PdfWriter.getInstance(pdfReport, baos);

			pdfReport.open();
			table.setSplitLate(false);
			table.setBreakPoints(3);
			pdfReport.add(table);
			pdfReport.close();

			response.setHeader("Content-Disposition", "inline; filename=\"Report.pdf\"");
			response.setHeader("Expires", "0");
			response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
			response.setHeader("Pragma", "public");
			response.setContentType("application/pdf");
			response.setContentLength(baos.size());

			OutputStream os = response.getOutputStream();
			baos.writeTo(os);
			os.flush();
			os.close();

		} catch (Exception e) {
			throw new IOException(e.getMessage());
		}
	}

	private void edit(HttpServletRequest req, HttpServletResponse resp, int id) throws ServletException, IOException {
		HttpSession session = req.getSession();
		Collection<RoleMenuDetail> sessionScope = (Collection<RoleMenuDetail>) session.getAttribute("rolemenudetail");
		String menus = null;
		String actions = null;
		boolean flag = false;
		for (RoleMenuDetail roleMenuDetail : sessionScope) {
			menus = roleMenuDetail.getMenuAction().getMenu().getItem();
			actions = roleMenuDetail.getMenuAction().getAction().getPermission();
			if (menus.equalsIgnoreCase("Assign Project") && actions.equalsIgnoreCase("create")) {
				flag = true;
				try {
					Project project = projectDaoImpl.findById(id);
					req.setAttribute("editProject", project);
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
				req.getRequestDispatcher("/WEB-INF/project/projectEdit.jsp").forward(req, resp);
			}

		}
		if (flag == false) {
			resp.sendRedirect(req.getServletContext().getContextPath() + "/");
		}
	}

	void createProcess(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String title = req.getParameter("projectTitle");
		LocalDate startdate = LocalDate.parse(req.getParameter("startDate"));
		LocalDate deadlinedate = LocalDate.parse(req.getParameter("deadlineDate"));

		Project project = new Project();
		project.setTitle(title);
		project.setStartdate(startdate);
		project.setDeadlinedate(deadlinedate);
		try {
			projectDaoImpl.save(project);
		} catch (Exception e) {
			e.printStackTrace();
		}
		resp.sendRedirect(req.getServletContext().getContextPath() + "/projects");

	}

	void editProcess(HttpServletRequest req, HttpServletResponse resp, int id) throws ServletException, IOException {
		String title = req.getParameter("projectTitle");
		LocalDate startdate = LocalDate.parse(req.getParameter("startDate"));
		LocalDate deadlinedate = LocalDate.parse(req.getParameter("deadlineDate"));

		Project project = new Project();
		project.setId(id);
		project.setTitle(title);
		project.setStartdate(startdate);
		project.setDeadlinedate(deadlinedate);
		try {
			projectDaoImpl.edit(project);
		} catch (Exception e) {
			e.printStackTrace();
		}
		resp.sendRedirect(req.getServletContext().getContextPath() + "/projects");
	}

	void deleteProcess(HttpServletRequest req, HttpServletResponse resp, int id) throws ServletException, IOException {

		HttpSession session = req.getSession();
		Collection<RoleMenuDetail> sessionScope = (Collection<RoleMenuDetail>) session.getAttribute("rolemenudetail");
		String menus = null;
		String actions = null;
		boolean flag = false;
		for (RoleMenuDetail roleMenuDetail : sessionScope) {
			menus = roleMenuDetail.getMenuAction().getMenu().getItem();
			actions = roleMenuDetail.getMenuAction().getAction().getPermission();
			if (menus.equalsIgnoreCase("Project") && actions.equalsIgnoreCase("create")) {
				flag = true;

				Project project = new Project();
				project.setId(id);
				try {
					projectDaoImpl.delete(project);
				} catch (Exception e) {
					e.printStackTrace();
				}
				resp.sendRedirect(req.getServletContext().getContextPath() + "/projects");
			}
		}

		if (flag == false) {
			resp.sendRedirect(req.getServletContext().getContextPath() + "/");
		}
	}

}
