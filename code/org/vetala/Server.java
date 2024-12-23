package org.vetala;

import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Set;
import java.util.TreeSet;
import java.util.Map;
import java.util.TreeMap;

import jakarta.servlet.ServletContext;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Enumeration;

import jakarta.json.Json;
import jakarta.json.JsonReader;
import jakarta.json.JsonObject;
import jakarta.json.JsonArray;
import java.io.BufferedReader;
import java.io.StringReader;
import java.lang.reflect.Constructor;
import java.util.Map;
import java.util.HashSet;
import java.util.HashMap;

// TODO: support /sample/:detail/:whatever
// TODO: support static import, to use handle() directly
// TODO: support live-reload for web service

@MultipartConfig
public class Server extends HttpServlet {

	public
	Server() {
		instance = this;
	}
	
	@Override public void
	init(ServletConfig sc) {
		try {
			super.init(sc);
			Enumeration<String> all = getInitParameterNames();
			while (all.hasMoreElements()) {
				String name = all.nextElement();
				if (name == null) continue;
				String value = getInitParameter(name);
				
				if (name.startsWith("controller")) {
					Method[] list = Class.forName(value)
										.getDeclaredMethods();
					for (Method method : list) {
						if ("start".equals(method.getName())) {
							Constructor co = Class.forName(value)
												.getDeclaredConstructor();
							co.setAccessible(true);
							Object instance = co.newInstance();
							
							method.setAccessible(true);
							method.invoke(instance);
						}
					}
				}
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	private static Server instance;
	
	public static Server
	getInstance() {
		return instance;
	}
	
	public void
	handle(String verb, String uri, SimpleHandler handler) {
		pathMap.put(verb + " " + uri, handler);
	}
	
	public void
	handle(String uri, SimpleHandler handler) {
		pathMap.put("GET " + uri, handler);
	}
	
	public void
	handle(String verb, String uri, ContextHandler handler) {
		pathMap.put(verb + " " + uri, handler);
	}
	
	public void
	handle(String uri, ContextHandler handler) {
		pathMap.put("GET " + uri, handler);
	}
	
	public void
	handle(String verb, String uri, MapHandler handler) {
		pathMap.put(verb + " " + uri, handler);
	}
	
	public void
	handle(String uri, MapHandler handler) {
		pathMap.put("GET " + uri, handler);
	}
	
	public void
	handle(String verb, String uri, JsonHandler handler) {
		pathMap.put(verb + " " + uri, handler);
	}
	
	public void
	handle(String uri, JsonHandler handler) {
		pathMap.put("GET " + uri, handler);
	}
	
	public HandleTarget
	handle(String ... items) {
		return new HandleTarget(items);
	}

	Map<String, Object> pathMap = new TreeMap<>();
	
	public void addStatic(String path) {
		direct.add(path);
	}
	
	Set<String> direct = new HashSet<String>();
	
	Object showError;
	
	public void 
	handleError(SimpleHandler handler) {
		showError = handler;
	}
	public void 
	handleError(ContextHandler handler) {
		showError = handler;
	}
	
	@Override public void 
	service(
		HttpServletRequest request,
		HttpServletResponse response) {
		
		// For this request:
		// http://localhost:7300/search?query=sample&location=New+York
		// URI   is "/search"
		// Query is "query=sample&location=New+York"
		// All queries can be accessed from request.getParameter()
		
		String method = request.getMethod();        // Use this
		String query  = request.getQueryString();   // Use this
		String path   = request.getContextPath();
		String uri    = request.getRequestURI();    // Use this
		String url    = request.getRequestURL().toString();

		System.out.println("Method: " + method);
		System.out.println("Query:  " + query);
		System.out.println("Path:   " + path);
		System.out.println("URI:    " + uri);
		System.out.println("URL:    " + url);
		System.out.println();

		response.setCharacterEncoding("UTF-8");

		ServletContext servletContext = getServletContext();
		String staticPath = servletContext.getRealPath(uri);
		File staticFile = new File(staticPath);
		boolean found = staticFile.exists();
		
		/*
		if (found == true) {
			try {
				request.getRequestDispatcher(uri)
						.forward(request, response);
			} catch (Exception e) { }
			return;
		} */
		
		if (found == true) {
			try {
				servletContext.getNamedDispatcher("default")
						.forward(request, response);
			} catch (Exception e) { }
			return;
		}

		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (Exception e) { }
		
		String key = method + " " + uri;
		Object handler = pathMap.get(key);
		
		Object result = null;
		
		if (handler == null && showError == null) {
			result = "Not Found";
		}
		if (handler == null && showError instanceof SimpleHandler) {
			result = ((SimpleHandler)showError).handle();
		}
		
		if (handler == null && showError instanceof ContextHandler) {
			Context context  = new Context();
			context.request  = request;
			context.response = response;
			result = ((ContextHandler)showError).handle(context);
		}
		
		// TODO: Add MapHandler error message
		// TODO: Add JsonHandler error message
		
		if (handler instanceof SimpleHandler) {
			result = ((SimpleHandler)handler).handle();
		}
		
		if (handler instanceof ContextHandler) {
			Context context  = new Context();
			context.request  = request;
			context.response = response;
			result = ((ContextHandler)handler).handle(context);
		}
		
		if (handler instanceof MapHandler) {
			Map<String,String[]> m = request.getParameterMap();
			result = ((MapHandler)handler).handle(m);
		}
		
		if (handler instanceof JsonHandler) {
			JsonObject detail = null;
			String buffer = "";
			try {
				BufferedReader br = request.getReader();
				while (true) {
					String line = br.readLine();
					if (line == null) break;
					buffer += line;
				}
				StringReader sr = new StringReader(buffer);
				JsonReader reader = Json.createReader(sr);
				detail = reader.readObject();
			} catch (Exception e) { }
			result = ((JsonHandler)handler).handle(detail);
		}
		
		if (result == null) {
			return;
		}

		if (result instanceof String && 
			"Error".equals(result)) {
			return;
		}
			
		if (result instanceof String && 
			"Redirect".equals(result)) {
			return;
		}
		
		if (result instanceof String && 
			"Forwarded".equals(result)) {
			return;
		}

		if (result instanceof String) {
			response.setContentType("text/html");
			out.println(result);
			return;
		}

		if (result instanceof JsonArray) {
			response.setContentType("application/json");
			out.println(result);
			return;
		}

		if (result instanceof JsonObject) {
			response.setContentType("application/json");
			out.println(result);
			return;
		}

		if (result instanceof Object[]) {
			out.println("An instance of Array");
			return;
		}

		out.println( result.toString() );
	}
}
