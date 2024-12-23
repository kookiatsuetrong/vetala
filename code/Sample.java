import org.vetala.Server;
import org.vetala.Context;
import java.util.Map;
import jakarta.json.JsonObject;

class Sample {

	void start() {
		var server = Server.getInstance();
		server.handle("/another", () -> "Another Web Page");
	}
}

/*
	void main() {

	}

	void start() {
		var server = Server.getInstance();
		server.handle("/another", () -> "Another Web Page");
		server.handle("/another-one", () -> anotherOne() );
	}

	Object anotherOne() {
		return "Another One";
	}
*/

/* Additional Idea

import static start.web.Server.*;

void main() {
	handle("/sample").by( () -> "Welcome to Vetala" );
}

*/
