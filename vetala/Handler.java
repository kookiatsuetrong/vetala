import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Map;
public interface Handler extends Remote {
	boolean isSupported(String pattern) throws RemoteException;
	String call(String pattern, Map<String,String[]> map) throws RemoteException;
}

