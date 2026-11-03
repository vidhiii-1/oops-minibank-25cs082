package practical11;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

class StorableAccount implements Serializable {
    private static final long serialVersionUID = 1001L;
    private String accountNumber;
    private String ownerName;
    private long balance;

    public StorableAccount(String accountNumber, String ownerName, long balance) {
        this.accountNumber = accountNumber;
        this.ownerName = ownerName;
        this.balance = balance;
    }

    @Override
    public String toString() {
        return accountNumber + " | " + ownerName + " | Rs. " + balance;
    }
}

class StatePersister {
    public static void save(StorableAccount[] accounts, Path file) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(file))) {
            oos.writeObject(accounts);
        }
    }

    public static StorableAccount[] load(Path file) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(file))) {
            return (StorableAccount[]) ois.readObject();
        }
    }
}

class TransactionLog {
    public static void append(Path file, String line) throws IOException {
        Files.writeString(file, line + System.lineSeparator(),
                StandardOpenOption.CREATE, StandardOpenOption.APPEND);
    }
}

class ReportGenerator {
    public static void generateReport(Path logFile, Path reportFile) throws IOException {
        long totalDeposits = 0;
        long totalWithdrawals = 0;

        try (BufferedReader reader = Files.newBufferedReader(logFile)) {
            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue;
                String[] parts = line.split("\\s+");
                if (parts.length >= 3) {
                    String op = parts[0];
                    long amount = Long.parseLong(parts[2]);
                    if (op.equalsIgnoreCase("DEPOSIT")) {
                        totalDeposits += amount;
                    } else if (op.equalsIgnoreCase("WITHDRAW")) {
                        totalWithdrawals += amount;
                    }
                }
            }
        }

        String report = "=== END-OF-DAY FINANCIAL SUMMARY ===\n" +
                "Total Deposits    : Rs. " + totalDeposits + "\n" +
                "Total Withdrawals : Rs. " + totalWithdrawals + "\n" +
                "Net Liquidity     : Rs. " + (totalDeposits - totalWithdrawals) + "\n";

        Files.writeString(reportFile, report, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        System.out.print(report);
    }
}

public class MiniBank {
    public static void main(String[] args) throws Exception {
        System.out.println("==================================================");
        System.out.println(" MiniBank - Practical 11: File I/O & Persistence");
        System.out.println(" Student: Vidhi Patel | ID: 25CS082");
        System.out.println("==================================================");

        Path dataFile = Paths.get("d:/WDF____/Practical_File_25CS069/accounts.dat");
        Path logFile = Paths.get("d:/WDF____/Practical_File_25CS069/transactions.log");
        Path reportFile = Paths.get("d:/WDF____/Practical_File_25CS069/eod_report.txt");

        StorableAccount[] accounts = {
            new StorableAccount("AC0001", "Vidhi Patel", 12000),
            new StorableAccount("AC0002", "Riya Shah", 8500),
            new StorableAccount("AC0003", "Aarav Mehta", 3000)
        };

        System.out.println("\nSaving accounts to binary storage...");
        StatePersister.save(accounts, dataFile);

        System.out.println("Loading accounts back into memory...");
        StorableAccount[] loaded = StatePersister.load(dataFile);
        for (StorableAccount acc : loaded) {
            System.out.println("  Loaded: " + acc);
        }

        System.out.println("\nLogging financial transactions...");
        TransactionLog.append(logFile, "DEPOSIT AC0001 5000");
        TransactionLog.append(logFile, "WITHDRAW AC0002 2000");
        TransactionLog.append(logFile, "DEPOSIT AC0003 1500");

        System.out.println("\nGenerating End-Of-Day Audit Report via NIO:");
        ReportGenerator.generateReport(logFile, reportFile);

        // cleanup generated temporary files
        Files.deleteIfExists(dataFile);
        Files.deleteIfExists(logFile);
        Files.deleteIfExists(reportFile);
    }
}
