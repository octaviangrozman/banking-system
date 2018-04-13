import java.rmi.RemoteException;
import java.util.List;

public interface BankCustomer {
    String TABLE_NAME = "Customer";
    String COLUMN_CUSTOMER_NAME ="name";
    String COLUMN_CUSTOMER_CPR = "cpr";
    String COLUMN_CUSTOMER_ADDRESS = "address";

    boolean createCustomer(Customer customer) throws RemoteException;
    Customer getCustomer(String customerCPR) throws RemoteException;
    List getAllCustomers() throws RemoteException;

}
