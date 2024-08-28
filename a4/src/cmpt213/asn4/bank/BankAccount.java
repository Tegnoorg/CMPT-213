package cmpt213.asn4.bank;

public class BankAccount {

    private double balance;
    private int numDeposits = 0;
    int numWithdrawals = 0;
    private double interest;
    float monthlyCharge;
    /**
     * Constructor to initialize the balance and annual interest
     * @param balance starting balance
     * @param interest annual interest on account
     */
    public BankAccount(double balance, double interest) {
        if(balance < 0 || interest < 0) {
            throw new IllegalArgumentException("Invalid balance or interest. Please enter positive values for balance and interest.");
        }
        this.balance = balance;
        this.interest = interest;
    }

    /**
     * deposit a given amount
     * @param amount what you want to deposit
     * @throws IllegalArgumentException if the amount is not a possitive number
     */
    public void deposit(double amount) {
        if(amount < 0) {
            throw new IllegalArgumentException("Invalid amount. Please enter a positive value.");
        }
        balance += amount;
        numDeposits++;
    }

    /**
     * withdraws a given amount
     * @param amount is what you want to withdraw
     * @throws IllegalArgumentException if the number is not positive or leaves the balance in the negatives
     */
    public void withdraw(double amount) {
        if(amount < 0) {
            throw new IllegalArgumentException("Invalid amount. Please enter a positive value.");
        }
        if(balance - amount < 0) {
            throw new IllegalArgumentException("Insufficient balance. Please enter a positive value.");
        }
        balance -= amount;
        numWithdrawals++;
    }

    /**
     * calculates the interest based on balance
     */
    public void calculateInterest() {
        double monthlyInterestRate = this.interest / 12;
        double monthlyInterest = this.balance * monthlyInterestRate;
        balance += monthlyInterest;
    }

    /**
     * monthly reset and calculates balance
     * calls calculateInterest
     */

    public void monthlyProcess(){
        balance -= monthlyCharge;
        calculateInterest();
        numDeposits = 0;
        numWithdrawals = 0;
        monthlyCharge = 0;
    }


    /**
     * standards getters and setters
     * @return the private values of balance, numDeposits, numWithdrawls, interest, montlyCharge
     */
    public double getBalance() {
        return balance;
    }

    public int getNumDeposits() {
        return numDeposits;
    }

    public int getNumWithdrawals() {
        return numWithdrawals;
    }
    public double getInterest() {
        return interest;
    }
    public double getMonthlyCharge() {
        return monthlyCharge;
    }
}
