import java.rmi.registry.LocateRegistry;
import java.rmi.Naming;
public class Broker {
	public static void main(String[] data) {
		try {
			LocateRegistry.createRegistry(4700);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
