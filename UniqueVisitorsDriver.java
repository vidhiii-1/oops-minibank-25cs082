package practical12;

import java.util.*;

public class UniqueVisitorsDriver {
    public static void main(String[] args) {
        System.out.println("=== Practical 12 - Task 2: Unique Visitors (Set Implementations) ===");
        List<String> logins = List.of("krisha", "aarav", "riya", "krisha", "kabir", "aarav", "diya");

        Set<String> uniqueSet = new HashSet<>(logins);
        Set<String> insertionOrderedSet = new LinkedHashSet<>(logins);

        System.out.println("Total login attempts: " + logins.size());
        System.out.println("Unique active users count: " + uniqueSet.size());
        System.out.println("First three distinct users seen:");
        int count = 0;
        for (String user : insertionOrderedSet) {
            System.out.println("  " + (++count) + ". " + user);
            if (count == 3) break;
        }
    }
}
