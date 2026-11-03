package practical8;

class DivideByZeroException extends Exception {
    public DivideByZeroException(String message) {
        super(message);
    }
}

public class GuardedCalculator {
    public static double calculate(String num1Str, String num2Str, char op) throws DivideByZeroException {
        double a = Double.parseDouble(num1Str);
        double b = Double.parseDouble(num2Str);

        return switch (op) {
            case '+' -> a + b;
            case '-' -> a - b;
            case '*' -> a * b;
            case '/' -> {
                if (b == 0) throw new DivideByZeroException("Attempted division by zero!");
                yield a / b;
            }
            default -> throw new IllegalArgumentException("Unknown operator: " + op);
        };
    }

    public static void main(String[] args) {
        System.out.println("=== Practical 8 - Task 1: Guarded Calculator ===");
        String[][] testCases = {
            {"10", "2", "/"},
            {"10", "0", "/"},
            {"abc", "5", "+"}
        };

        for (String[] test : testCases) {
            System.out.println("\nAttempting operation: " + test[0] + " " + test[2] + " " + test[1]);
            try {
                double res = calculate(test[0], test[1], test[2].charAt(0));
                System.out.println("Result = " + res);
            } catch (DivideByZeroException e) {
                System.out.println("Caught Custom Exception: " + e.getMessage());
            } catch (NumberFormatException e) {
                System.out.println("Caught NumberFormatException: Invalid numeric input.");
            } finally {
                System.out.println("Finally block executed: Operation attempt logged.");
            }
        }
    }
}
