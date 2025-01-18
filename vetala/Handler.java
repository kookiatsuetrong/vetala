/**
 * The external Vetala handler
 * 
 */
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;

public interface Handler extends Remote {

	String call(String pattern, Map<String,String[]> map) 
		throws RemoteException;
		
	// boolean isSupported(String pattern) throws RemoteException;
	
}

