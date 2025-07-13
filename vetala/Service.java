import java.util.ArrayList;
import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;

import server.Storage;
import server.User;
import server.UserManagement;

class Service {
	
	static Object replySuccess(Context context) {
		JsonObject result = Json.createObjectBuilder()
						.add("result", "OK")
						.build();
		String text = result.toString();
		return (Object)context.sendJson(text);
	}
	
	static Object replyError(Context context) {
		JsonObject result = Json.createObjectBuilder()
						.add("result", "ERROR")
						.build();
		String text = result.toString();
		return (Object)context.sendJson(text);
	}
	
	/*
	*  Get User Status
	*
	*  GET /service-user-status?user=123456
	*/
	static Object replyUserStatus(Context context) {
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
	
	
	/*
	*  Get User Detail
	*
	*  GET /service-user-detail?number=123456
	*/
	/*
	static Object replyUserDetail(Context context) {		
		if (context.isLoggedIn()) {
			String targetString = context.getParameter("number");
			try {
				int number = Integer.parseInt(targetString);
				User user = Storage.getUserDetail(number);

				JsonObject o = Json.createObjectBuilder()
								.add("result", result)
								.add("user",   user)
								.build();

				return context.sendJson(o.toString());

			} catch (Exception e) { }
			
			return // Invalid User Number
		}
		return // Access Denied
	}
	*/
	
	/*
	* Friend Request
	*
	* GET /service-friend-request?number=123456
	*
	* TODO: Add friend request two ways to accept automatically
	*
	*/
	static Object friendRequest(Context context) {
		String targetString = context.getParameter("number");
		try {
			int target = Integer.parseInt(targetString);
			User user  = context.getCurrentUser();
			
			if (valid(user)) {
				int source = user.number;
				Storage.addFriendRequest(source, target);
				return replySuccess(context);
			}
		} catch (Exception e) { }
		return replyError(context);
	}
	
	/*
	* Cancel Friend Request
	*
	* GET /service-cancel-friend-request?number=123456
	*/
	static Object cancelFriendRequest(Context context) {
		String targetString = context.getParameter("number");
		try {
			int target = Integer.parseInt(targetString);
			User user  = context.getCurrentUser();
			
			if (valid(user)) {
				int source = user.number;
				Storage.cancelFriendRequest(source, target);
				return replySuccess(context);
			}
		} catch (Exception e) { }
		return replyError(context);
	}
	
	// GET /service-reject-friend-request?number=123456
	static Object rejectFriendRequest(Context context) {
		String targetString = context.getParameter("number");
		try {
			int target = Integer.parseInt(targetString);
			User user  = context.getCurrentUser();
			
			if (valid(user)) {
				int source = user.number;
				Storage.rejectFriendRequest(target, source);
				return replySuccess(context);
			}
		} catch (Exception e) { }
		return replyError(context);
	}
	
	// GET /service-accept-friend?number=123456
	static Object acceptFriend(Context context) {
		String targetString = context.getParameter("number");
		try {
			int target = Integer.parseInt(targetString);
			User user  = context.getCurrentUser();
			
			if (valid(user)) {
				int source = user.number;
				Storage.acceptFriend(target, source);
				return replySuccess(context);
			}
		} catch (Exception e) { }
		return replyError(context);
	}
	
	// GET /service-unfriend?number=123456
	static Object unfriend(Context context) {
		String targetString = context.getParameter("number");
		try {
			int target = Integer.parseInt(targetString);
			User user  = context.getCurrentUser();
			
			if (valid(user)) {
				int source = user.number;
				Storage.unfriend(source, target);
				return replySuccess(context);
			}
		} catch (Exception e) { }
		return replyError(context);
	}
	
	// GET /service-list-friend
	static Object listFriend(Context context) {
		User user  = context.getCurrentUser();
		if (valid(user)) {
			ArrayList<User> list = Storage.getMyFriend(user.number);
			
			JsonArrayBuilder builder = Json.createArrayBuilder();
			for (User u : list) {
				System.out.println(u);
				JsonObjectBuilder b = Json.createObjectBuilder();
				b.add("number",    u.number);
				b.add("firstName", u.firstName);
				b.add("lastName",  u.lastName);
				builder.add(b.build());
			}
			
			JsonObject result = Json.createObjectBuilder()
							.add("result", "OK")
							.add("list",   builder.build())
							.build();
			String text = result.toString();
			return (Object)context.sendJson(text);
		}
		return replyError(context);
	}
	
	// GET /service-list-friend-request
	static Object listFriendRequest(Context context) {
		User user  = context.getCurrentUser();
		if (valid(user)) {
			ArrayList<User> list = Storage.getMyFriendRequest
										(user.number);
			
			JsonArrayBuilder builder = Json.createArrayBuilder();
			for (User u : list) {
				System.out.println(u);
				JsonObjectBuilder b = Json.createObjectBuilder();
				b.add("number",    u.number);
				b.add("firstName", u.firstName);
				b.add("lastName",  u.lastName);
				builder.add(b.build());
			}
			
			JsonObject result = Json.createObjectBuilder()
							.add("result", "OK")
							.add("list",   builder.build())
							.build();
			String text = result.toString();
			return (Object)context.sendJson(text);
		}
		return replyError(context);
	}
	
	// GET /service-list-friend-request
	static Object listFriendRequestToMe(Context context) {
		User user  = context.getCurrentUser();
		if (valid(user)) {
			ArrayList<User> list = Storage.getFriendRequestToMe
										(user.number);
			
			JsonArrayBuilder builder = Json.createArrayBuilder();
			for (User u : list) {
				System.out.println(u);
				JsonObjectBuilder b = Json.createObjectBuilder();
				b.add("number",    u.number);
				b.add("firstName", u.firstName);
				b.add("lastName",  u.lastName);
				builder.add(b.build());
			}
			
			JsonObject result = Json.createObjectBuilder()
							.add("result", "OK")
							.add("list",   builder.build())
							.build();
			String text = result.toString();
			System.out.println(text);
			return (Object)context.sendJson(text);
		}
		return replyError(context);
	}
	
	static boolean valid(Object o) {
		return o != null;
	}
	
	
}