import utils.DatabaseHelper;

import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class BankCustomerDAO implements BankCustomer {

    private final DatabaseHelper helper;

    public BankCustomerDAO(DatabaseHelper helper) {
        this.helper = helper;
    }

    private Customer createCustomer(ResultSet rs) throws SQLException {
        String cpr = rs.getString(COLUMN_CUSTOMER_CPR);
        String name = rs.getString(COLUMN_CUSTOMER_NAME);
        String address = rs.getString(COLUMN_CUSTOMER_ADDRESS);
        return new Customer(cpr, name, address);
    }

    @Override
    public boolean createCustomer(Customer customer) throws RemoteException {
        int rows = helper.executeUpdate(
                "INSERT INTO " + TABLE_NAME + "(" + COLUMN_CUSTOMER_CPR + ", " + COLUMN_CUSTOMER_NAME + ", " + COLUMN_CUSTOMER_ADDRESS + ") VALUES\n" +
                        "  (?, ?, ?)",
                customer.getCpr(), customer.getName(), customer.getAddress());
        return rows == 1;
    }

    @Override
    public Customer getCustomer(String customerCPR) throws RemoteException {
        return (Customer) helper.mapSingle((rs) -> createCustomer(rs), "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_CUSTOMER_NAME + " = ?", customerCPR);
    }

    @Override
    public List getAllCustomers() throws RemoteException {
        return helper.map((rs) -> createCustomer(rs), "SELECT * FROM " + TABLE_NAME);
    }
}
