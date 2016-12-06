package tracker.task.servlet;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import javax.servlet.Servlet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import tracker.task.dao.TaskDao;
import tracker.task.dao.TaskDaoImpl;
import tracker.task.entity.Task;
import tracker.task.factory.TaskFactory;

@WebServlet("/api/task/*")
public class TaskWs extends HttpServlet implements Servlet {
	private static final long serialVersionUID = 1L;
	TaskDao taskdao = new TaskDaoImpl();
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String path = request.getPathInfo();
		path = path == null ? "" : path;
		String[] parts = path.split("/");
		if (parts.length <= 1) {
			list(request, response);
		}  else if("search".equals(parts[1])){
			search (request, response);
		}
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String path = request.getPathInfo();
		String[] parts = path.split("/");
		if (parts[1].equals("create")) {
			createProcess(request, response);
		}

	}

	private void list(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		try {
			Collection<Task> task = taskdao.findAll();
			Gson gson = new Gson();
			String jsonString = gson.toJson(task);
			res.setContentType("application/json");
			res.getWriter().print(jsonString);
						
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	

	private void search(HttpServletRequest request, HttpServletResponse response) {
		try {
			
			Collection<Task> task = taskdao.findByTime();
			Gson gson = new Gson();
			String jsonString = gson.toJson(task);
			response.setContentType("application/json");
			response.getWriter().print(jsonString);
			System.out.println(jsonString);
								
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	private void createProcess(HttpServletRequest req, HttpServletResponse res) {
		List<Task> tasklist = TaskFactory.getDataFromJsonArray(req);
		try {
			
			taskdao.saveData(tasklist);
		} catch (Exception e) {
			e.printStackTrace();
		}
		res.setStatus(HttpServletResponse.SC_CREATED);
	}
}
