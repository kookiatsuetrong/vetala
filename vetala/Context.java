/**
 * The combination class for 
 * 1. HttpServletRequest
 * 2. HttpServletResponse
 * 
 */
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.PrintWriter;
import server.User;

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
	
	public String send(String s) {
		try {
			PrintWriter out = response.getWriter();
			out.print(s);
		} catch (Exception e) { }
		return "SENT";
	}
	
	public String sendJson(String s) {
		try {
			response.setHeader("Content-Type", 
					"application/json; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.print(s);
		} catch (Exception e) { }
		return "SENT";
	}
	
	public String getParameter(String s) {
		return request.getParameter(s);
	}
	
	public HttpSession getSession(boolean created) {
		return request.getSession(created);
	}
	
	public User getCurrentUser() {
		HttpSession session = request.getSession(true);
		User user = (User)session.getAttribute("user");
		return user;
	}
	
	public boolean isLoggedIn() {
		HttpSession session = request.getSession(true);
		User user = (User)session.getAttribute("user");
		return user != null;
	}
	
}

