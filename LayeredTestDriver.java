package practical14;

import java.util.*;

class SimpleAccount {
    private String id;
    private String owner;
    private long balance;

    public SimpleAccount(String id, String owner, long balance) {
        this.id = id;
        this.owner = owner;
        this.balance = balance;
    }

    public String getId() { return id; }
    public String getOwner() { return owner; }
    public long getBalance() { return balance; }
    public void deposit(long amt) { balance += amt; }
    public boolean withdraw(long amt) {
        if (balance >= amt) {
            balance -= amt;
            return true;
        }
        return false;
    }
}

class SimpleBankService {
    private List<SimpleAccount> list = new ArrayList<>();

    public void add(SimpleAccount a) { list.add(a); }
    public int count() { return list.size(); }
    public SimpleAccount find(String id) {
        return list.stream().filter(a -> a.getId().equals(id)).findFirst().orElse(null);
    }
}

public class LayeredTestDriver {
    public static void main(String[] args) {
        System.out.println("=== Practical 14 - Task 1: Layered Mini Architecture Test ===");
        SimpleBankService service = new SimpleBankService();

        // Test 1: Addition increases count
        service.add(new SimpleAccount("AC1", "Krisha", 1000));
        assert service.count() == 1 : "Count assertion failed!";
        System.out.println("Test 1: Addition increases count -> PASS (Count = " + service.count() + ")");

        // Test 2: Deposit reflects balance
        SimpleAccount acc = service.find("AC1");
        acc.deposit(500);
        assert acc.getBalance() == 1500 : "Deposit balance assertion failed!";
        System.out.println("Test 2: Deposit balance update -> PASS (Balance = " + acc.getBalance() + ")");

        // Test 3: Overdrawn rejection
        boolean ok = acc.withdraw(3000);
        assert !ok : "Withdraw overdraw should have failed!";
        System.out.println("Test 3: Overdrawn withdrawal rejected -> PASS");
        System.out.println("All unit test assertions passed successfully.");
    }
}
