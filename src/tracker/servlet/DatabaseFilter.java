package tracker.servlet;

import java.io.IOException;

import java.sql.SQLException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import tracker.database.Database;

@WebFilter(urlPatterns = { "/*" })
public class DatabaseFilter implements Filter {
	
	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		try {
			String requestURI = ((HttpServletRequest) req).getRequestURI();

			if (requestURI.matches(".*(css|jpg|png|gif|js)$")) {
			} else {
				// System.out.println("Initializing database...");
				Database.init();
			}

			chain.doFilter(req, res);

		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
			res.getWriter().write(e.getMessage());
		}
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}

}
