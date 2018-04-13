
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class DBServerLocator {

	public static DBServer getDBServer() throws RemoteException {
		Registry registry = LocateRegistry.getRegistry(DBServer.PORT);
		try {
			return (DBServer) registry.lookup(DBServer.SERVER_NAME);
		} catch (NotBoundException e) {
			throw new RemoteException(e.getMessage(), e);
		}
	}

}
