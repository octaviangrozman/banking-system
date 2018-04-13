import utils.DatabaseHelper;

import java.math.BigDecimal;
import java.rmi.RemoteException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class BankExchangeRateDAO implements BankExchangeRate {

    private final DatabaseHelper helper;

    public BankExchangeRateDAO(DatabaseHelper helper) {
        this.helper = helper;
    }

    private ExchangeRate createExchangeRate(ResultSet rs) throws SQLException {
        String currency = rs.getString(COLUMN_EXCHANGERATE_CURRENCY);
        BigDecimal rate = rs.getBigDecimal(COLUMN_EXCHANGERATE_RATE);
        return new ExchangeRate(currency, rate);
    }

    @Override
    public boolean addExchangeRate(ExchangeRate exchangeRate) throws RemoteException {
        int rows = helper.executeUpdate(
                "INSERT INTO " + TABLE_NAME + "(" + COLUMN_EXCHANGERATE_CURRENCY + ", " + COLUMN_EXCHANGERATE_RATE + ") VALUES\n" +
                        "  (?, ?)", exchangeRate.getCurrency(), exchangeRate.getRate());
        return rows == 1;
    }

    @Override
    public List getExchangeRates() throws RemoteException {
        return helper.map((rs) -> createExchangeRate(rs), "SELECT * FROM " + TABLE_NAME);
    }
}
