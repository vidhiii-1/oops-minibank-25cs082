package practical2;

public class CinemaShow {
    private String title;
    private int seatsAvailable;
    private final int capacity;
    private static int totalBooked = 0;

    public CinemaShow(String title, int capacity) {
        this.title = title;
        this.capacity = capacity;
        this.seatsAvailable = capacity;
    }

    public CinemaShow(String title) {
        this(title, 100);
    }

    public boolean book(int n) {
        if (n <= seatsAvailable) {
            seatsAvailable -= n;
            totalBooked += n;
            return true;
        }
        return false;
    }

    public void cancel(int n) {
        seatsAvailable = Math.min(capacity, seatsAvailable + n);
    }

    public int getSeatsAvailable() {
        return seatsAvailable;
    }

    public static int getTotalBooked() {
        return totalBooked;
    }

    public static void main(String[] args) {
        System.out.println("=== Cinema Show Booking System ===");
        CinemaShow show = new CinemaShow("Inception", 50);
        System.out.println("Show initialized with seats: " + show.getSeatsAvailable());

        boolean b1 = show.book(20);
        System.out.println("Booked 20: " + b1 + " | Remaining seats: " + show.getSeatsAvailable());

        boolean b2 = show.book(40);
        System.out.println("Booked 40 (exceeds): " + b2 + " | Remaining seats: " + show.getSeatsAvailable());

        boolean b3 = show.book(15);
        System.out.println("Booked 15: " + b3 + " | Remaining seats: " + show.getSeatsAvailable());

        System.out.println("Cancelling 5 seats...");
        show.cancel(5);
        System.out.println("Remaining seats after cancel: " + show.getSeatsAvailable());

        System.out.println("Total cumulative tickets booked across all shows: " + CinemaShow.getTotalBooked());
    }
}
