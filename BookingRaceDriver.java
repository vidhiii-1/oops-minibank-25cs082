package practical9;

class Theater {
    private int seatsLeft = 5;

    public void bookUnsync(String customer) {
        if (seatsLeft > 0) {
            try { Thread.sleep(10); } catch (InterruptedException ignored) {}
            seatsLeft--;
            System.out.println(customer + " booked a seat. Remaining: " + seatsLeft);
        } else {
            System.out.println(customer + " failed to book. House full!");
        }
    }

    public synchronized void bookSync(String customer) {
        if (seatsLeft > 0) {
            try { Thread.sleep(10); } catch (InterruptedException ignored) {}
            seatsLeft--;
            System.out.println(customer + " successfully booked! Remaining: " + seatsLeft);
        } else {
            System.out.println(customer + " failed to book. House full!");
        }
    }

    public int getSeatsLeft() { return seatsLeft; }
}

public class BookingRaceDriver {
    public static void main(String[] args) throws InterruptedException {
        System.out.println("=== Practical 9 - Task 2: Seat Booking Race ===");
        Theater theater = new Theater();
        Thread[] threads = new Thread[10];
        for (int i = 0; i < 10; i++) {
            final String cust = "Customer-" + (i + 1);
            threads[i] = new Thread(() -> theater.bookSync(cust));
            threads[i].start();
        }
        for (Thread t : threads) t.join();
        System.out.println("Booking complete. Final seats left: " + theater.getSeatsLeft());
    }
}
