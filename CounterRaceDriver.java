package practical9;

class Counter {
    private int count = 0;

    public void incrementUnsync() {
        count++;
    }

    public synchronized void incrementSync() {
        count++;
    }

    public int getCount() { return count; }
}

public class CounterRaceDriver {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== Practical 9 - Task 1: Counter Race Condition ===");

        // 1. Unsynchronized Race
        Counter unsyncCounter = new Counter();
        Thread[] tUnsync = new Thread[10];
        for (int i = 0; i < 10; i++) {
            tUnsync[i] = new Thread(() -> {
                for (int j = 0; j < 1000; j++) unsyncCounter.incrementUnsync();
            });
            tUnsync[i].start();
        }
        for (Thread t : tUnsync) t.join();
        System.out.println("Without Synchronization (Expected 10,000): Result = " + unsyncCounter.getCount());

        // 2. Synchronized Fix
        Counter syncCounter = new Counter();
        Thread[] tSync = new Thread[10];
        for (int i = 0; i < 10; i++) {
            tSync[i] = new Thread(() -> {
                for (int j = 0; j < 1000; j++) syncCounter.incrementSync();
            });
            tSync[i].start();
        }
        for (Thread t : tSync) t.join();
        System.out.println("With Synchronization (Thread-Safe):       Result = " + syncCounter.getCount());
    }
}
