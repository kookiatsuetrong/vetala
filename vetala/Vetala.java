/**
 * Vetala is a non-stop Java web server
 *
 * Usage:
 *
 * java -classpath "runtime/*" Vetala \
 *      --home web --port 7300
 *
 */
import java.io.File;

import org.apache.catalina.Server;
import org.apache.catalina.Service;
import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.connector.Connector;

import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.logging.LogManager;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

import java.io.PrintWriter;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class Vetala {

	public static final int rmiPort = 4700;
	public static final String rmiRegistry = 
				"rmi://127.0.0.1:" + rmiPort + "/handler";

	public static void main(String[] data) {
		var port = 12345;
		var home = "web";
		var working = "working";
		var verbose = false;

		for (var i = 0; i < data.length; i++) {
			if ("--port".equals(data[i])) {
				i++;
				port = Integer.parseInt(data[i]);
			}
			if ("--home".equals(data[i])) {
				i++;
				home = data[i];
			}
			if ("--verbose".equals(data[i])) {
				verbose = true;
			}
		}

		// LogManager.getLogManager().reset();
			
		try {
			LocateRegistry.createRegistry(rmiPort);
		} catch (Exception e) {
			System.out.println(e);
		}

		try {
			var tomcat = new Tomcat();
			tomcat.setPort(port);
			tomcat.setBaseDir(working);
			tomcat.setSilent(!verbose);
			
			var connector = tomcat.getConnector(); // Mandatory
			
			var context = tomcat.addContext("", null);
			Tomcat.addServlet(context, "main", new MainServlet());
			context.addServletMappingDecoded("/", "main");
			
			tomcat.start();
			tomcat.getServer().await();
		} catch (Exception e) {
			System.out.println("ERROR " + e);
		}
	}
}

class MainServlet extends HttpServlet {
	
	@Override
	public void service(HttpServletRequest request,
						HttpServletResponse response) {
		try {
			// 1. Create input map
			String verb = request.getMethod();
			String uri  = request.getRequestURI();
			String pattern = verb + " " + uri;
			System.out.println(pattern);
			
			String[] paths = uri.trim().split("/");
			if (paths.length > 1 && 
				"manager".equals(paths[1])) {
				managerService(request, response);
				return;
			}
			
			Map<String,String[]> rawMap = request.getParameterMap();
			Map<String,String[]> map = new TreeMap<>();
			for (String key : rawMap.keySet()) {
				String[] items = rawMap.get(key);
				map.put(key, items);
			}
			printMap(map);
			
			// 2. Extract user detail
			
			// 3. Call the handler
			Handler remote = (Handler)Naming.lookup(Vetala.rmiRegistry);
			
			String result = remote.call(pattern, map);
			printMap(map);
			
			var out = response.getWriter();
			out.println("The Main Servlet " + result);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	void printMap(Map<String,String[]> map) {
		for (String key : map.keySet()) {
			System.out.print(key + " -> ");
			String[] items = map.get(key);
			for (String s : items) {
				System.out.print(s + " ");
			}
			System.out.println();
		}
		System.out.println();	
	}
	
	void managerService(HttpServletRequest request,
						HttpServletResponse response) {
		try {
			var out = response.getWriter();
			out.println("The Manager");
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}

/*

ServletRequest

startAsync()
startAsync(request, response)

getAsyncContext()
getDispatcherType()

String                getContentType()
String                getParameter()
Map<String,String[]>  getParameterMap()
String                getProtocol()
String                getScheme()

HttpServletRequest

String                getMethod()
String                getRequestURI()
StringBuffer          getRequestURL()
Collection<Part>      getParts()

HttpSession           getSession()
HttpSession           getSession(boolean create)

Cookie[]              getCookies()

Enumeration<String>   getHeaderNames()
Enumeration<String>   getHeaders(String s)


ServletContext.getRequestDispatcher(String s)
ServletContext.getNamedDispatcher(String name)
ServletRequest.getRequestDispatcher(String s)


/favicon.ico


*/











