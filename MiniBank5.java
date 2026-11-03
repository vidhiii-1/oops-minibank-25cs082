package practical5;

abstract class Account {
    private final String accountNumber;
    private String ownerName;
    protected long balance;

    public Account(String accountNumber, String ownerName, long balance) {
        this.accountNumber = accountNumber;
        this.ownerName = ownerName;
        this.balance = balance;
    }

    public String getAccountNumber() { return accountNumber; }
    public String getOwnerName() { return ownerName; }
    public long getBalance() { return balance; }

    public void deposit(long amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposited Rs. " + amount + " to " + accountNumber + ". New balance: " + balance);
        }
    }

    public boolean withdraw(long amount) {
        if (canWithdraw(amount)) {
            balance -= amount;
            System.out.println("Withdrawn Rs. " + amount + " from " + accountNumber + ". New balance: " + balance);
            return true;
        } else {
            System.out.println("Withdrawal of Rs. " + amount + " REJECTED on " + accountNumber + " by account policy rules.");
            return false;
        }
    }

    // Abstract methods to be overridden by account subtypes
    public abstract double interestRate();
    public abstract boolean canWithdraw(long amount);

    @Override
    public String toString() {
        return accountNumber + " (" + getClass().getSimpleName() + ") | Owner: " + ownerName + " | Balance: Rs. " + balance;
    }
}

class SavingsAccount extends Account {
    private long minBalance;

    public SavingsAccount(String accountNumber, String ownerName, long balance, long minBalance) {
        super(accountNumber, ownerName, balance);
        this.minBalance = minBalance;
    }

    public long getMinBalance() {
        return minBalance;
    }

    @Override
    public double interestRate() {
        return 4.0;
    }

    @Override
    public boolean canWithdraw(long amount) {
        return amount > 0 && (balance - amount >= minBalance);
    }
}

class CurrentAccount extends Account {
    private long overdraftLimit;

    public CurrentAccount(String accountNumber, String ownerName, long balance, long overdraftLimit) {
        super(accountNumber, ownerName, balance);
        this.overdraftLimit = overdraftLimit;
    }

    public long getOverdraftLimit() {
        return overdraftLimit;
    }

    @Override
    public double interestRate() {
        return 0.0;
    }

    @Override
    public boolean canWithdraw(long amount) {
        return amount > 0 && (balance - amount >= -overdraftLimit);
    }
}

class FixedDepositAccount extends Account {
    public FixedDepositAccount(String accountNumber, String ownerName, long balance) {
        super(accountNumber, ownerName, balance);
    }

    @Override
    public double interestRate() {
        return 7.0;
    }

    @Override
    public boolean canWithdraw(long amount) {
        // Locked until maturity
        return false;
    }
}

public class MiniBank {
    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println(" MiniBank - Practical 5: Inheritance & Polymorphism");
        System.out.println(" Student: Vidhi Patel | ID: 25CS082");
        System.out.println("==================================================");

        Account[] accounts = new Account[] {
            new SavingsAccount("SA1001", "Vidhi Patel", 12000, 2000),
            new CurrentAccount("CA2001", "Riya Shah", 5000, 10000),
            new FixedDepositAccount("FD3001", "Aarav Mehta", 50000)
        };

        System.out.println("\n--- Demonstrating Run-Time Polymorphism (Dynamic Dispatch) ---");
        for (Account acc : accounts) {
            System.out.println(acc);
            System.out.println("  -> Annual Interest Rate: " + acc.interestRate() + "%");

            // Using pattern matching with instanceof
            if (acc instanceof SavingsAccount sa) {
                System.out.println("  -> [Pattern match] SavingsAccount detected. Minimum mandatory balance: Rs. " + sa.getMinBalance());
            } else if (acc instanceof CurrentAccount ca) {
                System.out.println("  -> [Pattern match] CurrentAccount detected. Approved Overdraft Limit: Rs. " + ca.getOverdraftLimit());
            } else if (acc instanceof FixedDepositAccount) {
                System.out.println("  -> [Pattern match] FixedDepositAccount detected. Term locked: withdrawals restricted.");
            }
        }

        System.out.println("\n--- Testing Polymorphic Withdrawal Rules ---");
        System.out.println("1. Attempting Rs. 11,000 from SavingsAccount SA1001 (Balance: 12000, minBalance: 2000):");
        accounts[0].withdraw(11000); // rejected: remaining would be 1000 < 2000

        System.out.println("\n2. Attempting Rs. 9,000 from CurrentAccount CA2001 (Balance: 5000, overdraft: 10000):");
        accounts[1].withdraw(9000);  // accepted: falls to -4000 >= -10000

        System.out.println("\n3. Attempting Rs. 5,000 from FixedDepositAccount FD3001 (Balance: 50000):");
        accounts[2].withdraw(5000);  // rejected: FD is locked
    }
}
