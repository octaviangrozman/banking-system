import utils.DatabaseHelper;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;


@SuppressWarnings("serial")
public class DBBankServer extends UnicastRemoteObject implements DBServer {

	private BankAccount accountDAO;
	private BankTransaction transactionDAO;
	private BankCustomer customerDAO;
	private BankExchangeRate exchangeRateDAO;

	private DBBankServer() throws RemoteException {
		super();
		DatabaseHelper helper = new DatabaseHelper(DB_URL, DB_USER, DB_PASS);
		accountDAO = new BankAccountDAO(helper);
		transactionDAO = new BankTransactionDAO(helper);
		customerDAO = new BankCustomerDAO(helper);
		exchangeRateDAO = new BankExchangeRateDAO(helper);
	}

	@Override
	public boolean deposit(int accountNr, BigDecimal amount)
			throws RemoteException {
		boolean depositSuccessful = accountDAO.deposit(accountNr, amount);
		Transaction transaction = new Transaction(Transaction.DEPOSIT, amount, accountNr);
		boolean transactionSuccessful = transactionDAO.saveTransactionHistory(transaction);
		return depositSuccessful && transactionSuccessful;
	}

	@Override
	public boolean withdraw(int accountNr, BigDecimal amount)
			throws RemoteException {
		boolean withdrawSuccessful = accountDAO.withdraw(accountNr, amount);
		Transaction transaction = new Transaction(Transaction.WITHDRAW, amount, accountNr);
        boolean transactionSuccessful = transactionDAO.saveTransactionHistory(transaction);
		return withdrawSuccessful && transactionSuccessful;
	}

	@Override
	public boolean transfer(int sourceAccountNr, int destinationAccountNr,
			BigDecimal amount) throws RemoteException {
        boolean transferSuccessful = accountDAO.transfer(sourceAccountNr, destinationAccountNr, amount);
		Transaction transaction = new Transaction(Transaction.TRANSFER, amount, sourceAccountNr, destinationAccountNr);
        boolean transactionSuccessful = transactionDAO.saveTransactionHistory(transaction);
		return transferSuccessful && transactionSuccessful;
	}

	@Override
	public List getAllAccounts(String customerCPR) throws RemoteException {
		return accountDAO.getAllAccounts(customerCPR);
	}

	@Override
	public Account getAccount(int accountNumber) throws RemoteException {
		return accountDAO.getAccount(accountNumber);
	}

	@Override
	public List getAllTransactions(String customerCPR)
			throws RemoteException {
		return transactionDAO.getAllTransactions(customerCPR);
	}

	@Override
	public boolean createCustomer(Customer customer) throws RemoteException {
		return customerDAO.createCustomer(customer);
	}

	@Override
	public Customer getCustomer(String customerCPR) throws RemoteException {
		return customerDAO.getCustomer(customerCPR);
	}

	@Override
	public List getAllCustomers() throws RemoteException {
		return customerDAO.getAllCustomers();
	}

	@Override
	public boolean addExchangeRate(ExchangeRate exchangeRate) throws RemoteException {
		return exchangeRateDAO.addExchangeRate(exchangeRate);
	}

	@Override
	public ExchangeRates getExchangeRates() throws RemoteException {
		List rates = exchangeRateDAO.getExchangeRates();
		return new ExchangeRates(rates);
	}

	private static void startAsTestServer() throws RemoteException {
        DBBankServer dbBankServer = new DBBankServer();
        Registry registry = LocateRegistry.createRegistry(PORT);
        registry.rebind(SERVER_NAME, dbBankServer);
        System.out.println("Database server started...");
    }

    public static void main(String[] args) throws Exception {
        startAsTestServer();
    }

}
