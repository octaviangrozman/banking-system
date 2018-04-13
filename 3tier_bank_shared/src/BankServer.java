import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;


public interface BankServer extends Remote {

	String SERVER_NAME = "bankServer";
	int PORT = 1099;

	boolean performTransaction(
			Money money,
            String transactionType,
			Account sourceAccount, 
			Account destinationAccount
		) throws RemoteException;

	Account getAccount(int accountNumber) throws RemoteException;
    List getAllAccounts(String customerCPR) throws RemoteException;

	List getAllTransactions(String customerCPR) throws RemoteException;

	Customer getCustomer(String customerCPR) throws RemoteException;
	List getAllCustomers() throws RemoteException;

	ExchangeRates getExchangeRates() throws RemoteException;
}
