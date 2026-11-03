package practical8;

class BankException extends Exception {
    public BankException(String message) {
        super(message);
    }
}

class InsufficientFundsException extends BankException {
    private long shortfall;

    public InsufficientFundsException(String msg, long shortfall) {
        super(msg);
        this.shortfall = shortfall;
    }

    public long getShortfall() {
        return shortfall;
    }
}

class InvalidAmountException extends BankException {
    public InvalidAmountException(String msg) {
        super(msg);
    }
}

class AutoAuditLogger implements AutoCloseable {
    public void log(String action) {
        System.out.println("[Audit Log] " + action);
    }

    @Override
    public void close() {
        System.out.println("[Audit Log Closed] Connection auto-closed safely via try-with-resources.");
    }
}

class Account {
    private String accountNumber;
    private long balance;

    public Account(String accountNumber, long balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public void deposit(long amount) throws InvalidAmountException {
        if (amount <= 0) {
            throw new InvalidAmountException("Deposit amount must be strictly positive: " + amount);
        }
        balance += amount;
        System.out.println("Deposited Rs. " + amount + ". Balance: Rs. " + balance);
    }

    public void withdraw(long amount) throws InsufficientFundsException, InvalidAmountException {
        if (amount <= 0) {
            throw new InvalidAmountException("Withdrawal amount must be strictly positive: " + amount);
        }
        if (amount > balance) {
            long shortfall = amount - balance;
            throw new InsufficientFundsException("Insufficient funds in " + accountNumber + "!", shortfall);
        }
        balance -= amount;
        System.out.println("Withdrawn Rs. " + amount + ". New Balance: Rs. " + balance);
    }

    public long getBalance() { return balance; }
    public String getAccountNumber() { return accountNumber; }
}

public class MiniBank {
    public static void transfer(Account from, Account to, long amount) throws BankException {
        System.out.println("Initiating transfer of Rs. " + amount + " from " + from.getAccountNumber() + " to " + to.getAccountNumber());
        try {
            from.withdraw(amount);
            to.deposit(amount);
            System.out.println("Transfer successful!");
        } catch (BankException e) {
            System.out.println("Transfer transaction failed! Rolling back changes.");
            throw e;
        } finally {
            System.out.println("Transfer attempt audit record stored.");
        }
    }

    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println(" MiniBank - Practical 8: Custom Exception Hierarchy");
        System.out.println(" Student: Vidhi Patel | ID: 25CS082");
        System.out.println("==================================================");

        Account a1 = new Account("AC0001", 1000);
        Account a2 = new Account("AC0002", 500);

        try (AutoAuditLogger logger = new AutoAuditLogger()) {
            logger.log("Session opened for 25CS082.");

            System.out.println("\n--- Attempting Overdraw (Rs. 5000 on balance of 1000) ---");
            try {
                a1.withdraw(5000);
            } catch (InsufficientFundsException e) {
                System.out.println("Caught Expected Exception: " + e.getMessage());
                System.out.println("Withdrawal failed: short by " + e.getShortfall() + ". Balance stays: Rs. " + a1.getBalance());
            } catch (InvalidAmountException e) {
                System.out.println("Invalid amount error: " + e.getMessage());
            }

            System.out.println("\n--- Attempting Invalid Negative Deposit ---");
            try {
                a1.deposit(-200);
            } catch (InvalidAmountException e) {
                System.out.println("Caught Expected Exception: " + e.getMessage());
            }

            System.out.println("\n--- Performing Valid Transfer ---");
            try {
                transfer(a1, a2, 400);
            } catch (BankException e) {
                System.out.println("Caught BankException: " + e.getMessage());
            }
        } // logger automatically closed here
    }
}
