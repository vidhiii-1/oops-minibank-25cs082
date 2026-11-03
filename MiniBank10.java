package practical10;

import java.util.concurrent.*;

class SafeAccount {
    private final String accountNumber;
    private long balance;

    public SafeAccount(String accountNumber, long balance) {
        this.accountNumber = accountNumber;
        this.balance = balance;
    }

    public String getAccountNumber() { return accountNumber; }
    public synchronized long getBalance() { return balance; }

    public synchronized void deposit(long amount) {
        balance += amount;
    }

    public synchronized boolean withdraw(long amount) {
        if (balance >= amount) {
            balance -= amount;
            return true;
        }
        return false;
    }

    // Deadlock-free transfer using consistent lock ordering
    public static void transferSafe(SafeAccount from, SafeAccount to, long amount) {
        SafeAccount firstLock = from.getAccountNumber().compareTo(to.getAccountNumber()) < 0 ? from : to;
        SafeAccount secondLock = from.getAccountNumber().compareTo(to.getAccountNumber()) < 0 ? to : from;

        synchronized (firstLock) {
            synchronized (secondLock) {
                if (from.withdraw(amount)) {
                    to.deposit(amount);
                    System.out.println("Transferred Rs. " + amount + " from " + from.getAccountNumber() + " to " + to.getAccountNumber());
                }
            }
        }
    }
}

class TransactionProcessor {
    private ExecutorService pool = Executors.newFixedThreadPool(4);

    public void submit(Runnable task) {
        pool.execute(task);
    }

    public void stop() {
        pool.shutdown();
        try {
            pool.awaitTermination(3, TimeUnit.SECONDS);
        } catch (InterruptedException ignored) {}
    }
}

public class MiniBank {
    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println(" MiniBank - Practical 10: Thread Pool & Deadlock Prevention");
        System.out.println(" Student: Vidhi Patel | ID: 25CS082");
        System.out.println("==================================================");

        SafeAccount a1 = new SafeAccount("AC0001", 5000);
        SafeAccount a2 = new SafeAccount("AC0002", 5000);

        TransactionProcessor processor = new TransactionProcessor();

        System.out.println("\nSubmitting concurrent bidirectional transfers with lock ordering...");
        for (int i = 0; i < 4; i++) {
            processor.submit(() -> SafeAccount.transferSafe(a1, a2, 100));
            processor.submit(() -> SafeAccount.transferSafe(a2, a1, 100));
        }

        processor.stop();

        System.out.println("\nAll transaction pool tasks finished without deadlock.");
        System.out.println("Final Balance AC0001: Rs. " + a1.getBalance());
        System.out.println("Final Balance AC0002: Rs. " + a2.getBalance());
    }
}
