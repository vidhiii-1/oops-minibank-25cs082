package practical1;

import java.util.Scanner;

record Vehicle(String number, String type) {}

public class TollBooth {
    public static void main(String[] args) {
        System.out.println("=== Toll Booth Management ===");
        int totalToll = 0;
        int bikeCount = 0;
        int carCount = 0;
        int truckCount = 0;

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Enter vehicle number (or 'done' to finish): ");
            if (!scanner.hasNext()) break;
            String number = scanner.next();
            if (number.equalsIgnoreCase("done")) {
                break;
            }

            System.out.print("Enter vehicle type (bike/car/truck): ");
            String type = scanner.next().trim().toLowerCase();

            Vehicle vehicle = new Vehicle(number, type);

            int toll = switch (vehicle.type()) {
                case "bike" -> {
                    bikeCount++;
                    yield 20;
                }
                case "car" -> {
                    carCount++;
                    yield 50;
                }
                case "truck" -> {
                    truckCount++;
                    yield 150;
                }
                default -> {
                    System.out.println("Unknown vehicle type! Toll charged: 0");
                    yield 0;
                }
            };

            totalToll += toll;
            System.out.println("Vehicle [" + vehicle.number() + " (" + vehicle.type() + ")] charged: Rs. " + toll);
        }

        System.out.println("\n--- Summary ---");
        System.out.println("Total toll: " + totalToll);

        String mostFrequent;
        int max = Math.max(bikeCount, Math.max(carCount, truckCount));
        if (max == 0) {
            mostFrequent = "none";
        } else if (carCount == max) {
            mostFrequent = "car";
        } else if (bikeCount == max) {
            mostFrequent = "bike";
        } else {
            mostFrequent = "truck";
        }

        System.out.println("Most frequent: " + mostFrequent + " (Bikes: " + bikeCount + ", Cars: " + carCount + ", Trucks: " + truckCount + ")");
    }
}
