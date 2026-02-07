package b_2;

import java.util.LinkedList;





public class Main {
    public static void main(String[] args) {
        ReadAccounts reader = new ReadAccounts("Accounts.csv");
        LinkedList<String> firstNames = reader.getFirstNames();
        LinkedList<String> lastNames = reader.getLastNames();
        LinkedList<Integer> accountNumbers = reader.getAccounts();
        LinkedList<Integer> balances = reader.getBalances();

        LinkedList<Account> accounts = new LinkedList<>();
        for (int i = 0; i < firstNames.size(); i++) {
            accounts.add(new Account(firstNames.get(i), lastNames.get(i), accountNumbers.get(i), balances.get(i)));
        }

        new GUI(accounts);
    }
}
