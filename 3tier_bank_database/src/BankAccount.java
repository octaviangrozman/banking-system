import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.util.List;


public interface BankAccount {
	String TABLE_NAME = "Account";
	String COLUMN_ACCOUNT_AMOUNT ="amount";
	String COLUMN_ACCOUNT_NUMBER = "number";
	String COLUMN_ACCOUNT_CUSTOMERCPR = "customercpr";
	String COLUMN_ACCOUNT_CURRENCY = "currency";

	boolean deposit(int accountNr, BigDecimal amount) throws RemoteException;
	boolean withdraw(int accountNr, BigDecimal amount) throws RemoteException;
	boolean transfer(int sourceAccountNr, int destinationAccountNr, BigDecimal amount) throws RemoteException;

	List getAllAccounts(String customerCPR) throws RemoteException;
	Account getAccount(int accountNumber) throws RemoteException;
}
