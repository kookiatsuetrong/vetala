package server;

import jakarta.websocket.OnOpen;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.Session;
import jakarta.websocket.OnMessage;
import jakarta.websocket.EndpointConfig;
import jakarta.websocket.server.ServerEndpoint;
import java.util.HashMap;
import java.util.Map;

@ServerEndpoint(
	value = "/ws",
	configurator = SocketConfigurator.class
)
public class Socket {
	
	static Map<String, Session> map = new HashMap<>(); 
	
	@OnOpen
	public void handleOpen(Session session, 
							EndpointConfig configure) {
		try {
			
			session.setMaxIdleTimeout(24 * 60 * 60 * 100);
			
			String socketNumber = session.getId();
			map.put(socketNumber, session);
			
			System.out.println("WebSocket Open Session = " + socketNumber);
			
			String card = (String)configure.getUserProperties().get("CARD");
			System.out.println("WebSocket Cookie = " + card);
			
			Integer userInstance = UserManagement.cookieToUserMap.get(card);
			if (valid(userInstance)) {
				int userNumber = userInstance;
				UserManagement.increaseSession(userNumber);
				UserManagement.socketToUserMap.put(socketNumber, userNumber);
				System.out.println("WebSocket User number " + userNumber);
			}

			UserManagement.socketToCookieMap.put(socketNumber, card);
			
			showAllOnlineUsers();
		} catch (Exception e) { }
	}
	
	@OnClose
	public void handleClose(Session session) {
		try {
			String socketNumber = session.getId();
			map.remove(socketNumber);
			
			System.out.println("WebSocket Close Session = " + socketNumber);
			
			String card = UserManagement.socketToCookieMap.get(socketNumber);
			System.out.println("WebSocket Cookie = " + card);
			UserManagement.socketToCookieMap.remove(socketNumber);
			
			Integer userInstance = UserManagement
									.socketToUserMap
									.get(socketNumber);
			if (valid(userInstance)) {
				int userNumber = userInstance;
				UserManagement.decreaseSession(userNumber);
				System.out.println("WebSocket User number " + userNumber);
			}
			
			showAllOnlineUsers();
		} catch (Exception e) { }
	}
	
	void showAllOnlineUsers() {
		System.out.println("WebSocket Online: " + map.keySet());
		for (String s : map.keySet()) {
			String  card = UserManagement.socketToCookieMap.get(s);
			Integer user = UserManagement.socketToUserMap.get(s);
			System.out.println("Web Socket User " +
					s + " " + user + " " + card);
		}
	}
	
	boolean valid(Object o) {
		return o != null;
	}
	
	@OnMessage
	public void handleMessage(Session session,
								String message) {
		try {
			System.out.println("WebSocket Message from Session " + 
								session.getId());
			System.out.println("WebSocket Message: " + message);
			session.getAsyncRemote().sendText("Hello");
		} catch (Exception e) { }
	}
	
	@OnError
	public void handleError(Session session,
		Throwable throwable) {
	}
	
}
