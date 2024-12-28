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
			LocateRegistry.createRegistry(4700);
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
			Map<String,String[]> map = request.getParameterMap();
			
			System.out.println(verb + " " + uri);
			System.out.println("Map:");
			for (String key : map.keySet()) {
				System.out.print(key + " -> ");
				String[] items = map.get(key);
				for (String s : items) {
					System.out.print(s + " ");
				}
				System.out.println();
			}
			System.out.println();
			
			// 2. Extract user detail
			
			// 3. Check the handler
			Handler h = (Handler)Naming
						.lookup("rmi://localhost:4700/sample");
			
			String result = h.call(verb + " " + uri, null);
			
			var out = response.getWriter();
			out.println("The Main Servlet " + result);
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

String              getMethod()
String              getRequestURI()
StringBuffer        getRequestURL()
Collection<Part>    getParts()

HttpSession         getSession()
HttpSession         getSession(boolean create)

Cookie[]            getCookies()

Enumeration<String> getHeaderNames()
Enumeration<String> getHeaders(String s)

*/











