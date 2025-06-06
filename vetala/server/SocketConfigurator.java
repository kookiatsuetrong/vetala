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
		System.out.println(headers);
		
		System.out.println();
		List<String> cookies = headers.get("cookie");
		for (String s : cookies) {
			System.out.println(s);
		}
	}
}
