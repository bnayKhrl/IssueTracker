package tracker.task.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tracker.task.dao.TaskDao;
import tracker.task.dao.TaskDaoImpl;

@WebServlet("/task/*")
public class TaskServlet extends HttpServlet {

	TaskDao taskdao = new TaskDaoImpl();
	private static final long serialVersionUID = 1L;

	public TaskServlet() {
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String path = request.getPathInfo();
		path = path == null ? "" : path;
		String[] parts = path.split("/");

		if (parts.length <= 1) {
			list(request, response);
		} else if (parts.length <= 3 && parts.length > 1) {
			if (parts[1].equals("create"))
				create(request, response);
		}else if ("search".equals(parts[2])) {
			search (request, response);
		}
	}

	private void search(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("/WEB-INF/task/taskDetail.jsp").forward(request, response);
		
	}

	private void create(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/task/taskAdd.jsp").forward(req, res);
	}

	private void list(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.getRequestDispatcher("/WEB-INF/task/taskDetail.jsp").forward(req, res);
	}

}
