package practical2;

class Customer {
    private String name;
    private String email;
    private String mobile;
    private final String customerId;

    private static long customerCounter = 100;

    private static synchronized String generateCustomerId() {
        customerCounter++;
        return "CUST" + customerCounter;
    }

    public Customer(String name, String email, String mobile) {
        this.customerId = generateCustomerId();
        this.name = name;
        this.email = email;
        this.mobile = mobile;
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getMobile() {
        return mobile;
    }
}

class Account {
    private final String accountNumber;
    private String ownerName;
    private long balance;
    private boolean active;

    private static int accountCounter = 0;

    private static synchronized String generateAccountNumber() {
        accountCounter++;
        return String.format("AC%04d", accountCounter);
    }

    public Account(String ownerName, long balance) {
        this.accountNumber = generateAccountNumber();
        this.ownerName = ownerName;
        this.balance = Math.max(0, balance);
        this.active = true;
    }

    public Account(String ownerName) {
        this(ownerName, 0);
    }

    public void deposit(long amount) {
        if (amount > 0) {
            balance += amount;
            System.out.println("Deposited Rs. " + amount + " into " + accountNumber + ". Current balance: " + balance);
        } else {
            System.out.println("Invalid deposit amount.");
        }
    }

    public boolean withdraw(long amount) {
        if (amount > 0 && balance >= amount) {
            balance -= amount;
            System.out.println("Withdrawn Rs. " + amount + " from " + accountNumber + ". Current balance: " + balance);
            return true;
        } else {
            System.out.println("Withdrawal of Rs. " + amount + " FAILED on " + accountNumber + " (Insufficient balance or invalid amount). Current balance: " + balance);
            return false;
        }
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public long getBalance() {
        return balance;
    }

    public boolean isActive() {
        return active;
    }
}

public class MiniBank {
    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println(" MiniBank - Practical 2: Customer & Account Entities");
        System.out.println(" Student: Vidhi Patel | ID: 25CS082");
        System.out.println("==================================================");

        Customer c1 = new Customer("Vidhi Patel", "krisha@example.com", "9876543210");
        Customer c2 = new Customer("Riya Shah", "riya@example.com", "9123982280");

        System.out.println("Created Customer: " + c1.getCustomerId() + " -> " + c1.getName() + " (" + c1.getEmail() + ")");
        System.out.println("Created Customer: " + c2.getCustomerId() + " -> " + c2.getName() + " (" + c2.getEmail() + ")");

        System.out.println("\n--- Initializing Accounts in Account[] ---");
        Account[] accounts = new Account[3];
        accounts[0] = new Account(c1.getName(), 5000);
        accounts[1] = new Account(c2.getName(), 10000);
        accounts[2] = new Account("Aarav Mehta"); // zero balance constructor chaining

        for (Account acc : accounts) {
            System.out.println("Account: " + acc.getAccountNumber() + " | Owner: " + acc.getOwnerName() + " | Balance: Rs. " + acc.getBalance());
        }

        System.out.println("\n--- Performing Transactions on " + accounts[0].getAccountNumber() + " ---");
        accounts[0].deposit(2000);  // 5000 + 2000 = 7000
        accounts[0].withdraw(3000); // 7000 - 3000 = 4000
        boolean failedAttempt = accounts[0].withdraw(10000); // Fails (4000 < 10000)
        System.out.println("Large withdraw returned: " + failedAttempt);

        System.out.println("\n--- Final Account Statuses ---");
        for (Account acc : accounts) {
            System.out.println(acc.getAccountNumber() + " (" + acc.getOwnerName() + ") Final Balance: Rs. " + acc.getBalance());
        }
    }
}
