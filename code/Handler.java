import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Handler extends Remote {
	String find(String s) throws RemoteException;
}
