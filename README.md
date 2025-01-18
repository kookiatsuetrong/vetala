## Vetala

Vetala is a non-stop web server for Java application.

![](vetala-screen.png)

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
'
'                                     -------- EXECUTABLE ASSET -------- 
'
'
'-- servlet.xml                  Java Web Deployment Descriptor
'-- web                          Java Web Application
'   '-- index.jsp                JavaServer Pages
'   '-- normalize.css            Public Files
'   '-- main.css
'   '-- photo.jpg
'   '-- favicon.ico
'   '
'   '-- WEB-INF                  View (Access from Servlet)
'   '   '-- user-register.jsp
'   '   '-- user-profile.jsp
'   '   '
'   '   '-- module-1             Additional Module
'   '       '-- module.txt
'   '       '-- index.jsp
'   '-- META-INF
'   '   '-- module-1             Additional Module
'   '       '-- module.txt
'   '       '-- index.jsp
'   '-- module-2
'       '-- module.txt
'       '-- index.jsp
'
'-- make.sh                      Make file for Unix
'-- make.bat                     Make file for Windows
'-- schema.sql                   Database Schema
'
'-- runtime
'   '-- embedded-tomcat.jar
'   '-- jdbc.jar
'   '-- xxx.class
'   '-- yyy.class
'   '-- server
'       '-- zzz.class
'
'                                     -------- SOURCE CODE ASSET -------
'
'-- vetala                       Internal Engine
    '-- Vetala.java              Embedded Tomcat
    '-- MainServlet.java         The Main Servlet
    '-- Context.java             Combination of Request and Response 
    '-- ErrorMessage.java
    '-- Tool.java
    '
    '-- server                   Shared Code with JSP
    '   '-- Storage.java         Database Access Layer
    '   '-- User.java
    '   '-- Email.java
    '   '-- EmailSender.java
    '
    '-- Handler.java             Template for RMI Handler
    '-- SimpleHandler.java

```

Common configuration (setup.txt) file.

```
connectionString = jdbc:mysql://127.0.0.1/sample?user=me&password=password

rmiEnabled    = true

emailEnabled  = true
emailPassword = TheEmailPassword
emailAddress  = noreply@server.com
emailServer   = smtp.server.com
emailSender   = The System Administrator
emailPort     = 587
emailSecurity = TLSv1.2
```

Creating Module

```
'
'-- vetala
    '
    '-- web
        '-- index.jsp
        '
        '-- WEB-INF
            '-- whatever.jsp
            '-- user-profile.jsp -------------.
            '                                 '
            '-- classes                       '
            '   '-- whatever                  '
            '-- lib                           '
            '   '-- driver.jar                '
            '                                 '
            '-- module-1                  <---'
                '-- module.txt    (Mandatory)
                '-- index.jsp     (Mandatory)
                '-- another.jsp   (Optional)

Type of module:
- administrator
- staff
- user
```


RMI Handler for non-stop Java application

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
For visitor:

 GET /user-check-email
POST /user-check-email

 GET /user-register
POST /user-register

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


For user:

 GET /user-profile
 GET /user-password
POST /user-password
 GET /user-logout


For administrator

 GET /user-management


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
	I want to copy/paste a photo in the contact page.

V02: As a visitor, 
	I want to create account from my social network account 
	including Facebook or Google.

A01: As an administrator,
	I want to enable/disable RMI handler.

A02: As an administrator,
	I want to force the system to reload configuration.

A03: As a developer, 
        I want to write a module.

```


