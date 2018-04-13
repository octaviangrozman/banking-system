import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;


public class BranchBankServer extends UnicastRemoteObject implements BankServer {

    private DBServer dbServer;
	private ExchangeRates exchangeRates;

	private BranchBankServer(DBServer dbServer) throws RemoteException {
		super();
		this.dbServer = dbServer;
		this.exchangeRates = getExchangeRates();
	}
	
	@Override
	public boolean performTransaction(Money money,
									  String transactionType, Account sourceAccount,
			Account destinationAccount) throws RemoteException {

		// convert money to the right currency
        BigDecimal convertedAmount = exchangeRates.convert(
                money,
                transactionType.equals(Transaction.TRANSFER) ?
						destinationAccount.getMoney().getCurrency() : sourceAccount.getMoney().getCurrency()
        );

		switch (transactionType) {
            case Transaction.DEPOSIT:
                return dbServer.deposit(sourceAccount.getNumber(), convertedAmount);
            case Transaction.WITHDRAW:
				return dbServer.withdraw(sourceAccount.getNumber(), convertedAmount);
            case Transaction.TRANSFER:
				return dbServer.transfer(sourceAccount.getNumber(), destinationAccount.getNumber(), convertedAmount);
            default: return false;
        }
		
	}

	@Override
	public Account getAccount(int accountNumber) throws RemoteException {
		return dbServer.getAccount(accountNumber);
	}

	@Override
	public List getAllAccounts(String customerCPR) throws RemoteException {
		return dbServer.getAllAccounts(customerCPR);
	}

	@Override
	public List getAllTransactions(String customerCPR)
			throws RemoteException {
		return dbServer.getAllTransactions(customerCPR);
	}

	@Override
	public Customer getCustomer(String customerCPR) throws RemoteException {
		return dbServer.getCustomer(customerCPR);
	}

	@Override
	public List getAllCustomers() throws RemoteException {
		return dbServer.getAllCustomers();
	}

	@Override
	public ExchangeRates getExchangeRates() throws RemoteException {
		return dbServer.getExchangeRates();
	}

    public static void main(String[] args) throws RemoteException {
		BranchBankServer branchBankServer = new BranchBankServer(DBServerLocator.getDBServer());
		Registry registry = LocateRegistry.getRegistry(BankServer.PORT);
		registry.rebind(BankServer.SERVER_NAME, branchBankServer);
		System.out.println("Bank server started...");
    }

}
