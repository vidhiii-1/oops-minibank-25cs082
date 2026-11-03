package practical13;

public class BoundedStatsDriver {
    public static <N extends Number> double average(N[] values) {
        if (values == null || values.length == 0) return 0.0;
        double sum = 0.0;
        for (N val : values) {
            sum += val.doubleValue();
        }
        return sum / values.length;
    }

    public static void main(String[] args) {
        System.out.println("=== Practical 13 - Task 2: Bounded Generics (<N extends Number>) ===");

        Integer[] intArr = { 10, 20, 30, 40, 50 };
        Double[] dblArr = { 2.5, 4.5, 6.0, 7.0 };
        Long[] lngArr = { 1000L, 2000L, 3000L };

        System.out.printf("Integer Array Average : %.2f\n", average(intArr));
        System.out.printf("Double Array Average  : %.2f\n", average(dblArr));
        System.out.printf("Long Array Average    : %.2f\n", average(lngArr));
    }
}
