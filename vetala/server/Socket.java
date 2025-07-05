package server;

import jakarta.websocket.OnOpen;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.Session;
import jakarta.websocket.OnMessage;
import jakarta.websocket.EndpointConfig;
import jakarta.websocket.server.ServerEndpoint;

@ServerEndpoint(
	value = "/ws",
	configurator = SocketConfigurator.class
)
public class Socket {
	@OnOpen
	public void handleOpen(Session session, 
							EndpointConfig configure) {
		try {
			System.out.println(session);
			System.out.println("Open Session Number  = " + session.getId());
			String card = (String)configure.getUserProperties().get("CARD");
			System.out.println("CARD  = " + card);
			
			String socketNumber = session.getId();
			Integer userInstance = UserManagement.cookieToUserMap.get(card);
			if (valid(userInstance)) {
				int userNumber = userInstance;
				UserManagement.increaseSession(userNumber);
				UserManagement.socketToUserMap.put(socketNumber, userNumber);
			}
			
			UserManagement.socketToCookieMap.put(socketNumber, card);
		} catch (Exception e) { }
	}
	
	@OnClose
	public void handleClose(Session session) {
		try {
			System.out.println("Close Session Number = " + session.getId());
			
			String socketNumber = session.getId();
			UserManagement.socketToCookieMap.remove(socketNumber);
			
			Integer userInstance = UserManagement
									.socketToUserMap
									.get(socketNumber);
			if (valid(userInstance)) {
				int userNumber = userInstance;
				UserManagement.decreaseSession(userNumber);
			}
		} catch (Exception e) { }
	}
	
	boolean valid(Object o) {
		return o != null;
	}
	
	@OnMessage
	public void handleMessage(Session session,
								String message) {
		try {
			System.out.println(session.getId());
			System.out.println("Client Message: " + message);
			session.getAsyncRemote().sendText("Hello");
		} catch (Exception e) { }
	}
	
	@OnError
	public void handleError(Session session,
		Throwable throwable) {
	}
	
}
