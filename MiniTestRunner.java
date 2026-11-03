package practical7;

import java.lang.annotation.*;
import java.lang.reflect.Method;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface Run {}

class TestSuite {
    @Run
    public void testDatabaseConnection() {
        System.out.println("Executing: testDatabaseConnection - PASS");
    }

    public void helperMethod() {
        System.out.println("Executing helper (should not run automatically)");
    }

    @Run
    public void testAuthentication() {
        System.out.println("Executing: testAuthentication - PASS");
    }

    @Run
    public void testBalanceCalculation() {
        System.out.println("Executing: testBalanceCalculation - PASS");
    }
}

public class MiniTestRunner {
    public static void main(String[] args) {
        System.out.println("=== Practical 7 - Task 2: Mini Test Runner (@Run) ===");
        TestSuite suite = new TestSuite();
        Method[] methods = suite.getClass().getDeclaredMethods();

        int executedCount = 0;
        for (Method m : methods) {
            if (m.isAnnotationPresent(Run.class)) {
                try {
                    m.invoke(suite);
                    executedCount++;
                } catch (Exception e) {
                    System.out.println("Failed executing test: " + m.getName());
                }
            }
        }
        System.out.println("Total @Run test methods executed: " + executedCount);
    }
}
