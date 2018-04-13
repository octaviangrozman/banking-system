import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.Objects;

public class Transaction implements Serializable {

    public static final String DEPOSIT = "DEPOSIT";
    public static final String WITHDRAW = "WITHDRAW";
    public static final String TRANSFER = "TRANSFER";

    private Date date;
	private String type;
	private BigDecimal amount;
	private int sourceAccountNr;
	private int destinationAccountNr;

    public Transaction(String type, BigDecimal amount, int sourceAccountNr) {
        this.type = type;
        this.amount = amount;
        this.sourceAccountNr = sourceAccountNr;
    }

    public Transaction(String type, BigDecimal amount, int sourceAccountNr, int destinationAccountNr) {
        this.type = type;
        this.amount = amount;
        this.sourceAccountNr = sourceAccountNr;
        this.destinationAccountNr = destinationAccountNr;
    }

    public Transaction(Date date, String type, BigDecimal amount, int sourceAccountNr, int destinationAccountNr) {
        this.date = date;
        this.type = type;
        this.amount = amount;
        this.sourceAccountNr = sourceAccountNr;
        this.destinationAccountNr = destinationAccountNr;
    }

    public Date getDate() {
        return date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public int getSourceAccountNr() {
        return sourceAccountNr;
    }

    public void setSourceAccountNr(int sourceAccountNr) {
        this.sourceAccountNr = sourceAccountNr;
    }

    public int getDestinationAccountNr() {
        return destinationAccountNr;
    }

    public void setDestinationAccountNr(int destinationAccountNr) {
        this.destinationAccountNr = destinationAccountNr;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transaction that = (Transaction) o;
        return sourceAccountNr == that.sourceAccountNr &&
                destinationAccountNr == that.destinationAccountNr &&
                Objects.equals(type, that.type) &&
                Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {

        return Objects.hash(type, amount, sourceAccountNr, destinationAccountNr);
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "type=" + type +
                ", date=" + date +
                ", amount=" + amount +
                ", sourceAccountNr=" + sourceAccountNr +
                ", destinationAccountNr=" + destinationAccountNr +
                '}';
    }
}
