package practical10;

import java.util.LinkedList;
import java.util.Queue;

class Buffer {
    private Queue<Integer> queue = new LinkedList<>();
    private final int CAPACITY = 3;

    public synchronized void produce(int value) throws InterruptedException {
        while (queue.size() == CAPACITY) {
            wait();
        }
        queue.add(value);
        System.out.println("Produced: " + value + " | Buffer size: " + queue.size());
        notifyAll();
    }

    public synchronized int consume() throws InterruptedException {
        while (queue.isEmpty()) {
            wait();
        }
        int value = queue.poll();
        System.out.println("Consumed: " + value + " | Buffer size: " + queue.size());
        notifyAll();
        return value;
    }
}

public class ProducerConsumerDriver {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== Practical 10 - Task 2: Producer-Consumer with wait/notify ===");
        Buffer buffer = new Buffer();

        Thread producer = new Thread(() -> {
            try {
                for (int i = 1; i <= 5; i++) {
                    buffer.produce(i);
                    Thread.sleep(10);
                }
            } catch (InterruptedException ignored) {}
        });

        Thread consumer = new Thread(() -> {
            try {
                for (int i = 1; i <= 5; i++) {
                    buffer.consume();
                    Thread.sleep(15);
                }
            } catch (InterruptedException ignored) {}
        });

        producer.start();
        consumer.start();
        producer.join();
        consumer.join();
        System.out.println("Producer-Consumer coordination completed successfully without data loss.");
    }
}
