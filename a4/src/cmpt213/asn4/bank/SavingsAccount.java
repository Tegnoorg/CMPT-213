package cmpt213.asn4.bank;

public class SavingsAccount extends BankAccount {
    private boolean isActive;


    /**
     * Constructor to initialize balance and annual interest
     * @param balance
     * @param interest
     */
    public SavingsAccount(double balance, double interest) {
        super(balance, interest);
        this.isActive = (balance >= 25);
    }

    /**
     * withdrows a given amount
     * @param amount is what you want to withdraw
     * @throws IllegalArgumentException if the number is not positive or leaves the balance in the negatives
     */

    @Override
    public void withdraw(double amount) {
        if(!isActive){
            throw new IllegalArgumentException("account is inactive");
        }
        super.withdraw(amount);
        if( super.getBalance() < 25){
            isActive = false;
        }
    }

    /**
     * deposit a given amount
     * @param amount what you want to deposit
     * @throws IllegalArgumentException if the amount is not a positive number
     */

    @Override
    public void deposit(double amount) {
        super.deposit(amount);
        if(super.getBalance() > 25){
            isActive = true;
        }
    }

    /**
     * monthly reset and calculates balance
     */

    @Override
    public void monthlyProcess(){
        if(numWithdrawals > 4 ){
            monthlyCharge = numWithdrawals - 4;
        }
        super.monthlyProcess();
        if(super.getBalance() < 25){
            isActive = false;
        }
    }

    /**
     * getter for isActive
     */

    public boolean isActive() {
        return isActive;
    }
}
