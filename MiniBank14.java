package practical14;

import java.util.*;

class BankService {
    private Map<String, Long> accountStore = new HashMap<>();

    public BankService() {
        accountStore.put("AC0001", 10000L);
        accountStore.put("AC0002", 5000L);
    }

    public void openAccount(String acc, long initial) {
        accountStore.put(acc, initial);
        System.out.println("Opened Account: " + acc + " with balance: Rs. " + initial);
    }

    public void deposit(String acc, long amt) {
        long current = accountStore.getOrDefault(acc, 0L);
        accountStore.put(acc, current + amt);
        System.out.println("Deposited Rs. " + amt + " to " + acc + ". Current Balance: Rs. " + (current + amt));
    }

    public boolean withdraw(String acc, long amt) {
        long current = accountStore.getOrDefault(acc, 0L);
        if (current >= amt) {
            accountStore.put(acc, current - amt);
            System.out.println("Withdrawn Rs. " + amt + " from " + acc + ". Current Balance: Rs. " + (current - amt));
            return true;
        } else {
            System.out.println("Withdrawal failed: insufficient funds in " + acc);
            return false;
        }
    }

    public void transfer(String from, String to, long amt) {
        if (withdraw(from, amt)) {
            deposit(to, amt);
            System.out.println("Transfer of Rs. " + amt + " completed successfully.");
        }
    }

    public void listAccounts() {
        System.out.println("--- Current Bank Accounts ---");
        for (Map.Entry<String, Long> entry : accountStore.entrySet()) {
            System.out.println("Account: " + entry.getKey() + " | Balance: Rs. " + entry.getValue());
        }
    }
}

public class MiniBank {
    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println(" MiniBank - Practical 14: Integrated Capstone Milestone");
        System.out.println(" Student: Vidhi Patel | ID: 25CS082");
        System.out.println(" Subject: CEUE203 (Object-Oriented Programming)");
        System.out.println("==================================================");

        BankService service = new BankService();
        service.listAccounts();

        System.out.println("\n--- Executing Service Layer Operations ---");
        service.openAccount("AC0003", 7500);
        service.deposit("AC0001", 2000);
        service.transfer("AC0001", "AC0002", 3000);

        System.out.println("\n--- Final Account Statuses Post-Integration ---");
        service.listAccounts();
        System.out.println("\nCapstone milestone 3 completed successfully.");
    }
}
