package practical3;

import java.util.Objects;

class Point {
    private int x;
    private int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() { return x; }
    public int getY() { return y; }

    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Point point = (Point) obj;
        return x == point.x && y == point.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}

public class DistinctPointsDriver {
    public static void main(String[] args) {
        System.out.println("=== Practical 3 - Task 1: Distinct Points ===");
        Point[] points = {
            new Point(1, 2),
            new Point(3, 4),
            new Point(1, 2), // duplicate
            new Point(5, 6),
            new Point(3, 4)  // duplicate
        };

        System.out.print("Input Points array: ");
        for (Point p : points) {
            System.out.print(p + " ");
        }
        System.out.println();

        int distinctCount = 0;
        for (int i = 0; i < points.length; i++) {
            boolean isRepeated = false;
            for (int j = 0; j < i; j++) {
                if (points[i].equals(points[j])) {
                    isRepeated = true;
                    break;
                }
            }
            if (!isRepeated) {
                distinctCount++;
            }
        }

        System.out.println("Distinct: " + distinctCount);
    }
}
