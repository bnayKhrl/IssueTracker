package tracker.servlet;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter(urlPatterns = { "/*" })
public class AuthenticationFilter implements Filter {

	@Override
	public void destroy() {
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {
		String requestURI = ((HttpServletRequest) req).getRequestURI();
		if (requestURI.matches(".*(css|jpg|png|gif|js)$")) {
			// ignore static files
		} else if (requestURI.endsWith("login")) {

		} else {
			HttpSession session = ((HttpServletRequest) req).getSession();
			if (session.getAttribute("user") == null) {
				String loginPage = req.getServletContext().getContextPath() + "/login";
				((HttpServletResponse) res).sendRedirect(loginPage);
				return;
			}
		}
		chain.doFilter(req, res);

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
	}

}
