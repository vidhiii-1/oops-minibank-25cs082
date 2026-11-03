package practical12;

import java.util.*;

class BankAccount implements Comparable<BankAccount> {
    private String accountNumber;
    private String ownerName;
    private long balance;

    public BankAccount(String accountNumber, String ownerName, long balance) {
        this.accountNumber = accountNumber;
        this.ownerName = ownerName;
        this.balance = balance;
    }

    public String getAccountNumber() { return accountNumber; }
    public String getOwnerName() { return ownerName; }
    public long getBalance() { return balance; }

    @Override
    public int compareTo(BankAccount other) {
        return this.accountNumber.compareTo(other.accountNumber);
    }

    @Override
    public String toString() {
        return accountNumber + " | " + ownerName + " | Rs. " + balance;
    }
}

class AccountStore {
    private Map<String, BankAccount> store = new HashMap<>();
    private List<String> history = new ArrayList<>();

    public void add(BankAccount account) {
        store.put(account.getAccountNumber(), account);
        history.add("Added account: " + account.getAccountNumber());
    }

    public BankAccount get(String accNum) {
        return store.get(accNum);
    }

    public List<BankAccount> sortedByBalance() {
        List<BankAccount> list = new ArrayList<>(store.values());
        list.sort((a, b) -> Long.compare(b.getBalance(), a.getBalance())); // highest first
        return list;
    }
}

public class MiniBank {
    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println(" MiniBank - Practical 12: Collections Framework");
        System.out.println(" Student: Vidhi Patel | ID: 25CS082");
        System.out.println("==================================================");

        AccountStore store = new AccountStore();
        store.add(new BankAccount("AC0002", "Riya Shah", 2000));
        store.add(new BankAccount("AC0001", "Vidhi Patel", 9000));
        store.add(new BankAccount("AC0003", "Aarav Mehta", 5000));

        System.out.println("\n--- Looking up AC0002 in HashMap ---");
        BankAccount found = store.get("AC0002");
        System.out.println("Lookup Result: " + found);

        System.out.println("\n--- Accounts Sorted by Balance (Highest First) ---");
        List<BankAccount> sorted = store.sortedByBalance();
        for (BankAccount acc : sorted) {
            System.out.println("  " + acc);
        }
    }
}
