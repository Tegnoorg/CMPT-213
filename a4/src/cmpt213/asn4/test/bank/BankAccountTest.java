package cmpt213.asn4.test.bank;

import cmpt213.asn4.bank.BankAccount;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.*;

public class BankAccountTest {

    @Test
    public void testBankConstructor() {
        BankAccount acc = new BankAccount(1000,0.01);
        assertEquals(1000, acc.getBalance());
        assertEquals(0.01, acc.getInterest());
        assertEquals(0, acc.getNumDeposits());
        assertEquals(0, acc.getNumWithdrawals());
        assertEquals(0, acc.getMonthlyCharge());

    }

    @Test
    public void testBankDeposit() {
        BankAccount acc1 = new BankAccount(1000, 0.01);
        acc1.deposit(1000);
        assertEquals(2000, acc1.getBalance());
        assertEquals(1, acc1.getNumDeposits());

        BankAccount acc2 = new BankAccount(1000, 0.01);
        assertThrows(IllegalArgumentException.class, ()-> acc2.deposit(-1) );
        assertEquals(1000, acc2.getBalance());
        assertEquals(0, acc2.getNumDeposits());

    }

    @Test
    public void testBankWithdraw() {
        BankAccount acc1 = new BankAccount(1000, 0.01);
        acc1.withdraw(1000);
        assertEquals(0, acc1.getBalance());
        assertEquals(1, acc1.getNumWithdrawals());

        BankAccount acc2 = new BankAccount(1000, 0.01);
        assertThrows(IllegalArgumentException.class, ()-> acc2.withdraw(-1) );
        assertEquals(1000, acc2.getBalance());
        assertEquals(0, acc2.getNumWithdrawals());

        BankAccount acc3 = new BankAccount(1000, 0.01);
        assertThrows(IllegalArgumentException.class, ()-> acc3.withdraw(1001) );
        assertEquals(1000, acc3.getBalance());
        assertEquals(0, acc3.getNumWithdrawals());
    }

    @Test
    public void testBankCalculateMonthlyCharge() {
        BankAccount acc = new BankAccount(1000,0.01);
        acc.calculateInterest();
        assertEquals(1000 + ((0.01/12)*1000), acc.getBalance() );
    }
    @Test
    public void testBankCalculateMonthlyChargeWithdrawal() {
        BankAccount acc = new BankAccount(1000,0.01);
        acc.monthlyProcess();
        assertEquals(1000 + ((0.01/12)*1000), acc.getBalance() );
        assertEquals(0.01, acc.getInterest());
        assertEquals(0, acc.getNumDeposits());
        assertEquals(0, acc.getNumWithdrawals());
        assertEquals(0, acc.getMonthlyCharge());
    }
}

