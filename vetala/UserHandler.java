import java.io.File;
import java.util.Map;
import java.util.TreeMap;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import server.Setup;
import server.User;
import server.Email;
import server.EmailSender;

public class UserHandler {

	/*
	*  Asks email address from the visitor
	*
	*  GET /user-check-email
	*
	*/
	static Object askEmail(Context context) {
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
	static Object checkEmail(Context context) {
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
			
			if (Setup.emailEnabled) {
				new Email().sendActivationCode(email, activation);
			}
			if (Setup.emailEnabled == false) {
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
	static Object showRegisterPage(Context context) {
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
	static Object createAccount(Context context) {
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
		
		if (Setup.emailEnabled) {
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
	static Object showErrorPage(Context context) {
		return context.render("/WEB-INF/error.jsp");
	}


	/*
	*  Displays the log in page
	*
	*  GET /user-login
	*/
	static Object showLogInPage(Context context) {
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
	static Object checkPassword(Context context) {
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
	static Object showProfilePage(Context context) {
		if (context.isLoggedIn()) {
			// display user profile page
			return context.render("/WEB-INF/user-profile.jsp");
		}
			
		// ask user to login before continue
		return context.redirect("/user-check-email");
	}
	
	/*
	*  Displays password management for current user
	*
	*  GET /user-password
	*/
	static Object showPasswordPage(Context context) {
		if (context.isLoggedIn()) {
			return context.render("/WEB-INF/user-password.jsp");
		}
		return context.redirect("/user-check-email");
	}
	
	/*
	*  Changes the password for current user
	*
	*  POST /user-password
	*/
	static Object changePassword(Context context) {
		if (context.isLoggedIn() == false) {
			return context.redirect("/user-check-email");
		}
		
		String current  = context.getParameter("current");
		String password = context.getParameter("password");
		String confirm  = context.getParameter("confirm");
		
		if (password == null) password = "";
		
		HttpSession session = context.getSession(true);
		
		if (password.length() < 8) {
			session.setAttribute("message", ErrorMessage.PASSWORD_TOO_SHORT);
			return context.redirect("/user-password");
		}
		
		if (password.equals(confirm) == false) {
			session.setAttribute("message", 
					ErrorMessage.INCORRECT_CONFIRM_PASSWORD);
			return context.redirect("/user-password");
		}
		
		if (password.matches(".*[0-9].*") == false) {
			session.setAttribute("message", ErrorMessage.PASSWORD_NUMBER);
			return context.redirect("/user-password");
		}
		
		if (password.matches(".*[A-Z].*") == false) {
			session.setAttribute("message", ErrorMessage.PASSWORD_UPPERCASE);
			return context.redirect("/user-password");
		}
		
		if (password.matches(".*[a-z].*") == false) {
			session.setAttribute("message", ErrorMessage.PASSWORD_LOWERCASE);
			return context.redirect("/user-password");
		}
		
		User user = context.getCurrentUser();
		int result = Storage.changePassword(user.email, current, password);
		
		if (result == 0) {
			session.setAttribute("message", ErrorMessage.INCORRECT_CURRENT_PASSWORD);
			return context.redirect("/user-password");
		}

		session.setAttribute("message", ErrorMessage.PASSWORD_CHANGED);
		return context.redirect("/user-password");
	}
	
	/*
	*   Logs out
	*
	*   GET /user-logout
	*/
	static Object showLogOutPage(Context context) {
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
	static Object showResetPasswordPage(Context context) {
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
	static Object checkResetPassword(Context context) {
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
		
		if (Setup.emailEnabled) {
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
	static Object showResetPasswordCode(Context context) {
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
	static Object checkResetPasswordCode(Context context) {
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
		
		if (Setup.emailEnabled) {
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
	static Object showResetPasswordFinal(Context context) {
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
	static Object showContactPage(Context context) {
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
	static Object saveContactDetail(Context context) {
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
		if (Setup.emailEnabled) {
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
	static Object showContactFinalPage(Context context) {
		return context.render("/WEB-INF/contact-final.jsp");
	}

}

