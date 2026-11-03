package practical7;

import java.lang.annotation.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface Id {}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface Positive {
    String message() default "must be > 0";
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface MaxLengthConstraint {
    int value();
}

class AccountModel {
    @Id
    private String accountNumber;

    @MaxLengthConstraint(15)
    private String ownerName;

    @Positive
    private long balance;

    public AccountModel(String accountNumber, String ownerName, long balance) {
        this.accountNumber = accountNumber;
        this.ownerName = ownerName;
        this.balance = balance;
    }

    public String getAccountNumber() { return accountNumber; }
    public String getOwnerName() { return ownerName; }
    public long getBalance() { return balance; }
}

class AnnotationValidator {
    public static String[] validate(Object obj) {
        List<String> errors = new ArrayList<>();
        Field[] fields = obj.getClass().getDeclaredFields();

        for (Field f : fields) {
            f.setAccessible(true);
            try {
                if (f.isAnnotationPresent(Positive.class)) {
                    Positive pos = f.getAnnotation(Positive.class);
                    Object val = f.get(obj);
                    if (val instanceof Number num) {
                        if (num.doubleValue() <= 0) {
                            errors.add(f.getName() + " " + pos.message());
                        }
                    }
                }
                if (f.isAnnotationPresent(MaxLengthConstraint.class)) {
                    MaxLengthConstraint ml = f.getAnnotation(MaxLengthConstraint.class);
                    Object val = f.get(obj);
                    if (val instanceof String s && s.length() > ml.value()) {
                        errors.add(f.getName() + " exceeds max length of " + ml.value());
                    }
                }
            } catch (IllegalAccessException e) {
                errors.add("Access error on: " + f.getName());
            }
        }
        return errors.toArray(new String[0]);
    }
}

public class MiniBank {
    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println(" MiniBank - Practical 7: Metadata & Reflection");
        System.out.println(" Student: Vidhi Patel | ID: 25CS082");
        System.out.println("==================================================");

        AccountModel invalidAcc = new AccountModel("AC0001", "ThisIsAVeryLongCustomerNameExceedingAllowedLength", -500);
        AccountModel validAcc = new AccountModel("AC0002", "Vidhi Patel", 15000);

        System.out.println("\nValidating Invalid Account Object (balance = -500):");
        String[] errors = AnnotationValidator.validate(invalidAcc);
        for (String err : errors) {
            System.out.println("  [VALIDATION ERROR] " + err);
        }

        System.out.println("\nValidating Valid Account Object (balance = 15000):");
        String[] validErrors = AnnotationValidator.validate(validAcc);
        if (validErrors.length == 0) {
            System.out.println("  All annotations passed! Account is completely valid.");
        }
    }
}
