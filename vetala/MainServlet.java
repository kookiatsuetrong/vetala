/**
 * The Main Servlet of Vetala engine
 *
 * This servlet can be deployed to any standard Java web server.
 *
 */

import jakarta.servlet.annotation.MultipartConfig;
import server.Setup;

import java.io.File;
import java.util.Map;
import java.util.TreeMap;
import java.rmi.Naming;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/*
	Common Operations:
	1. Create Account
	2. Log In
	3. Reset Password
	4. Contact

	Any POST request must be immediately followed by GET Request

	1. Create Account
	'
	'-- GET  /user-check-email
		POST /user-check-email
		'
		'-- GET  /user-register
			POST /user-register
			'
			'-- GET /user-profile
				'
				'-- GET /user-logout

	2. Log In
	'
	'-- GET  /user-check-email
		POST /user-check-email
		'
		'-- GET  /user-login
			POST /user-login
			'
			'-- GET /user-profile
				'
				'-- GET /user-logout

	3. Reset Password
	'
	'-- GET  /reset-password
		POST /reset-password
		'
		'-- GET  /reset-password-code
			POST /reset-password-code
			'
			'-- GET  /reset-password-final
				'
				'-- GET  /user-check-email

	4. Contact
	'
	'-- GET  /contact
		POST /contact
		'
		'-- GET  /contact-final

*/

@MultipartConfig
public class MainServlet extends HttpServlet {
	
	Map<String, ContextHandler> map = new TreeMap<>();

	public MainServlet() {
		Setup.reload();
		
		map.put( "GET /user-check-email", UserHandler::askEmail);
		map.put("POST /user-check-email", UserHandler::checkEmail);
		map.put( "GET /user-register",    UserHandler::showRegisterPage);
		map.put("POST /user-register",    UserHandler::createAccount);
		map.put( "GET /user-login",       UserHandler::showLogInPage);
		map.put("POST /user-login",       UserHandler::checkPassword);
		map.put( "GET /user-profile",     UserHandler::showProfilePage);
		map.put( "GET /user-password",    UserHandler::showPasswordPage);
		map.put("POST /user-password",    UserHandler::changePassword);
		map.put( "GET /user-photo",       UserHandler::showUserPhotoPage);
		map.put("POST /user-photo",       UserHandler::changeUserPhoto);
		map.put( "GET /user-logout",      UserHandler::showLogOutPage);
		
		map.put( "GET /error",            UserHandler::showErrorPage);
		
		map.put( "GET /friend-search",    FriendHandler::showSearchFriend);
		map.put( "GET /member-detail",    FriendHandler::showMemberDetail);
		
		map.put( "GET /service-list-friend",    Service::listFriend);
		map.put( "GET /service-user-status",    Service::replyUserStatus);
		map.put( "GET /service-accept-friend",  Service::acceptFriend);
		map.put( "GET /service-unfriend",       Service::unfriend);
		map.put( "GET /service-friend-request", Service::friendRequest);
		map.put( "GET /service-cancel-friend-request", 
												Service::cancelFriendRequest);
		map.put( "GET /service-reject-friend-request", 
												Service::rejectFriendRequest);
		map.put( "GET /service-list-friend-request",
												Service::listFriendRequest);
		map.put( "GET /service-list-friend-request-to-me",
												Service::listFriendRequestToMe);
		
		map.put( "GET /service-error",          Service::replyError);
		
		map.put( "GET /contact",       ContactHandler::showPage);
		map.put("POST /contact",       ContactHandler::saveDetail);
		map.put( "GET /contact-final", ContactHandler::showFinal);
		
		map.put( "GET /reset-password",       ResetHandler::showPage);
		map.put("POST /reset-password",       ResetHandler::checkReset);
		map.put( "GET /reset-password-code",  ResetHandler::askResetCode);
		map.put("POST /reset-password-code",  ResetHandler::checkResetCode);
		map.put( "GET /reset-password-final", ResetHandler::showFinal);
	}
	
	boolean valid(Object o) {
		return o != null;
	}
	
	@Override
	public void service(HttpServletRequest request,
						HttpServletResponse response) {
		String verb = request.getMethod();
		String uri  = request.getRequestURI();
		String pattern = verb + " " + uri;
		
		System.out.println(pattern);
		
		// TODO: Support GET Request writh /x/y/z
		// /whatever/parameter/sample/query
		
		// Case 1: External
		//         This case must be started with "/external"
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
			
		} catch (Exception e) { }
		
		System.out.println("The Internal Service");
		
		// Case 2: Internal Web Application
		//         This case must be in the URL mapping pattern 
		//         from the this servlet's constructor
		Context context = new Context();
		context.request = request;
		context.response = response;
		try {
			ContextHandler handler = map.get(pattern);
			if (valid(handler)) {
				handler.handle(context);
				return;
			}
		} catch (Exception e) { }
		
		// Case 3: JSP File
		//         This case can be any kind of JSP file
		var sc = getServletContext();
		try {
			if (uri.endsWith(".jsp")) {
				var rd = sc.getRequestDispatcher(uri);
				rd.forward(request, response);
				return;
			}
		} catch (Exception e) { }
		
		// Case 4: Static File
		//         This case must be a file in the home directory 
		try {
			var path  = sc.getRealPath(uri);
			var file  = new File(path);
			var found = file.exists();

			if (found) {
				System.out.println("Static file " + file);
				var rd = sc.getNamedDispatcher("default");
				rd.forward(request, response);
				return;
			}
		} catch (Exception e) { }

		// Case 5: Module
		//         This case must be started with "/module"
		
		// Case 6: Not Found
		context.redirect("/error");
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
			// TODO:
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


