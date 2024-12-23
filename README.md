## Vetala

Vetala is a non-stop Java web service and web application.


```
From HTTP Request:

GET  /login -------->
POST /search ------->   /
GET  / ------------->   '
                        '
                        '
                        '---> Vetala Engine
                              '
                              '
                              ' RMI
                              '
                              '---> Vetala Handler
```

Starting Vetala Engine
```
bash build.sh
```

Writing Vetala Handler
```java
import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

class Start {
	public static void main(String[] data) {
		try {
			SampleHandler s = new SampleHandler();
			Naming.rebind("rmi://localhost:4700/sample", s);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}

interface Handler extends Remote {
	String find(String s) throws RemoteException;
}

class SampleHandler extends UnicastRemoteObject implements Handler {

	public SampleHandler() throws RemoteException { 
		super();
	}

	@Override
	public String find(String s) throws RemoteException {
		return "The message is " + s;
	}
}

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





