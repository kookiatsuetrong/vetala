import java.io.File;
import java.util.Map;
import java.util.TreeMap;
import java.rmi.Naming;
import java.io.PrintWriter;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpSession;

public class MainServlet extends HttpServlet {
	
	@Override
	public void service(HttpServletRequest request,
						HttpServletResponse response) {
		String verb = request.getMethod();
		String uri  = request.getRequestURI();
		String pattern = verb + " " + uri;
		System.out.println(pattern);
		
		try {
			String starting = "";
			String[] items = uri.trim().split("/");
			if (items.length > 1) {
				starting = items[1];
			}
			if ("external".equals(starting)) {
				externalService(request, response);
				return;
			}
			/*
			if (condition) {
				whatever();
				return
			}
			*/

			// The fallback condition
			internalService(request, response);
			
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	void internalService(HttpServletRequest request,
						HttpServletResponse response) {
		System.out.println("The Internal Service");
		try {
			String uri  = request.getRequestURI();
			if ("/user-check-email".equals(uri)) {
				Context c = new Context();
				c.request = request;
				c.response = response;
				String s = (String)askEmail(c);
				response.setHeader("Content-Type", "text/html");
				PrintWriter out = response.getWriter();
				out.print(s);
				return;
			}
			
			var context = getServletContext();
			var path    = context.getRealPath(uri);
			var file    = new File(path);
			var found   = file.exists();

			if (found) {
				var rd1 = context.getNamedDispatcher("default");
				rd1.forward(request, response);
				return;
			}
			
			var rd2 = context.getRequestDispatcher(uri);
			rd2.forward(request, response);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	/*
	*  Asks email address from the visitor
	*
	*  GET /user-check-email
	*
	*/
	Object askEmail(Context context) {
		if (context.isLoggedIn()) {
			return context.redirect("/user-profile");
		}
		HttpSession session = context.getSession(true);
		String code = Tool.randomPhotoCode();
		String photoCode = Tool.createPhotoCode(code);
		session.setAttribute("code", code);
		session.setAttribute("photo-code", photoCode);
		return context.render("/WEB-INF/user-ask-email.jsp");
	}
	
	void externalService(HttpServletRequest request,
						HttpServletResponse response) {
		System.out.println("The External Service");
		
		// 1. Create input map
		String verb = request.getMethod();
		String uri  = request.getRequestURI();
		String pattern = verb + " " + uri;
		
		Map<String,String[]> rawMap = request.getParameterMap();
		Map<String,String[]> map = new TreeMap<>();
		for (String key : rawMap.keySet()) {
			String[] items = rawMap.get(key);
			map.put(key, items);
		}
		printMap(map);
		
		try {
			// 2. Extract user detail
			
			// 3. Call the handler
			Handler remote = (Handler)Naming.lookup(Vetala.rmiRegistry);
			String result = remote.call(pattern, map);

			var out = response.getWriter();
			out.println("The External Service " + result);

		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	void printMap(Map<String,String[]> map) {
		for (String key : map.keySet()) {
			System.out.print(key + " -> ");
			String[] items = map.get(key);
			for (String s : items) {
				System.out.print(s + " ");
			}
			System.out.println();
		}
		System.out.println();	
	}
}

/*

ServletRequest

startAsync()
startAsync(request, response)

getAsyncContext()
getDispatcherType()

String                getContentType()
String                getParameter()
Map<String,String[]>  getParameterMap()
String                getProtocol()
String                getScheme()

HttpServletRequest

String                getMethod()
String                getRequestURI()
StringBuffer          getRequestURL()
Collection<Part>      getParts()

HttpSession           getSession()
HttpSession           getSession(boolean create)

Cookie[]              getCookies()

Enumeration<String>   getHeaderNames()
Enumeration<String>   getHeaders(String s)


ServletContext.getRequestDispatcher(String s)
ServletContext.getNamedDispatcher(String name)
ServletRequest.getRequestDispatcher(String s)


/favicon.ico


*/


