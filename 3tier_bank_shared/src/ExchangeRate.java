import java.io.Serializable;
import java.math.BigDecimal;

public class ExchangeRate implements Serializable {

    private final String currency;
    private final BigDecimal rate;

    public ExchangeRate(String currency, BigDecimal rate) {
        this.currency = currency;
        this.rate = rate;
    }

    public String getCurrency() {
        return currency;
    }

    public BigDecimal getRate() {
        return rate;
    }
}
