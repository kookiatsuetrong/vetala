import jakarta.servlet.http.HttpSession;

import server.Storage;
import server.Setup;
import server.User;
import server.Email;

public class ResetHandler {
	
	/*
	*  Displays page for user to recover password
	*
	*  GET /reset-password
	*/
	static Object showPage(Context context) {
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
	static Object checkReset(Context context) {
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
	static Object askResetCode(Context context) {
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
	static Object checkResetCode(Context context) {
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
	static Object showFinal(Context context) {
		if (context.isLoggedIn()) {
			return context.redirect("/user-profile");
		}
		return context.render("/WEB-INF/reset-password-final.jsp");
	}
}