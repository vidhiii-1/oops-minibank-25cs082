package practical6;

interface Transactable {
    void deposit(long amount);
    boolean withdraw(long amount);
}

interface InterestBearing {
    double interestRate();
    default double yearlyInterest(long balance) {
        return (balance * interestRate()) / 100.0;
    }
}

interface Premium {} // Marker interface

@FunctionalInterface
interface WithdrawRule {
    boolean allow(long currentBalance, long amount);
}

class BankAccount implements Transactable, InterestBearing, Premium {
    private String accountNumber;
    private String ownerName;
    private long balance;

    public BankAccount(String accountNumber, String ownerName, long balance) {
        this.accountNumber = accountNumber;
        this.ownerName = ownerName;
        this.balance = balance;
    }

    @Override
    public void deposit(long amount) {
        if (amount > 0) balance += amount;
    }

    @Override
    public boolean withdraw(long amount) {
        if (amount > 0 && balance >= amount) {
            balance -= amount;
            return true;
        }
        return false;
    }

    @Override
    public double interestRate() {
        return 4.5;
    }

    public long getBalance() { return balance; }
    public String getAccountNumber() { return accountNumber; }
    public String getOwnerName() { return ownerName; }
}

public class MiniBank {
    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println(" MiniBank - Practical 6: Interfaces, Lambdas & Rules");
        System.out.println(" Student: Vidhi Patel | ID: 25CS082");
        System.out.println("==================================================");

        BankAccount acc = new BankAccount("AC0001", "Vidhi Patel", 25000);

        System.out.println("Account: " + acc.getAccountNumber() + " | Owner: " + acc.getOwnerName() + " | Balance: Rs. " + acc.getBalance());
        System.out.println("Calculated Yearly Interest (Default method): Rs. " + acc.yearlyInterest(acc.getBalance()));

        // Anonymous class WithdrawRule
        WithdrawRule dailyLimitRule = new WithdrawRule() {
            @Override
            public boolean allow(long currentBalance, long amount) {
                return amount <= 10000 && currentBalance >= amount;
            }
        };

        // Lambda expression WithdrawRule
        WithdrawRule positiveFundsRule = (bal, amt) -> amt > 0 && bal >= amt;

        long attempt1 = 8000;
        long attempt2 = 15000;

        System.out.println("\nTesting WithdrawRule (Anonymous Class: Max 10,000 per tx):");
        System.out.println("  Withdraw " + attempt1 + " allowed: " + dailyLimitRule.allow(acc.getBalance(), attempt1));
        System.out.println("  Withdraw " + attempt2 + " allowed: " + dailyLimitRule.allow(acc.getBalance(), attempt2));

        System.out.println("\nTesting WithdrawRule (Lambda Expression):");
        System.out.println("  Withdraw " + attempt2 + " allowed by lambda: " + positiveFundsRule.allow(acc.getBalance(), attempt2));

        if (acc instanceof Premium) {
            System.out.println("\nAccount " + acc.getAccountNumber() + " is marked as [Premium] account holder.");
        }
    }
}
