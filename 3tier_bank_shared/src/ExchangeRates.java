import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class ExchangeRates implements Serializable {

    private final Map<String, BigDecimal> rates;

    public ExchangeRates(Collection<ExchangeRate> exchangeRates) {
        rates = new HashMap();
        if (exchangeRates == null) {
            rates.put("EUR", BigDecimal.valueOf(1));
            rates.put("USD", BigDecimal.valueOf(1.2));
            rates.put("DKK", BigDecimal.valueOf(7.6));
        } else {
            exchangeRates.forEach(exchangeRate -> {
                String currency = exchangeRate.getCurrency();
                BigDecimal rate = exchangeRate.getRate();
                rates.put(currency, rate);
            });
        }
    }

    public Map<String, BigDecimal> getRates() {
        return rates;
    }

    public BigDecimal convert(Money money, String currencyToConvertTo) {
        switch (currencyToConvertTo) {
            case "EUR": {
                BigDecimal rate = rates.get(money.getCurrency());
                return money.getAmount().divide(rate);
            }
            default: {
                BigDecimal rate1 = rates.get(money.getCurrency());
                BigDecimal rate2 = rates.get(currencyToConvertTo);
                BigDecimal amountInEur = money.getAmount().divide(rate1, BigDecimal.ROUND_FLOOR);
                return amountInEur.multiply(rate2);
            }
        }
    }

    @Override
    public String toString() {
        return "ExchangeRates{" +
                "rates=" + rates +
                '}';
    }
}
