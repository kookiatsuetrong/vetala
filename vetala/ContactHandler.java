import java.io.File;

import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;

import server.Storage;
import server.Setup;
import server.User;
import server.Email;

public class ContactHandler {
	
	/*
	*  Displays the contact page
	*
	*  GET /contact
	*/
	static Object showPage(Context context) {
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
	static Object saveDetail(Context context) {
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
	static Object showFinal(Context context) {
		return context.render("/WEB-INF/contact-final.jsp");
	}
}
