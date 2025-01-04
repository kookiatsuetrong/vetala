package server;
import java.io.FileReader;

public class Setup {
	public static boolean emailEnabled = false;
	public static String emailSender   = "";
	public static String emailSecurity = "";
	public static String emailServer   = "";
	public static String emailPort     = "";
	public static String emailAddress  = "";
	public static String emailPassword = "";
	
	public static String connectionString = "";
	
	static {
		reload();
	}
	
	public static void reload() {
		System.out.println("Reloading configuration");
		String buffer = "";
		try (FileReader fr = new FileReader("setup.txt")) {
			while (true) {
				int k = fr.read();
				if (k == -1) break;
				buffer += (char)k;
			}
		} catch (Exception e) { }
		
		String [] lines = buffer.split("\n");
		for (String s : lines) {
			String [] list = s.trim().split("=");
			if (list.length < 2) continue;
			for (int i = 0; i < list.length; i++) {
				if (list[i] == null) {
					list[i] = "";
				}
				list[i] = list[i].trim();
			}
			String value = "";
			for (int i = 1; i < list.length - 1; i++) {
				value += list[i] + "=";
			}
			value += list[list.length - 1];

			switch (list[0]) {
				case "emailEnabled"  -> emailEnabled  = "true".equals(value);
				case "emailAddress"  -> emailAddress  = value;
				case "emailPassword" -> emailPassword = value;
				case "emailServer"   -> emailServer   = value;
				case "emailSender"   -> emailSender   = value;
				case "emailPort"     -> emailPort     = value;
				case "emailSecurity" -> emailSecurity = value;
				case "connectionString" -> connectionString = value;
			}
		}
	}
	
}
