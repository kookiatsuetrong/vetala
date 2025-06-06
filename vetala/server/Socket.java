package server;

import jakarta.websocket.OnOpen;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.Session;
import jakarta.websocket.OnMessage;
import jakarta.websocket.server.ServerEndpoint;

@ServerEndpoint(
	value = "/ws",
	configurator = SocketConfigurator.class
)
public class Socket {
	@OnOpen
	public void handleOpen(Session session) {
		try {
			System.out.println(session);
		} catch (Exception e) { }
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
	
	@OnClose
	public void handleClose(Session session) {
		try {
			
		} catch (Exception e) { }
	}
	
	@OnError
	public void handleError(Session session,
		Throwable throwable) {
	}
}
