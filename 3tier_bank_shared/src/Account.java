import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class Account implements Serializable {

    private int number;
    private Money money;
    private String customerCpr;
    private ArrayList<Transaction> transactions;

    public Account(int number, Money money, String customerCpr) {
        this.number = number;
        this.money = money;
        this.customerCpr = customerCpr;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Money getMoney() {
        return money;
    }

    public void setMoney(Money money) {
        this.money = money;
    }

    public String getCustomerCpr() {
        return customerCpr;
    }

    public void setCustomerCpr(String customerCpr) {
        this.customerCpr = customerCpr;
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(ArrayList<Transaction> transactions) {
        this.transactions = transactions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return number == account.number &&
                Objects.equals(customerCpr, account.customerCpr) &&
                Objects.equals(money, account.money) &&
                Objects.equals(transactions, account.transactions);
    }

    @Override
    public int hashCode() {

        return Objects.hash(number, money, customerCpr, transactions);
    }

    @Override
    public String toString() {
        return "Account{" +
                "number=" + number +
                ", money=" + money +
                ", customerCpr=" + customerCpr +
                ", transactions=" + transactions +
                '}';
    }
}
