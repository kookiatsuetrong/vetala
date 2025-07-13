import java.util.ArrayList;

import jakarta.json.Json;
import jakarta.json.JsonObject;

import server.User;
import server.Storage;
import server.UserManagement;


class FriendHandler {
	
	/*
	*  Displays friend list
	*
	*  GET /friend
	*  GET /friends
	*/
	static Object showFriend(Context context) {
		User user = context.getCurrentUser();
		if (valid(user)) {
			ArrayList<User> friends = Storage.getMyFriend(user.number);
			context.request.setAttribute("friends", friends);
			
			ArrayList<User> requests = Storage.getMyFriendRequest(user.number);
			context.request.setAttribute("requests", requests);
			
			ArrayList<User> toMe = Storage.getFriendRequestToMe(user.number);
			context.request.setAttribute("toMe", toMe);
			
			return context.render("/WEB-INF/friend.jsp");
		}
		return context.redirect("/user-check-email");
	}

	/*
	*  Displays the friend searching page
	*
	*  GET /friend-search
	*/
	static Object showSearchFriend(Context context) {
		if (context.isLoggedIn()) {		
			String query = context.getParameter("query");

			if (valid(query) && query.length() > 0) {
				ArrayList<User> result = Storage.searchFriend(query);
				context.request.setAttribute("list", result);
			}
			
			return context.render("/WEB-INF/friend-search.jsp");
		}
		return context.redirect("/user-check-email");
	}
	
	/*
	* Displays user detail of the given member
	*
	* GET /member-detail?number=123456
	*
	* GET /member-detail/123456               TODO: Support this
	* GET /member-detail/uuuu-xxxx-yyyy-zzzz  TODO: Support this
	*/
	static Object showMemberDetail(Context context) {
		if (context.isLoggedIn()) {
			String number = context.getParameter("number");
			User user = Storage.getUserByNumber(number);
			
			if (valid(number) && valid(user)) {
				context.request.setAttribute("user", user);
				return context.render("/WEB-INF/user-detail.jsp");
			}
			
			return context.render("/WEB-INF/user-not-found.jsp");
		}
		
		return context.redirect("/user-check-email");
	}
	
	static boolean valid(Object o) {
		return o != null;
	}
	
}
