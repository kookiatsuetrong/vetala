package server;

import java.util.Map;
import java.util.List;
import jakarta.websocket.HandshakeResponse;
import jakarta.websocket.server.HandshakeRequest;
import jakarta.websocket.server.ServerEndpointConfig;

public class SocketConfigurator extends
	ServerEndpointConfig.Configurator {
	
	@Override
	public void modifyHandshake(
				ServerEndpointConfig configure,
				HandshakeRequest request,
				HandshakeResponse response) {
		
		Map<String, List<String>> headers = 
							request.getHeaders();
		
		// System.out.println(headers);
		String card = null;
		List<String> cookies = headers.get("cookie");
		for (String c : cookies) {
			String[] items = c.split(";");
			for (String t : items) {
				t = t.trim();
				if (t.startsWith("CARD")) {
					String[] tokens = t.split("=");
					if (tokens.length == 2) {
						card = tokens[1];
					}
				}
			}
		}
		
		if (valid(card)) { 
			configure.getUserProperties().put("CARD", card);
		}
	}
	
	boolean valid(Object o) {
		return o != null;
	}
}
