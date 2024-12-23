package org.vetala;

import java.util.ArrayList;

public class HandleTarget {
	public HandleTarget(String ... items) {
		for (String s : items) {
			list.add(s);
		}
	}
	
	String method = "GET";
	ArrayList<String> list = new ArrayList<>();
	
	public HandleTarget via(String m) {
		method = m;
		return this;
	}
	
	public void by(SimpleHandler handler) {
		Server server = Server.getInstance();
		for (String s : list) {
			server.handle(method, s, handler);
		}
	}
	
	public void by(ContextHandler handler) {
		Server server = Server.getInstance();
		for (String s : list) {
			server.handle(method, s, handler);
		}
	}
	
	public void by(MapHandler handler) {
		Server server = Server.getInstance();
		for (String s : list) {
			server.handle(method, s, handler);
		}
	}
	
	public void by(JsonHandler handler) {
		Server server = Server.getInstance();
		for (String s : list) {
			server.handle(method, s, handler);
		}
	}
	
}
