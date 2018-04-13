import utils.DatabaseHelper;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class BankTransactionDAO implements BankTransaction {

	private final DatabaseHelper helper;

	public BankTransactionDAO(DatabaseHelper helper) {
		this.helper = helper;
	}

	@Override
	public boolean saveTransactionHistory(Transaction transaction) throws RemoteException {
		int rows = 0;
		if (transaction.getDestinationAccountNr() > 0) {
			rows = helper.executeUpdate(
					"INSERT INTO " + TABLE_NAME + "(" + COLUMN_TRANSACTION_TYPE + ", " + COLUMN_TRANSACTION_AMOUNT + ", " + COLUMN_TRANSACTION_SOURCEACCOUNTNR + ", " + COLUMN_TRANSACTION_DESTINATIONACCOUNTNR + ") VALUES\n" +
							"  (?, ?, ?, ?)", transaction.getType(), transaction.getAmount(), transaction.getSourceAccountNr(), transaction.getDestinationAccountNr());
		} else {
			rows = helper.executeUpdate(
					"INSERT INTO " + TABLE_NAME + "(" + COLUMN_TRANSACTION_TYPE + ", " + COLUMN_TRANSACTION_AMOUNT + ", " + COLUMN_TRANSACTION_SOURCEACCOUNTNR + ") VALUES\n" +
							"  (?, ?, ?)", transaction.getType(), transaction.getAmount(), transaction.getSourceAccountNr());
		}
		return rows == 1;
	}

	private Transaction createTransaction(ResultSet rs) throws SQLException {
		Date date = rs.getDate(COLUMN_TRANSACTION_DATE);
		String type = rs.getString(COLUMN_TRANSACTION_TYPE);
		BigDecimal amount = rs.getBigDecimal(COLUMN_TRANSACTION_AMOUNT);
		int sourceAccountNr = rs.getInt(COLUMN_TRANSACTION_SOURCEACCOUNTNR);
		int destinationAccountNr = rs.getInt(COLUMN_TRANSACTION_DESTINATIONACCOUNTNR);
		return new Transaction(date, type, amount, sourceAccountNr, destinationAccountNr);
	}

	@Override
	public List getAllTransactions(String customerCPR) throws RemoteException {
		return helper.map((rs) -> createTransaction(rs), "SELECT * FROM " + TABLE_NAME);
	}


}
