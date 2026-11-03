package practical10;

import java.util.concurrent.*;

public class ThreadPoolDriver {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== Practical 10 - Task 1: Thread-Pool Runner ===");
        ExecutorService pool = Executors.newFixedThreadPool(3);

        for (int i = 1; i <= 8; i++) {
            final int taskId = i;
            pool.submit(() -> {
                System.out.println("Task " + taskId + " running on Thread: " + Thread.currentThread().getName());
                try { Thread.sleep(20); } catch (InterruptedException ignored) {}
            });
        }

        pool.shutdown();
        boolean finished = pool.awaitTermination(2, TimeUnit.SECONDS);
        System.out.println("All tasks finished on thread pool: " + finished);
    }
}
