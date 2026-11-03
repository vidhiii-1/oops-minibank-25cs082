package practical2;

public class Thermostat {
    private String location;
    private int temperature;
    private static final int MIN = 16;
    private static final int MAX = 30;
    private static int activeCount = 0;

    public Thermostat(String location, int startTemp) {
        this.location = location;
        if (startTemp >= MIN && startTemp <= MAX) {
            this.temperature = startTemp;
        } else {
            this.temperature = 22;
        }
        activeCount++;
    }

    public Thermostat(String location) {
        this(location, 22);
    }

    public void raise() {
        if (temperature < MAX) {
            temperature++;
        } else {
            System.out.println("Already at maximum (" + MAX + ")");
        }
    }

    public void lower() {
        if (temperature > MIN) {
            temperature--;
        } else {
            System.out.println("Already at minimum (" + MIN + ")");
        }
    }

    public int getTemperature() {
        return temperature;
    }

    public String getLocation() {
        return location;
    }

    public static int getActiveCount() {
        return activeCount;
    }

    public static void main(String[] args) {
        System.out.println("=== Thermostat Simulation ===");
        Thermostat t1 = new Thermostat("Living Room", 24);
        Thermostat t2 = new Thermostat("Bedroom");

        System.out.println("Testing Thermostat 1 at: " + t1.getLocation());
        System.out.println("Initial Temperature: " + t1.getTemperature());

        System.out.println("\nRaising temperature 10 times:");
        for (int i = 1; i <= 10; i++) {
            t1.raise();
            System.out.println("Step " + i + ": Temp = " + t1.getTemperature());
        }

        System.out.println("\nLowering temperature 20 times:");
        for (int i = 1; i <= 20; i++) {
            t1.lower();
            System.out.println("Step " + i + ": Temp = " + t1.getTemperature());
        }

        System.out.println("\nTotal active thermostats count: " + Thermostat.getActiveCount());
    }
}
