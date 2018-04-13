import java.math.BigDecimal;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;


public interface DBServer extends Remote {

	String SERVER_NAME = "dbServer";
	int PORT = 1099;
	String DB_URL = "jdbc:postgresql://localhost:5432/bank";
	String DB_USER = "postgres";
	String DB_PASS = "qwerty123";

	boolean deposit(int accountNr, BigDecimal amount) throws RemoteException;
	boolean withdraw(int accountNr, BigDecimal amount) throws RemoteException;
	boolean transfer(int sourceAccountNr, int destinationAccountNr, BigDecimal amount) throws RemoteException;

	Account getAccount(int accountNumber) throws RemoteException;
	List getAllAccounts(String customerCPR) throws RemoteException;

	List getAllTransactions(String customerCPR) throws RemoteException;

	// customerDAO
	boolean createCustomer(Customer customer) throws RemoteException;
	Customer getCustomer(String customerCPR) throws RemoteException;
	List getAllCustomers() throws RemoteException;

	// exchangeRateDAO
	boolean addExchangeRate(ExchangeRate exchangeRate) throws RemoteException;
	ExchangeRates getExchangeRates() throws RemoteException;
}
