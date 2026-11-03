package practical1;

import java.util.Scanner;

record BankInfo(String name, String branch) {}

enum MenuOption {
    OPEN_ACCOUNT,
    DEPOSIT,
    WITHDRAW,
    TRANSFER,
    EXIT
}

public class MiniBank {
    public static void main(String[] args) {
        BankInfo info = new BankInfo("Charusat Apex Bank", "Changa Branch");
        System.out.println("==================================================");
        System.out.println(" Welcome to " + info.name() + " (" + info.branch() + ")");
        System.out.println(" Student: Vidhi Patel | ID: 25CS082");
        System.out.println("==================================================");

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n--- MAIN MENU ---");
            System.out.println("1. Open Account");
            System.out.println("2. Deposit Funds");
            System.out.println("3. Withdraw Funds");
            System.out.println("4. Transfer Funds");
            System.out.println("5. Exit");
            System.out.print("Select an option (1-5): ");

            if (!scanner.hasNextInt()) {
                System.out.println("Invalid input! Please enter a valid number (1-5).");
                scanner.next(); // discard invalid token
                continue;
            }

            int choice = scanner.nextInt();
            MenuOption option = switch (choice) {
                case 1 -> MenuOption.OPEN_ACCOUNT;
                case 2 -> MenuOption.DEPOSIT;
                case 3 -> MenuOption.WITHDRAW;
                case 4 -> MenuOption.TRANSFER;
                case 5 -> MenuOption.EXIT;
                default -> null;
            };

            if (option == null) {
                System.out.println("Error: Option number out of range. Please choose between 1 and 5.");
                continue;
            }

            String message = switch (option) {
                case OPEN_ACCOUNT -> "Open Account — to be implemented in a later lab.";
                case DEPOSIT -> "Deposit — to be implemented in a later lab.";
                case WITHDRAW -> "Withdraw — to be implemented in a later lab.";
                case TRANSFER -> "Transfer — to be implemented in a later lab.";
                case EXIT -> "Thank you for banking with Charusat Apex Bank. Goodbye!";
            };

            System.out.println(">>> " + message);

            if (option == MenuOption.EXIT) {
                running = false;
            }
        }
    }
}
