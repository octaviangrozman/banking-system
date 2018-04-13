import java.io.Serializable;
import java.util.Objects;

public class Customer implements Serializable {

    private String cpr;
    private String name;
    private String address;

    public Customer(String cpr, String name, String address) {
        this.cpr = cpr;
        this.name = name;
        this.address = address;
    }

    public String getCpr() {
        return cpr;
    }

    public void setCpr(String cpr) {
        this.cpr = cpr;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Customer customer = (Customer) o;
        return cpr.equals(customer.cpr) &&
                Objects.equals(name, customer.name) &&
                Objects.equals(address, customer.address);
    }

    @Override
    public int hashCode() {

        return Objects.hash(cpr, name, address);
    }

    @Override
    public String toString() {
        return "Customer{" +
                "cpr=" + cpr +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
