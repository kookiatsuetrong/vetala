/**
 * Vetala is a non-stop Java web server
 *
 * Usage:
 *
 * java Vetala
 * java Vetala --home web --port 7300
 *
 */
import java.io.File;

import org.apache.catalina.Server;
import org.apache.catalina.Service;
import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.servlets.DefaultServlet;

import java.util.logging.LogManager;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;

class Vetala {

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
			
			File file = new File(home);
			var context1 = tomcat.addWebapp("", file.getAbsolutePath());
			context1.setAltDDName("servlet.xml");
			
			// var context2 = tomcat.addContext("/external", null);
			// tomcat.addServlet("", "m-servlet", new MainServlet());
			// context1.addServletMappingDecoded("/", "m-servlet");
			
			var connector = tomcat.getConnector(); // Mandatory
			tomcat.start();
			tomcat.getServer().await();
			
		} catch (Exception e) {
			System.out.println("ERROR " + e);
		}
	}
}


