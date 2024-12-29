import java.rmi.Naming;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;

class Start {
	public static void main(String[] data) throws Exception {
		SimpleHandler handler = new SimpleHandler();
		Naming.rebind("rmi://localhost:4700/sample", handler);
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
			case "GET /"       -> result = "The Home";
			case "GET /test"   -> result = "The Test Application";
			case "GET /search" -> result = "The Search Application";
		}
		map.put("hello", new String[] {"world"});
		printMap(map);
		return result;
	}
	
	void printMap(Map<String,String[]> map) {
		for (String key : map.keySet()) {
			System.out.print(key + " -> ");
			String[] items = map.get(key);
			for (String s : items) {
				System.out.print(s + " ");
			}
			System.out.println();
		}
		System.out.println();	
	}
}

