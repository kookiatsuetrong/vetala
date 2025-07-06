import java.util.ArrayList;

import jakarta.json.Json;
import jakarta.json.JsonObject;

import server.User;
import server.Storage;
import server.UserManagement;


class FriendHandler {

	/*
	*  Displays the search friend pages
	*
	*  GET /user-search-friend
	*/
	static Object showSearchFriend(Context context) {
		if (context.isLoggedIn()) {
			
			String query = context.getParameter("query");
			System.out.println("Query = " + query);
			
			if (valid(query) && query.length() > 0) {
				ArrayList<User> result = Storage.searchFriend(query);
				context.request.setAttribute("list", result);
			}
			
			return context.render("/WEB-INF/friend-search.jsp");
		}
		return context.redirect("/user-check-email");
	}
	
	
	/*
	*  Get User Status
	*
	*  GET /api/user-status
	*/
	static Object getUserStatus(Context context) {
		String result = "Access Denied";
		String user = context.getParameter("user");
		
		if (context.isLoggedIn()) {
			try {
				Integer number = Integer.valueOf(user);
				result = UserManagement.getUserStatus(number);
			} catch (Exception e) {
				result = "Invalid User";
			}
		}
		JsonObject o = Json.createObjectBuilder()
						.add("result", result)
						.add("user",   user)
						.build();
		
		return context.sendJson(o.toString());
	}
	
	static boolean valid(Object o) {
		return o != null;
	}

}
