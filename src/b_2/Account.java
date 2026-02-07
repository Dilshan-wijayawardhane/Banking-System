package b_2;



public class Account extends Customer {
	private int balance;
    private int accountNumber;

    public Account(String FName, String LName, int accountNumber, int balance) {
        setFirstName(FName);
        setLastName(LName);
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public int getBalance() {
        return balance;
    }

    public int getAccountNum() {
        return accountNumber;
    }

    public void deposit(int amount) {
        balance += amount;
    }

    public void withdraw(int amount) {
        if (amount <= balance) {
            balance -= amount;
        } else {
            System.out.println("Insufficient funds!");
        }
    }
    public String toString() {
        return getFirstName() + " " + getLastName() + " | Acc#: " + accountNumber + " | Balance: " + balance;
    }

}
