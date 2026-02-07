package b_2;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.LinkedList;

public class ReadAccounts {
    private LinkedList<String> firstNames = new LinkedList<>();
    private LinkedList<String> lastNames = new LinkedList<>();
    private LinkedList<Integer> accountNumbers = new LinkedList<>();
    private LinkedList<Integer> balances = new LinkedList<>();

    public ReadAccounts(String filePath) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String line;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    firstNames.add(parts[0].trim());
                    lastNames.add(parts[1].trim());
                    accountNumbers.add(Integer.parseInt(parts[2].trim()));
                    balances.add(Integer.parseInt(parts[3].trim()));
                }
            }

            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public LinkedList<String> getFirstNames() {
        return firstNames;
    }

    public LinkedList<String> getLastNames() {
        return lastNames;
    }

    public LinkedList<Integer> getAccounts() {
        return accountNumbers;
    }

    public LinkedList<Integer> getBalances() {
        return balances;
    }

	public static LinkedList<Account> readFromCSV(String string) {
		
		return null;
	}
}
