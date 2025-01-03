/*
 * The Main Servlet of Vetala engine
 *
 * This servlet can be deployed from the deployment descriptor.
 *
 */
 
import server.User;
import server.Email;
import server.EmailSender;

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
import jakarta.servlet.http.Part;

/*
	Common Operations:
	- Create Account
	- Log In
	- Reset Password
	- Contact

	Any POST request must be immediately followed by GET Request

	Create Account
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

	Log In
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

	Reset Password
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

	Contact
	'
	'-- GET  /contact
		POST /contact
		'
		'-- GET  /contact-final

*/



public class MainServlet extends HttpServlet {
	
	@Override
	public void service(HttpServletRequest request,
						HttpServletResponse response) {
		String verb = request.getMethod();
		String uri  = request.getRequestURI();
		String pattern = verb + " " + uri;
		System.out.println(pattern);
		
		// Case 0: External
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
			
		} catch (Exception e) {
			System.out.println(e);
		}
		
		// Case 1 - 3
		internalService(request, response);
	}
	
	void internalService(HttpServletRequest request,
						HttpServletResponse response) {
		System.out.println("The Internal Service");
		
		String verb = request.getMethod();
		String uri  = request.getRequestURI();
		String pattern = verb + " " + uri;
		
		// Case 1: Internal Web Application
		Context context = new Context();
		context.request = request;
		context.response = response;
		try {
		
			if ( "GET /user-check-email".equals(pattern)) {
				String result = (String)askEmail(context);
				sendAsWeb(context, result);
				return;
			}
			
			if ("POST /user-check-email".equals(pattern)) {
				String result = (String)checkEmail(context);
				sendAsWeb(context, result);
				return;
			}
			
			if ( "GET /user-register".equals(pattern)) {
				String result = (String)showRegisterPage(context);
				sendAsWeb(context, result);
				return;
			}
			
			if ("POST /user-register".equals(pattern)) {
				String result = (String)createAccount(context);
				sendAsWeb(context, result);
				return;
			}

			if ( "GET /user-login".equals(pattern)) {
				String result = (String)showLogInPage(context);
				sendAsWeb(context, result);
				return;
			}

			if ("POST /user-login".equals(pattern)) {
				String result = (String)checkPassword(context);
				sendAsWeb(context, result);
				return;
			}

			if ( "GET /user-profile".equals(pattern)) {
				String result = (String)showProfilePage(context);
				sendAsWeb(context, result);
				return;
			}

			if ( "GET /user-logout".equals(pattern)) {
				String result = (String)showLogOutPage(context);
				sendAsWeb(context, result);
				return;
			}

			if ( "GET /reset-password".equals(pattern)) {
				String result = (String)showResetPasswordPage(context);
				sendAsWeb(context, result);
				return;
			}

			if ("POST /reset-password".equals(pattern)) {
				String result = (String)checkResetPassword(context);
				sendAsWeb(context, result);
				return;
			}

			if ( "GET /reset-password-code".equals(pattern)) {
				String result = (String)showResetPasswordCode(context);
				sendAsWeb(context, result);
				return;
			}

			if ("POST /reset-password-code".equals(pattern)) {
				String result = (String)checkResetPasswordCode(context);
				sendAsWeb(context, result);
				return;
			}

			if ( "GET /reset-password-final".equals(pattern)) {
				String result = (String)showResetPasswordFinal(context);
				sendAsWeb(context, result);
				return;
			}
			
			if ( "GET /contact".equals(pattern)) {
				String result = (String)showContactPage(context);
				sendAsWeb(context, result);
				return;
			}

			if ("POST /contact".equals(pattern)) {
				String result = (String)saveContactDetail(context);
				sendAsWeb(context, result);
				return;
			}

			if ( "GET /contact-final".equals(pattern)) {
				String result = (String)showContactFinalPage(context);
				sendAsWeb(context, result);
				return;
			}
			
		} catch (Exception e) {
			System.out.println(e);
		}
		
		// Case 2: Static File
		var sc = getServletContext();
		try {
			var path    = sc.getRealPath(uri);
			var file    = new File(path);
			var found   = file.exists();

			if (found) {
				System.out.println("Static file " + file);
				var rd1 = sc.getNamedDispatcher("default");
				rd1.forward(request, response);
				return;
			}
		} catch (Exception e) { }
		
		// Case 3: Non-Static File
		try {
			var rd2 = sc.getRequestDispatcher(uri);
			rd2.forward(request, response);
		} catch (Exception e) {
			System.out.println(e);
		}
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
	
	void sendAsWeb(Context context, String result) {
		context.response.setHeader("Content-Type", "text/html");
		try {
			PrintWriter out = context.response.getWriter();
			out.print(result);
		} catch (Exception e) { }
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
	
	/*
	*  Checks the email address from the visitor
	*
	*  POST /user-check-email
	*
	*/
	Object checkEmail(Context context) {
		if (context.isLoggedIn()) {
			return context.redirect("/user-profile");
		}
		HttpSession session = context.getSession(true);
		String code = (String)session.getAttribute("code");
		session.removeAttribute("code");
		if (code == null) code = "";
		String photoCode = context.getParameter("code");
		
		System.out.println("photoCode " + photoCode);
		
		if (code.equals(photoCode) == false) {
			session.setAttribute("message", ErrorMessage.INCORRECT_PHOTO_CODE);
			return context.redirect("/user-check-email");	
		}
		
		String email = context.getParameter("email");
		User user = Storage.getUserByEmail(email);
		session.setAttribute("email", email);
		
		System.out.println("email " + email);

		if (user == null) {
			// This email is a new email, then create new account.
			// This email is not in the database, then create a new account.
			String activation = Tool.randomActivationCode();
			session.setAttribute("activation-code", activation);
			
			if (EmailSender.emailEnabled) {
				new Email().sendActivationCode(email, activation);
			}
			if (EmailSender.emailEnabled == false) {
				session.setAttribute("code", activation);
			}
			
			return context.redirect("/user-register");
		}

		// This email is in the database, then go to login page
		return context.redirect("/user-login");
	}
	
	/*
	*  Displays the create account page
	*
	*  GET /user-register
	*
	*/
	Object showRegisterPage(Context context) {
		if (context.isLoggedIn()) {
			return context.redirect("/user-profile");
		}
		
		HttpSession session = context.getSession(true);
		String email = (String)session.getAttribute("email");
		if (email == null) {
			return context.redirect("/user-check-email");
		}
		
		return context.render("/WEB-INF/user-register.jsp");
	}

	/*
	*  Verifies user detail and create account.
	*
	*  POST /user-register
	*
	*/	
	Object createAccount(Context context) {
		if (context.isLoggedIn()) {
			return context.redirect("/user-profile");
		}
		HttpSession session = context.getSession(true);
		String activation = (String)session.getAttribute("activation-code");
		
		String code      = context.getParameter("activation-code");
		String email     = context.getParameter("email");
		String password  = context.getParameter("password");
		String firstName = context.getParameter("first-name");
		String lastName  = context.getParameter("last-name");
		if (code      == null) code      = "";
		if (email     == null) email     = "";
		if (password  == null) password  = "";
		if (firstName == null) firstName = "";
		if (lastName  == null) lastName  = "";
		
		session.setAttribute("code",       code);
		session.setAttribute("email",      email);
		session.setAttribute("password",   password);
		session.setAttribute("first-name", firstName);
		session.setAttribute("last-name",  lastName);
		
		if (firstName.length() < 2) {
			session.setAttribute("message", ErrorMessage.INVALID_FIRST_NAME);
			return context.redirect("/user-register");
		}
		
		if (lastName.length() < 2) {
			session.setAttribute("message", ErrorMessage.INVALID_LAST_NAME);
			return context.redirect("/user-register");
		}
		
		if (password.length() < 8) {
			session.setAttribute("message", ErrorMessage.PASSWORD_TOO_SHORT);
			return context.redirect("/user-register");
		}
		
		if (password.matches(".*[0-9].*") == false) {
			session.setAttribute("message", ErrorMessage.PASSWORD_NUMBER);
			return context.redirect("/user-register");
		}
		
		if (password.matches(".*[A-Z].*") == false) {
			session.setAttribute("message", ErrorMessage.PASSWORD_UPPERCASE);
			return context.redirect("/user-register");
		}
		
		if (password.matches(".*[a-z].*") == false) {
			session.setAttribute("message", ErrorMessage.PASSWORD_LOWERCASE);
			return context.redirect("/user-register");
		}
		
		if (code.equals(activation) == false) {
			session.setAttribute("message", 
						ErrorMessage.INCORRECT_ACTIVATION_CODE);
			return context.redirect("/user-register");
		}
		
		session.removeAttribute("code");
		session.removeAttribute("email");
		session.removeAttribute("password");
		session.removeAttribute("first-name");
		session.removeAttribute("last-name");
		session.removeAttribute("activation-code");
		Storage.createAccount(email, password, firstName, lastName);
		
		User user = Storage.checkPassword(email, password);
		session.setAttribute("user", user);
		
		if (EmailSender.emailEnabled) {
			Email e = new Email();
			e.sendWelcome(email);
		}
		return context.redirect("/user-profile");
	}
	
	
	/*
	*  Displays error
	*
	*  GET /error
	*/
	static Object showError(Context context) {
		return context.render("/WEB-INF/error.jsp");
	}


	/*
	*  Displays the log in page
	*
	*  GET /user-login
	*/
	Object showLogInPage(Context context) {
		if (context.isLoggedIn()) {
			return context.redirect("/user-profile");
		}
		HttpSession session = context.getSession(true);
		String email = (String)session.getAttribute("email");
		if (email == null) {
			context.redirect("/user-check-email");
		}
		return context.render("/WEB-INF/user-login.jsp");
	}


	/*
	*  Checks email and password
	*
	*  POST /user-login
	*/
	Object checkPassword(Context context) {
		if (context.isLoggedIn()) {
			return context.redirect("/user-profile");
		}
		HttpSession session = context.getSession(true);
		var email    = context.getParameter("email");
		var password = context.getParameter("password");
		
		User user = Storage.checkPassword(email, password);
		if (user == null) {
			session.setAttribute("message", ErrorMessage.INCORRECT_PASSWORD);
			return context.redirect("/user-login");
		}
		
		session.setAttribute("email", email);
		session.setAttribute("user", user);
		return context.redirect("/user-profile");
	}


	/*
	*  Displays profile of the current user
	*
	*  GET /user-profile
	*/
	Object showProfilePage(Context context) {
		if (context.isLoggedIn()) {
			// display user profile page
			return context.render("/WEB-INF/user-profile.jsp");
		}
			
		// ask user to login before continue
		return context.redirect("/user-check-email");
	}
	
	
	/*
	*   Logs out
	*
	*   GET /user-logout
	*/
	Object showLogOutPage(Context context) {
		if (context.isLoggedIn()) {
			HttpSession session = context.getSession(true);
			session.removeAttribute("email");
			session.removeAttribute("user");
			session.invalidate();
		}
		return context.render("/WEB-INF/user-logout.jsp");
	}


	/*
	*  Displays page for user to recover password
	*
	*  GET /reset-password
	*/
	Object showResetPasswordPage(Context context) {
		if (context.isLoggedIn()) {
			return context.redirect("/user-profile");
		}
		HttpSession session = context.getSession(true);
		String code = Tool.randomPhotoCode();
		String photoCode = Tool.createPhotoCode(code);
		session.setAttribute("code", code);
		session.setAttribute("photo-code", photoCode);
		return context.render("/WEB-INF/reset-password.jsp");
	}


	/*
	*  Checks email and photo-code
	*
	*  POST /reset-password
	*/
	Object checkResetPassword(Context context) {
		if (context.isLoggedIn()) {
			return context.redirect("/user-profile");
		}
		HttpSession session = context.getSession(true);
		String email = context.getParameter("email");
		String code  = context.getParameter("code");
		if (email == null) email = "";
		if (code  == null) code  = "";
		
		session.setAttribute("email", email);
		
		String value = (String)session.getAttribute("code");
		
		User user = Storage.getUserByEmail(email);
		if (user == null) {
			session.setAttribute("message", ErrorMessage.EMAIL_NOT_FOUND);
			return context.redirect("/reset-password");
		}
		
		if (code.equals(value) == false) {
			session.setAttribute("message", ErrorMessage.INCORRECT_PHOTO_CODE);
			return context.redirect("/reset-password");
		}
		
		String activation = Tool.randomActivationCode();
		session.setAttribute("activation-code", activation);
		session.setAttribute("email", email);
		
		if (EmailSender.emailEnabled) {
			Email e = new Email();
			e.sendResetCode(email, activation);
		}
		
		// TODO: How user can reset password without email system
		// e.g. let administrator to send reset code directly to the email
		
		return context.redirect("/reset-password-code");
	}

	
	/*
	*  Asks the confirmation-code
	*
	*  GET /reset-password-code
	*/
	Object showResetPasswordCode(Context context) {
		if (context.isLoggedIn()) {
			return context.redirect("/user-profile");
		}
		HttpSession session = context.getSession(true);
		String activation = (String)session.getAttribute("activation-code");
		String email = (String)session.getAttribute("email");
		
		if (activation == null || email == null) {
			return context.redirect("/reset-password");
		}
		return context.render("/WEB-INF/reset-password-code.jsp");
	}


	/*
	*  Checks the confirmation-code
	*
	*  POST /reset-password-code
	*/
	Object checkResetPasswordCode(Context context) {
		if (context.isLoggedIn()) {
			return context.redirect("/user-profile");
		}
		HttpSession session = context.getSession(true);
		
		String email = (String)session.getAttribute("email");
		String code  = context.getParameter("activation-code");
		if (code == null) code = "";
		
		String value = (String)session.getAttribute("activation-code");
		if (code.equals(value) == false) {
			session.setAttribute("message", ErrorMessage.INCORRECT_RESET_CODE);
			return context.redirect("/reset-password-code");
		}
		
		String password = context.getParameter("password");
		String confirm = context.getParameter("confirm");
		
		if (password == null || password.length() < 8) {
			session.setAttribute("message", ErrorMessage.PASSWORD_TOO_SHORT);
			return context.redirect("/reset-password-code");
		}
		
		if (password.equals(confirm) == false) {
			session.setAttribute("message", 
					ErrorMessage.INCORRECT_CONFIRM_PASSWORD);
			return context.redirect("/reset-password-code");
		}
		
		if (password.matches(".*[0-9].*") == false) {
			session.setAttribute("message", ErrorMessage.PASSWORD_NUMBER);
			return context.redirect("/reset-password-code");
		}
		
		if (password.matches(".*[A-Z].*") == false) {
			session.setAttribute("message", ErrorMessage.PASSWORD_UPPERCASE);
			return context.redirect("/reset-password-code");
		}
		
		if (password.matches(".*[a-z].*") == false) {
			session.setAttribute("message", ErrorMessage.PASSWORD_LOWERCASE);
			return context.redirect("/reset-password-code");
		}
		
		Storage.resetPassword(email, password);
		
		if (EmailSender.emailEnabled) {
			Email e = new Email();
			e.sendResetConfirmation(email);
		}
		
		session.setAttribute("message", ErrorMessage.PASSWORD_RESET_SUCCESS);
		return context.redirect("/reset-password-final");
	}


	/*
	*  Displays the password resetting result
	*
	*  GET /reset-password-final
	*/
	Object showResetPasswordFinal(Context context) {
		if (context.isLoggedIn()) {
			return context.redirect("/user-profile");
		}
		return context.render("/WEB-INF/reset-password-final.jsp");
	}


	/*
	*  Displays the contact page
	*
	*  GET /contact
	*/
	Object showContactPage(Context context) {
		HttpSession session = context.getSession(true);
		if (context.isLoggedIn()) {
			User user = (User)session.getAttribute("user");
			session.setAttribute("email", user.email);
		}
		String code = Tool.randomPhotoCode();
		String photoCode = Tool.createPhotoCode(code);
		session.setAttribute("code", code);
		session.setAttribute("photo-code", photoCode);
		return context.render("/WEB-INF/contact.jsp");
	}


	/*
	*  Adds detail from contact page
	*
	*  POST /contact
	*/
	Object saveContactDetail(Context context) {
		String topic  = context.getParameter("topic");
		String detail = context.getParameter("detail");
		String email  = context.getParameter("email");
		String code   = context.getParameter("code");
		
		if (topic  == null) topic  = "";
		if (detail == null) detail = "";
		if (email  == null) email  = "";
		if (code   == null) code   = "";
		
		HttpSession session = context.getSession(true);
		String photoCode = (String)session.getAttribute("code");
		session.removeAttribute("code");
		
		if (code.equals(photoCode) == false) {
			session.setAttribute("topic", topic);
			session.setAttribute("detail", detail);
			session.setAttribute("email", email);
			session.setAttribute("message", ErrorMessage.INCORRECT_PHOTO_CODE);
			return context.redirect("/contact");
		}
	
		int record = Storage.saveContactMessage(topic, detail, email);
		if (EmailSender.emailEnabled) {
			Email e = new Email();
			e.sendContactSavedConfirmation(email);
		}
		
		String path = context.request.getServletContext().getRealPath("");
		path += File.separator + "uploaded";
		int n = 1;
		try {
			for (Part part : context.request.getParts()) {
				String name = part.getName();
				String type = part.getContentType();
				System.out.println(name + " " + type);
				if (type == null) continue;
				
				String file = path + File.separator + 
								"file-" + record + "-" + n;
				switch (type) {
					case "image/png"  -> file += ".png";
					case "image/jpg"  -> file += ".jpg";
					case "image/jpeg" -> file += ".jpg";
				}
				part.write(file);
				n++;
			}
		} catch (Exception e) { }
		
		session.setAttribute("message", ErrorMessage.CONTACT_SAVE_SUCCESS);
		return context.redirect("/contact-final");
	}
	
	/*
	*  Displays the contact page final message
	*
	*  GET /contact-final
	*/
	Object showContactFinalPage(Context context) {
		return context.render("/WEB-INF/contact-final.jsp");
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


