package practical4;

import java.util.regex.Pattern;

class Validator {
    private static final Pattern MOBILE_PATTERN = Pattern.compile("^[6-9][0-9]{9}$");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$");
    private static final Pattern PAN_PATTERN = Pattern.compile("^[A-Z]{5}[0-9]{4}[A-Z]$");
    private static final Pattern IFSC_PATTERN = Pattern.compile("^[A-Z]{4}0[A-Z0-9]{6}$");

    public static boolean isValidMobile(String mobile) {
        return mobile != null && MOBILE_PATTERN.matcher(mobile).matches();
    }

    public static boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }

    public static boolean isValidPan(String pan) {
        return pan != null && PAN_PATTERN.matcher(pan).matches();
    }

    public static boolean isValidIfsc(String ifsc) {
        return ifsc != null && IFSC_PATTERN.matcher(ifsc).matches();
    }
}

enum TransactionType {
    DEPOSIT,
    WITHDRAW,
    TRANSFER
}

record Command(TransactionType type, String accountNumber, long amount) {}

class CommandParser {
    public static Command parse(String line) {
        if (line == null) {
            throw new IllegalArgumentException("Input line cannot be null.");
        }
        String[] parts = line.trim().split("\\s+");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Invalid command format. Expected: <TYPE> <ACCOUNT_NUM> <AMOUNT>");
        }

        TransactionType type = TransactionType.valueOf(parts[0].toUpperCase());
        String accountNum = parts[1];
        long amount = Long.parseLong(parts[2]);

        return new Command(type, accountNum, amount);
    }
}

class AccountDemo {
    private String accountNumber;
    private String ownerName;
    private long balance;

    public AccountDemo(String accountNumber, String ownerName, long balance) {
        this.accountNumber = accountNumber;
        this.ownerName = ownerName;
        this.balance = balance;
    }

    public String getAccountNumber() { return accountNumber; }
    public String getOwnerName() { return ownerName; }
    public long getBalance() { return balance; }
}

class StatementFormatter {
    public static String buildStatement(AccountDemo account) {
        StringBuilder sb = new StringBuilder();
        sb.append("=========================================\n");
        sb.append("           MINIBANK STATEMENT            \n");
        sb.append("=========================================\n");
        sb.append("Account Number : ").append(account.getAccountNumber()).append("\n");
        sb.append("Account Holder : ").append(account.getOwnerName()).append("\n");
        sb.append("Current Balance: Rs. ").append(account.getBalance()).append("\n");
        sb.append("Status         : ACTIVE\n");
        sb.append("=========================================\n");
        return sb.toString();
    }
}

public class MiniBank {
    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println(" MiniBank - Practical 4: Validation & Command Parsing");
        System.out.println(" Student: Vidhi Patel | ID: 25CS082");
        System.out.println("==================================================");

        System.out.println("\n--- Testing Validators (Valid & Invalid inputs) ---");
        System.out.println("isValidMobile(\"9876543210\"): " + Validator.isValidMobile("9876543210"));
        System.out.println("isValidMobile(\"12345\")     : " + Validator.isValidMobile("12345"));

        System.out.println("isValidEmail(\"abc@xyz.com\") : " + Validator.isValidEmail("abc@xyz.com"));
        System.out.println("isValidEmail(\"abc@xyz\")     : " + Validator.isValidEmail("abc@xyz"));

        System.out.println("isValidPan(\"ABCDE1234F\")   : " + Validator.isValidPan("ABCDE1234F"));
        System.out.println("isValidPan(\"12345ABCDE\")   : " + Validator.isValidPan("12345ABCDE"));

        System.out.println("isValidIfsc(\"SBIN0001234\")  : " + Validator.isValidIfsc("SBIN0001234"));
        System.out.println("isValidIfsc(\"SBIN1234\")     : " + Validator.isValidIfsc("SBIN1234"));

        System.out.println("\n--- Testing CommandParser ---");
        String commandInput = "DEPOSIT AC0001 500";
        Command cmd = CommandParser.parse(commandInput);
        System.out.println("Raw Input : \"" + commandInput + "\"");
        System.out.println("Parsed Command Record: " + cmd);
        System.out.println("  -> Type          : " + cmd.type());
        System.out.println("  -> Account Number: " + cmd.accountNumber());
        System.out.println("  -> Amount        : " + cmd.amount());

        System.out.println("\n--- Testing StatementFormatter ---");
        AccountDemo testAcc = new AccountDemo("AC0001", "Krisha Patel", 15500);
        System.out.println(StatementFormatter.buildStatement(testAcc));
    }
}
