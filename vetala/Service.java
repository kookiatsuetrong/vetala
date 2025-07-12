import jakarta.json.Json;
import jakarta.json.JsonObject;
import server.UserManagement;

class Service {
	static Object replyError(Context context) {
		JsonObject o = Json.createObjectBuilder()
						.add("result", "Error")
						.build();
		
		return (Object)context.sendJson(o.toString());
	}
	/*
	*  Get User Status
	*
	*  GET /service-user-status
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
}