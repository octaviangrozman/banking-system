import utils.DatabaseHelper;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;


public class BankAccountDAO implements BankAccount{

	private final DatabaseHelper helper;

	public BankAccountDAO(DatabaseHelper helper) {
		this.helper = helper;
	}

	@Override
	public boolean deposit(int accountNr, BigDecimal amount)
			throws RemoteException {
		int rows = helper.executeUpdate("UPDATE " + TABLE_NAME + " SET " + COLUMN_ACCOUNT_AMOUNT + " = " + COLUMN_ACCOUNT_AMOUNT + " + ? WHERE " + COLUMN_ACCOUNT_NUMBER + " = ?",
				amount, accountNr);
		return rows == 1;
	}

	@Override
	public boolean withdraw(int accountNr, BigDecimal amount)
			throws RemoteException {
		int rows = helper.executeUpdate("UPDATE  " + TABLE_NAME + " SET " + COLUMN_ACCOUNT_AMOUNT + " = " + COLUMN_ACCOUNT_AMOUNT + " - ? WHERE " + COLUMN_ACCOUNT_NUMBER + " = ?",
				amount, accountNr);
		return rows == 1;
	}

	@Override
	public boolean transfer(int sourceAccountNr, int destinationAccountNr,
			BigDecimal amount) throws RemoteException {
		return withdraw(sourceAccountNr, amount) && deposit(destinationAccountNr, amount);
	}

	private Account createAccount(ResultSet rs) throws SQLException {
		int number = rs.getInt(COLUMN_ACCOUNT_NUMBER);
		String customerCpr = rs.getString(COLUMN_ACCOUNT_CUSTOMERCPR);
		BigDecimal amount = rs.getBigDecimal(COLUMN_ACCOUNT_AMOUNT);
		String currency = rs.getString(COLUMN_ACCOUNT_CURRENCY);
		Money money = new Money(amount, currency);
		return new Account(number, money, customerCpr);
	}

	@Override
	public Account getAccount(int accountNumber) throws RemoteException {
		return (Account) helper.mapSingle((rs) -> createAccount(rs), "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_ACCOUNT_NUMBER + " = ?", accountNumber);
	}

	@Override
	public List getAllAccounts(String customerCPR) throws RemoteException {
		return helper.map((rs) -> createAccount(rs), "SELECT * FROM " + TABLE_NAME);
	}

}
