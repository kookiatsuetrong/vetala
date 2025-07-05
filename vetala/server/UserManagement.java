package server;
import java.util.Map;
import java.util.HashMap;

public class UserManagement {

	public static synchronized void 
	increaseSession(int userNumber) {
		Integer current = sessionMap.get(userNumber);
		if (current == null) {
			sessionMap.put(userNumber, 1);
			return;
		}
		int w = current;
		sessionMap.put(userNumber, w + 1);
	}
	
	public static synchronized void 
	decreaseSession(int userNumber) {
		Integer current = sessionMap.get(userNumber);
		if (current == null) {
			return;
		}
		
		int w = current;
		w--;
		if (w == 0) {
			sessionMap.remove(userNumber);
			return;
		}
		
		sessionMap.put(userNumber, w);
	}
	
	public String getUserStatus(int userNumber) {
		Integer current = sessionMap.get(userNumber);
		if (current == null) {
			return "Offline";
		}
		
		int w = current;
		if (w == 0) {
			return "Offline";
		}
		
		return "Online";
	}
	
	static Map<Integer, Integer>       sessionMap        = new HashMap<>();
	static Map<String, String>         socketToCookieMap = new HashMap<>();
	public static Map<String, Integer> cookieToUserMap   = new HashMap<>();
	static Map<String, Integer>        socketToUserMap   = new HashMap<>();
	
}
