import java.rmi.Naming;
public class HandlerManager {
	public static void main(String[] data) {
		try {
			SampleHandler s = new SampleHandler();
			Naming.rebind("rmi://localhost:4700/sample", s);
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}

