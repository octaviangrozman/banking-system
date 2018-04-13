import java.rmi.RemoteException;
import java.util.List;

public interface BankTransaction {
	String TABLE_NAME = "Transaction";
	String COLUMN_TRANSACTION_DATE = "date";
	String COLUMN_TRANSACTION_TYPE = "type";
	String COLUMN_TRANSACTION_AMOUNT = "amount";
	String COLUMN_TRANSACTION_SOURCEACCOUNTNR = "sourceaccountnumber";
	String COLUMN_TRANSACTION_DESTINATIONACCOUNTNR = "destinationaccountnumber";

	boolean saveTransactionHistory(Transaction transaction) throws RemoteException;
	List getAllTransactions(String customerCPR) throws RemoteException;
}
