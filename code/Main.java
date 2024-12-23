import org.vetala.Server;
import org.vetala.Context;
import java.util.Map;
import java.rmi.Naming;

class Main {

	void start() {
		var server = Server.getInstance();		
		server.handle("/find").by(Main::find);
		
	}
	
	static Object find(Context context) {
		try {
			Handler w = (Handler)Naming
						.lookup("rmi://localhost:4700/sample");
			String result = w.find("Welcome to RMI");
			return result;
		} catch (Exception e) {
			System.out.println(e);
			return "RMI Error";
		}
	}
}

