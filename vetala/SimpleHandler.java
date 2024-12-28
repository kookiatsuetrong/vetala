import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;

class Start {
	public static void main(String[] data) {
		try {
			SimpleHandler s = new SimpleHandler();
			Naming.rebind("rmi://localhost:4700/sample", s);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}

class SimpleHandler extends UnicastRemoteObject implements Handler {

	public SimpleHandler() throws RemoteException { 
		super();
	}
	
	@Override
	public boolean isSupported(String s) throws RemoteException {
		return true;
	}

	@Override
	public String call(String path, Map<String,String[]> map) 
		throws RemoteException {
		return "The request is " + path;
	}
}

