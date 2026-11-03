package practical1;

import java.util.Scanner;

enum Coin {
    ONE, TWO, FIVE, TEN
}

public class VendingMachine {
    public static void main(String[] args) {
        System.out.println("=== Vending Machine ===");
        int snackPrice = 15;
        int total = 0;
        Scanner scanner = new Scanner(System.in);

        while (total < snackPrice) {
            System.out.print("Enter coin (ONE, TWO, FIVE, TEN): ");
            if (!scanner.hasNext()) break;
            String input = scanner.next().trim().toUpperCase();
            try {
                Coin coin = Coin.valueOf(input);
                int value = switch (coin) {
                    case ONE -> 1;
                    case TWO -> 2;
                    case FIVE -> 5;
                    case TEN -> 10;
                };
                total += value;
                System.out.println("Inserted: " + value + " | Total so far: " + total);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid coin! Please insert ONE, TWO, FIVE, or TEN.");
            }
        }

        int change = total - snackPrice;
        System.out.println("Paid. Change: " + change);
    }
}
