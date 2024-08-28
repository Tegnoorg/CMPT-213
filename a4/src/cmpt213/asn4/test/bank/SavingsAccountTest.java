package cmpt213.asn4.test.bank;

import cmpt213.asn4.bank.SavingsAccount;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

public class SavingsAccountTest {

    @Test
    public void testSavingsConstructor() {
        SavingsAccount acc1 = new SavingsAccount(1000, 0.01);
        assertEquals(1000, acc1.getBalance());
        assertEquals(0.01, acc1.getInterest());
        assertEquals(0, acc1.getNumDeposits());
        assertEquals(0, acc1.getNumWithdrawals());
        assertEquals(0, acc1.getMonthlyCharge());
        assertTrue(acc1.isActive());

        SavingsAccount acc2 = new SavingsAccount(20, 0.02);
        assertEquals(20, acc2.getBalance());
        assertEquals(0.02, acc2.getInterest());
        assertEquals(0, acc2.getNumDeposits());
        assertEquals(0, acc2.getNumWithdrawals());
        assertEquals(0, acc2.getMonthlyCharge());
        assertFalse(acc2.isActive());
    }

    @Test
    public void testSavingsWithdraw() throws IllegalAccessException {
        SavingsAccount acc1 = new SavingsAccount(1000,0.01);
        acc1.withdraw(500);
        assertEquals(500, acc1.getBalance());
        assertEquals(1, acc1.getNumWithdrawals());
        assertTrue(acc1.isActive());

        SavingsAccount acc2 = new SavingsAccount(30,0.01);
        acc2.withdraw(10);
        assertEquals(20, acc2.getBalance());
        assertEquals(1, acc2.getNumWithdrawals());
        assertFalse(acc2.isActive());


        SavingsAccount acc3 = new SavingsAccount(20,0.01);
        assertThrows(IllegalArgumentException.class, () -> acc3.withdraw(1));
        assertEquals(20, acc3.getBalance());
        assertEquals(0, acc3.getNumDeposits());
        assertFalse(acc3.isActive());


        SavingsAccount acc4 = new SavingsAccount(30,0.01);
        assertThrows(IllegalArgumentException.class, () -> acc4.withdraw(31));
        assertEquals(30, acc4.getBalance());
        assertEquals(0, acc4.getNumDeposits());
        assertTrue(acc4.isActive());
    }

    @Test
    public void testSavingsDeposit() {
        SavingsAccount acc1 = new SavingsAccount(1000,0.01);
        acc1.deposit(500);
        assertEquals(1500, acc1.getBalance());
        assertEquals(1, acc1.getNumDeposits());
        assertTrue(acc1.isActive());

        SavingsAccount acc2 = new SavingsAccount(10,0.01);
        acc2.deposit(10);
        assertEquals(20, acc2.getBalance());
        assertEquals(1, acc2.getNumDeposits());
        assertFalse(acc2.isActive());


        SavingsAccount acc3 = new SavingsAccount(20,0.01);
        acc3.deposit(10);
        assertEquals(30, acc3.getBalance());
        assertEquals(1, acc3.getNumDeposits());
        assertTrue(acc3.isActive());
    }

    @Test
    public void testSavingsMontlyProcess() {
        SavingsAccount acc1 = new SavingsAccount(1015,0.01);
        acc1.withdraw(1);
        acc1.withdraw(2);
        acc1.withdraw(3);
        acc1.withdraw(4);
        acc1.withdraw(5);
        assertEquals(5, acc1.getNumWithdrawals());
        acc1.monthlyProcess();
        assertEquals((999 + ((0.01/12)*999) ), acc1.getBalance() );
        assertEquals(0, acc1.getNumWithdrawals());
        assertEquals(0, acc1.getMonthlyCharge());
        assertTrue(acc1.isActive());

        SavingsAccount acc2 = new SavingsAccount(1010,0.01);
        acc2.withdraw(1);
        acc2.withdraw(2);
        acc2.withdraw(3);
        acc2.withdraw(4);
        assertEquals(4, acc2.getNumWithdrawals());
        acc2.monthlyProcess();
        assertEquals((1000 + ((0.01/12)*1000) ), acc2.getBalance() );
        assertEquals(0, acc2.getNumWithdrawals());
        assertEquals(0, acc2.getMonthlyCharge());
        assertTrue(acc2.isActive());

        SavingsAccount acc3 = new SavingsAccount(40,0.01);
        acc3.withdraw(1);
        acc3.withdraw(2);
        acc3.withdraw(3);
        acc3.withdraw(4);
        acc3.withdraw(5);
        assertEquals(5, acc3.getNumWithdrawals());
        acc3.monthlyProcess();
        assertEquals((24 + ((0.01/12)*24) ), acc3.getBalance() );
        assertEquals(0, acc3.getNumWithdrawals());
        assertEquals(0, acc3.getMonthlyCharge());
        assertFalse(acc3.isActive());

    }
}
