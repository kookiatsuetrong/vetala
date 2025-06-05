import java.io.File;

import jakarta.servlet.http.Part;
import jakarta.servlet.http.HttpSession;

import server.Storage;
import server.Setup;
import server.User;
import server.Email;

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
		context.response.setStatus(404);
		
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
	*  Displays user photo for current user
	*
	*  GET /user-photo
	*/
	static Object showUserPhotoPage(Context context) {
		if (context.isLoggedIn()) {
			return context.render("/WEB-INF/user-photo.jsp");
		}
		return context.redirect("/user-check-email");
	}
	
	/*
	*  Changes user photo for current user
	*
	*  POST /user-photo
	*/
	static Object changeUserPhoto(Context context) {
		if (context.isLoggedIn()) {
			String path = context.request.getServletContext().getRealPath("");
			path += File.separator + "uploaded";
			
			HttpSession session = context.getSession(true);
			User current = (User)session.getAttribute("user");
			
			// TODO: Change to square photo
			// TODO: Change JPG to PNG format
			// TODO: Change Photo to about 320*320
			try {
				for (Part part : context.request.getParts()) {
					String file = path + File.separator + 
									"profile-" + current.number;
					
					String type = part.getContentType();
					switch (type) {
						case "image/png"  -> file += ".png";
						case "image/jpg"  -> file += ".jpg";
						case "image/jpeg" -> file += ".jpg";
					}
					System.out.println("File Name: " + file);
					part.write(file);
				}
			} catch (Exception e) {
				System.out.println(e);
			}
			
			return context.redirect("/user-photo");
		}
		return context.redirect("/user-check-email");
	}
}

