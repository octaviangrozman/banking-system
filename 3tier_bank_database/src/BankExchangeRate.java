import java.rmi.RemoteException;
import java.util.List;

public interface BankExchangeRate {
    String TABLE_NAME = "ExchangeRate";
    String COLUMN_EXCHANGERATE_CURRENCY ="currency";
    String COLUMN_EXCHANGERATE_RATE = "rate";

    boolean addExchangeRate(ExchangeRate exchangeRate) throws RemoteException;
    List getExchangeRates() throws RemoteException;

}
