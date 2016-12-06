package tracker.user.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import tracker.rolemenudetail.entity.RoleMenuDetail;
import tracker.user.dao.UserDao;
import tracker.user.dao.UserDaoImpl;
import tracker.user.entity.User;

@WebServlet("/login")
public class UserLoginServlet extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		req.getRequestDispatcher("/WEB-INF/user/login.jsp").forward(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		try {
			String username = req.getParameter("username");
			String password = req.getParameter("password");
			String role = req.getParameter("role");

			UserDao userDao = new UserDaoImpl();
			User loggedUser = userDao.findByUsernameAndPassword(username, password, role);

			int id = loggedUser.getRole().getId();
			Collection<RoleMenuDetail> roleMenuDetail = userDao.findAllRoleMenuDetail(id);
			HttpSession sessions = req.getSession();
			sessions.setAttribute("rolemenudetail", roleMenuDetail);

			if (loggedUser != null) {
				HttpSession session = ((HttpServletRequest) req).getSession();
				session.setAttribute("user", loggedUser);
				resp.sendRedirect(req.getServletContext().getContextPath());
				return;
			}
			req.setAttribute("message", "Invalid login");
			req.getRequestDispatcher("WEB-INF/user/login.jsp").forward(req, resp);
		} catch (SQLException ex) {
			ex.printStackTrace();
			resp.getWriter().write(ex.getMessage());
		}
	}

}
