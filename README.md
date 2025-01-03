## Vetala

Vetala is a non-stop web server for Java application.

Starting Vetala Engine
```
git clone https://github.com/kookiatsuetrong/vetala
cd vetala
sudo mysql < schema.sql
bash make.sh
```
And open web browser to http://localhost:12345


Directory structures
```
vetala
'-- servlet.xml                  Java Web Deployment Descriptor
'-- web                          Ordinary Java Web Application
'   '-- index.jsp                JavaServer Pages
'   '-- normalize.css
'   '-- main.css
'   '-- photo.jpg
'   '-- favicon.ico
'   '
'   '-- WEB-INF                  View (Access from Servlet)
'       '-- user-register.jsp
'
'-- make.sh                      Make for Unix
'-- make.bat                     Make for Windows
'-- schema.sql                   Database Schema
'
'-- vetala                       Internal Engine
'   '-- Vetala.java              Embedded Tomcat
'   '-- MainServlet.java         The Main Servlet
'   '-- Context.java             Java Request / Response
'   '-- ErrorMessage.java
'   '-- Storage.java             Database Access Layer
'   '-- Tool.java
'   '
'   '-- server                   Shared Code with View
'   '   '-- User.java
'   '   '-- Email.java
'   '   '-- EmailSender.java
'   '
'   '-- Handler.java             Template for RMI Handler
'   '-- SimpleHandler.java
'
'-- runtime
    '-- embedded-tomcat.jar
    '-- jdbc.jar
    '-- xxx.class
    '-- yyy.class
```


```
From HTTP Request:

GET  /login -------->
POST /search ------->   /
GET  / ------------->   '
                        '
                        '
                        '---> Vetala Engine
                              '
                              '---> Internal
                              '     '-- Servlet
                              '     '-- JavaServer Pages
                              '     '-- Static Files
                              '     '-- User Management
                              '
                              '---> External (RMI)
                                    (Vetala Handler)

```

From the definition of Handler interface
```java
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;
public interface Handler extends Remote {

	String call(String pattern, Map<String,String[]> map) 
		throws RemoteException;

}
```

Writing Vetala Handler
```java
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;

class Start {

	public static final int rmiPort = 4700;
	public static final String rmiRegistry = 
				"rmi://127.0.0.1:" + rmiPort + "/handler";

	public static void main(String[] data) throws Exception {
		String[] list = Naming.list("rmi://127.0.0.1:4700");
		for (String s : list) {
			System.out.println(s);
		}
	
		SimpleHandler handler = new SimpleHandler();
		Naming.rebind(rmiRegistry, handler);
	}
}

class SimpleHandler extends UnicastRemoteObject implements Handler {

	public SimpleHandler() throws RemoteException { 
		super();
	}

	@Override
	public String call(String path, Map<String,String[]> map) 
		throws RemoteException {
		String result = "Not Found";
		switch (path) {
			case "GET /external"        -> result = "The Home";
			case "GET /external/test"   -> result = "The Test App";
			case "GET /external/search" -> result = "The Search App";
		}
		return result;
	}
}
```

Common web application endpoints

```
 GET /user-check-email
POST /user-check-email

 GET /user-register
POST /user-register

 GET /user-profile
 GET /user-logout

 GET /user-login
POST /user-login

 GET /reset-password
POST /reset-password

 GET /reset-password-code
POST /reset-password-code
 GET /reset-password-final

 GET /contact
POST /contact
 GET /contact-final

 GET /error

```


java.rmi.Naming

```
static void 	bind(String name, Remote obj)
	Binds the specified name to a remote object.
	
static String[] 	list(String name)
	Returns an array of the names bound in the registry.
	
static Remote 	lookup(String name)
	Returns a reference, a stub, for the remote object 
	associated with the specified name.
	
static void 	rebind(String name, Remote obj)
	Rebinds the specified name to a new remote object.
	
static void 	unbind(String name)
	Destroys the binding for the specified name that is 
	associated with a remote object.

```

java.rmi.registry.LocateRegistry

```
static Registry 	createRegistry(int port)
	Creates and exports a Registry instance on 
	the local host that accepts requests on 
	the specified port.
	
static Registry 	getRegistry()
	Returns a reference to the the remote 
	object Registry for the local host on 
	the default registry port of 1099.
	
static Registry 	getRegistry(int port)
	Returns a reference to the the remote 
	object Registry for the local host on 
	the specified port.

static Registry 	getRegistry(String host)
	Returns a reference to the remote object 
	Registry on the specified host on the 
	default registry port of 1099.

static Registry 	getRegistry(String host, int port)
	Returns a reference to the remote object 
	Registry on the specified host and port.

```

Project backlogs

```
M01: As a member, 
	I want to have my profile picture.

V01: As a visitor, 
	I want to copy/paste my photo in the contact page.

V02: As a visitor, 
	I want to create account from my social network account 
	including Facebook or Google.

A01: As an administrator,
	I want to configure connection string.
```


