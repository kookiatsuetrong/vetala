import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;

public class SampleHandler extends UnicastRemoteObject implements Handler {

	public SampleHandler() throws RemoteException { 
		super();
	}

	@Override
	public String find(String s) throws RemoteException {
		return "I've got " + s;
	}
}
