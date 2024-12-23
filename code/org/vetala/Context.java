package org.vetala;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class Context {
	public HttpServletRequest request;
	public HttpServletResponse response;

	public String render(String s) {
		RequestDispatcher rd = request.getRequestDispatcher(s);
		if (rd == null) return "Error";

		try {
			rd.forward(request, response);
			return "Forwarded";
		} catch (Exception e) {
			return "Error";
		}
	}

	public String redirect(String s) {
		try {
			response.sendRedirect(s);
			return "Forwarded";
		} catch (Exception e) {
			return "Error";
		}
	}

	public String getParameter(String s) {
		return request.getParameter(s);
	}

	public HttpSession getSession(boolean created) {
		return request.getSession(created);
	}

	public boolean isLoggedIn() {
		HttpSession session = request.getSession(true);
		User user = (User)session.getAttribute("user");
		return user != null;
	}

}













