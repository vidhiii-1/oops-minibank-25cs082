package practical4;

import java.util.regex.Pattern;

class PasswordChecker {
    private static final Pattern UPPER_CASE = Pattern.compile(".*[A-Z].*");
    private static final Pattern DIGIT = Pattern.compile(".*[0-9].*");
    private static final Pattern SPECIAL_CHAR = Pattern.compile(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>/?].*");

    public static String strength(String pw) {
        if (pw == null) return "Weak";
        int passedRules = 0;

        if (pw.length() >= 8) passedRules++;
        if (UPPER_CASE.matcher(pw).matches()) passedRules++;
        if (DIGIT.matcher(pw).matches()) passedRules++;
        if (SPECIAL_CHAR.matcher(pw).matches()) passedRules++;

        if (passedRules >= 4) {
            return "Strong (" + passedRules + "/4 rules passed)";
        } else if (passedRules >= 2) {
            return "Medium (" + passedRules + "/4 rules passed)";
        } else {
            return "Weak (" + passedRules + "/4 rules passed)";
        }
    }
}

public class PasswordDriver {
    public static void main(String[] args) {
        System.out.println("=== Practical 4 - Task 1: Password Strength Checker ===");
        String[] testPasswords = {
            "abc",
            "password123",
            "Abcd1234",
            "Abcd1234!"
        };

        for (String pw : testPasswords) {
            System.out.println("\"" + pw + "\" -> " + PasswordChecker.strength(pw));
        }
    }
}
