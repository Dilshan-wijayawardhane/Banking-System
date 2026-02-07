package b_2;

public class Transaction {
    public void transfer(Account from, Account to, int amount) {
        if (from.getBalance() >= amount) {
            from.withdraw(amount);
            to.deposit(amount);
        }
    }
}
