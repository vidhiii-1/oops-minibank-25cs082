package practical9;

class SharedAccount {
    private String accountNumber;
    private long balance = 0;

    public SharedAccount(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    // Unsynchronized deposit demonstrates race condition
    public void depositUnsync(long amount) {
        long temp = balance;
        try { Thread.sleep(1); } catch (InterruptedException ignored) {}
        balance = temp + amount;
    }

    // Synchronized deposit fixes race condition
    public synchronized void depositSync(long amount) {
        balance += amount;
    }

    public long getBalance() { return balance; }
    public String getAccountNumber() { return accountNumber; }
}

class AccountWorker implements Runnable {
    private SharedAccount account;
    private int times;
    private long amount;
    private boolean useSync;

    public AccountWorker(SharedAccount account, int times, long amount, boolean useSync) {
        this.account = account;
        this.times = times;
        this.amount = amount;
        this.useSync = useSync;
    }

    @Override
    public void run() {
        for (int i = 0; i < times; i++) {
            if (useSync) {
                account.depositSync(amount);
            } else {
                account.depositUnsync(amount);
            }
        }
    }
}

public class MiniBank {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("==================================================");
        System.out.println(" MiniBank - Practical 9: Multithreading & Race Conditions");
        System.out.println(" Student: Vidhi Patel | ID: 25CS082");
        System.out.println("==================================================");

        // 1. Race condition demonstration
        SharedAccount accUnsafe = new SharedAccount("AC_RACE");
        Thread[] threadsUnsafe = new Thread[10];
        for (int i = 0; i < 10; i++) {
            threadsUnsafe[i] = new Thread(new AccountWorker(accUnsafe, 100, 1, false), "Worker-Unsafe-" + i);
            threadsUnsafe[i].start();
        }
        for (Thread t : threadsUnsafe) t.join();
        System.out.println("Race Condition Result (Expected 1000): Balance = Rs. " + accUnsafe.getBalance());

        // 2. Synchronized fix demonstration
        SharedAccount accSafe = new SharedAccount("AC_SAFE");
        Thread[] threadsSafe = new Thread[10];
        for (int i = 0; i < 10; i++) {
            threadsSafe[i] = new Thread(new AccountWorker(accSafe, 1000, 1, true), "Worker-Safe-" + i);
            threadsSafe[i].start();
        }
        for (Thread t : threadsSafe) t.join();
        System.out.println("Synchronized Safe Result (10x1000 = 10000): Balance = Rs. " + accSafe.getBalance());
    }
}
