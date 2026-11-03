package practical5;

abstract class Shape {
    private String name;

    public Shape(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public abstract double area();
}

class Circle extends Shape {
    private double radius;

    public Circle(double radius) {
        super("Circle");
        this.radius = radius;
    }

    @Override
    public double area() {
        return Math.PI * radius * radius;
    }
}

class Rectangle extends Shape {
    private double length;
    private double width;

    public Rectangle(double length, double width) {
        super("Rectangle");
        this.length = length;
        this.width = width;
    }

    @Override
    public double area() {
        return length * width;
    }
}

class Triangle extends Shape {
    private double base;
    private double height;

    public Triangle(double base, double height) {
        super("Triangle");
        this.base = base;
        this.height = height;
    }

    @Override
    public double area() {
        return 0.5 * base * height;
    }
}

public class ShapeDriver {
    public static void main(String[] args) {
        System.out.println("=== Practical 5 - Task 1: Shape Hierarchy Polymorphism ===");
        Shape[] shapes = {
            new Circle(5.0),
            new Rectangle(4.0, 6.0),
            new Triangle(3.0, 8.0),
            new Circle(3.0)
        };

        double totalArea = 0;
        Shape largestShape = shapes[0];

        for (Shape s : shapes) {
            double a = s.area();
            totalArea += a;
            System.out.printf("%-10s : Area = %8.2f | Running Total = %8.2f\n", s.getName(), a, totalArea);
            if (a > largestShape.area()) {
                largestShape = s;
            }
        }

        System.out.println("--------------------------------------------------");
        System.out.printf("Total Area: %.2f\n", totalArea);
        System.out.printf("Largest Shape: %s with Area: %.2f\n", largestShape.getName(), largestShape.area());
    }
}
