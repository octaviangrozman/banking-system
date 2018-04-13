import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class BankClient {
    private static BankServer bankServer;

    @Before
    public void setUp() throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry(BankServer.PORT);
        bankServer = (BankServer) registry.lookup(BankServer.SERVER_NAME);
    }

    @Test
    public void testDeposit() throws RemoteException {
        Money money = new Money(BigDecimal.valueOf(500), Money.EUR);
        Account OctavianAccountEUR = bankServer.getAccount(1);
        BigDecimal amount = OctavianAccountEUR.getMoney().getAmount();

        bankServer.performTransaction(money, Transaction.DEPOSIT, OctavianAccountEUR , null);
        OctavianAccountEUR = bankServer.getAccount(1);
        assertEquals(OctavianAccountEUR.getMoney().getAmount(), amount.add(BigDecimal.valueOf(500)));
    }

    @Test
    public void testWithdraw() throws RemoteException {
        Money money = new Money(BigDecimal.valueOf(100), Money.DKK);
        Account OctavianAccountDKK = bankServer.getAccount(2);
        BigDecimal amount = OctavianAccountDKK.getMoney().getAmount();

        bankServer.performTransaction(money, Transaction.WITHDRAW, OctavianAccountDKK , null);
        OctavianAccountDKK = bankServer.getAccount(2);
        assertTrue(OctavianAccountDKK.getMoney().getAmount().compareTo(amount) == -1);
    }

    @Test
    public void testTransfer() throws RemoteException {
        Money money = new Money(BigDecimal.valueOf(50), Money.EUR);
        Account OctavianAccountDKK = bankServer.getAccount(2);
        Account ClausAccountUSD = bankServer.getAccount(4);
        BigDecimal amountDKK = OctavianAccountDKK.getMoney().getAmount();
        BigDecimal amountUSD = ClausAccountUSD.getMoney().getAmount();

        bankServer.performTransaction(money, Transaction.TRANSFER, ClausAccountUSD , OctavianAccountDKK);
        OctavianAccountDKK = bankServer.getAccount(2);
        ClausAccountUSD = bankServer.getAccount(4);
        assertTrue(OctavianAccountDKK.getMoney().getAmount().compareTo(amountDKK) == 1);
        assertTrue(ClausAccountUSD.getMoney().getAmount().compareTo(amountUSD) == -1);
    }
}
