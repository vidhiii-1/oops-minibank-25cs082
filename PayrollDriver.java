package practical5;

abstract class Employee {
    private String id;
    private String name;

    public Employee(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() { return id; }
    public String getName() { return name; }

    public abstract double monthlySalary();
}

class FullTime extends Employee {
    private double fixedSalary;

    public FullTime(String id, String name, double fixedSalary) {
        super(id, name);
        this.fixedSalary = fixedSalary;
    }

    @Override
    public double monthlySalary() {
        return fixedSalary;
    }
}

class PartTime extends Employee {
    private double hours;
    private double hourlyRate;

    public PartTime(String id, String name, double hours, double hourlyRate) {
        super(id, name);
        this.hours = hours;
        this.hourlyRate = hourlyRate;
    }

    @Override
    public double monthlySalary() {
        return hours * hourlyRate;
    }
}

class Intern extends Employee {
    private double stipend;

    public Intern(String id, String name, double stipend) {
        super(id, name);
        this.stipend = stipend;
    }

    @Override
    public double monthlySalary() {
        return stipend;
    }
}

public class PayrollDriver {
    public static void main(String[] args) {
        System.out.println("=== Practical 5 - Task 2: Payroll System Polymorphism ===");
        Employee[] staff = {
            new FullTime("EMP001", "Krisha Patel", 75000),
            new PartTime("EMP002", "Amit Shah", 80, 450),
            new Intern("EMP003", "Neha Sharma", 15000),
            new FullTime("EMP004", "Rohan Joshi", 60000)
        };

        double totalPayroll = 0;

        for (Employee emp : staff) {
            double salary = emp.monthlySalary();
            totalPayroll += salary;
            System.out.printf("[%s] %-14s : Salary = Rs. %9.2f", emp.getId(), emp.getName(), salary);

            if (emp instanceof Intern) {
                System.out.print("  <-- [Note: Intern stipend - evaluation pending]");
            }
            System.out.println();
        }

        System.out.println("---------------------------------------------------------");
        System.out.printf("Total Monthly Payroll: Rs. %.2f\n", totalPayroll);
    }
}
