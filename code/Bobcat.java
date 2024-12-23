/**
 * Bobcat is an embedded version of Tomcat.
 *
 * Example of usage:
 *
 * java Bobcat --home web --port 7300
 *
 */
import java.io.File;

import java.util.logging.Logger;
import java.util.logging.Level;
import java.util.logging.LogManager;

import java.rmi.registry.LocateRegistry;

import org.apache.catalina.Server;
import org.apache.catalina.Service;
import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.connector.Connector;

public class Bobcat {
	public static void main(String[] data) {
		int port = 12345;
		String home = "web";
		String working = "working";
		boolean verbose = false;

		for (int i = 0; i < data.length; i++) {
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

		LogManager.getLogManager().reset();
			
		try {
			LocateRegistry.createRegistry(4700);
		} catch (Exception e) {
			System.out.println(e);
		}

		try {
			Tomcat tomcat = new Tomcat();
			tomcat.setPort(port);
			tomcat.setBaseDir(working);
			tomcat.setSilent(!verbose);
			tomcat.setAddDefaultWebXmlToWebapp(true);
			Connector connector = tomcat.getConnector();   // Mandatory
			File file = new File(home);
			Context context = tomcat.addWebapp("", file.getAbsolutePath());
			// context.setAltDDName(file.getAbsolutePath() + "/../web.xml");

			tomcat.start();
			tomcat.getServer().await();
		} catch (Exception e) {
			System.out.println("ERROR " + e);
		}

		/*
		try {
			tomcat.stop();
			tomcat.destroy();
			// remove the temporary working directory
		} catch (Exception e) { }
		*/
		
	}
}

// --port 12345
// --home web
// --war sample.war
