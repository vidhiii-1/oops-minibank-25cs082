package practical3;

import java.util.Objects;

class Customer implements Cloneable {
    private String name;
    private String email;
    private String mobile;
    private final String customerId;
    private Address address;

    private static long customerCounter = 100;

    private static synchronized String generateCustomerId() {
        customerCounter++;
        return "CUST" + customerCounter;
    }

    // Static nested class Address
    public static class Address implements Cloneable {
        private String line;
        private String city;
        private String pincode;

        public Address(String line, String city, String pincode) {
            this.line = line;
            this.city = city;
            this.pincode = pincode;
        }

        public String getLine() { return line; }
        public String getCity() { return city; }
        public String getPincode() { return pincode; }

        @Override
        public String toString() {
            return line + ", " + city + " - " + pincode;
        }

        @Override
        public Address clone() {
            try {
                return (Address) super.clone();
            } catch (CloneNotSupportedException e) {
                return new Address(this.line, this.city, this.pincode);
            }
        }
    }

    public Customer(String name, String email, String mobile, Address address) {
        this.customerId = generateCustomerId();
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.address = address;
    }

    public String getCustomerId() { return customerId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getMobile() { return mobile; }
    public Address getAddress() { return address; }
    public void setAddress(Address address) { this.address = address; }

    @Override
    public Customer clone() {
        try {
            Customer copy = (Customer) super.clone();
            if (this.address != null) {
                copy.address = this.address.clone(); // deep copy
            }
            return copy;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError("Clone not supported", e);
        }
    }

    @Override
    public String toString() {
        return "[" + customerId + "] " + name + " (" + email + ", " + mobile + ") Address: " + (address != null ? address : "None");
    }
}

class Account {
    private final String accountNumber;
    private String ownerName;
    private long balance;
    private boolean active;

    public Account(String accountNumber, String ownerName, long balance) {
        this.accountNumber = accountNumber;
        this.ownerName = ownerName;
        this.balance = balance;
        this.active = true;
    }

    public String getAccountNumber() { return accountNumber; }
    public String getOwnerName() { return ownerName; }
    public long getBalance() { return balance; }
    public boolean isActive() { return active; }

    @Override
    public String toString() {
        return accountNumber + " | " + ownerName + " | " + balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(accountNumber, account.accountNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountNumber);
    }
}

public class MiniBank {
    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println(" MiniBank - Practical 3: Object Methods & Nested Classes");
        System.out.println(" Student: Vidhi Patel | ID: 25CS082");
        System.out.println("==================================================");

        Account a1 = new Account("AC0001", "Riya", 4000);
        Account a2 = new Account("AC0001", "Riya Duplicate", 5000);
        Account a3 = new Account("AC0002", "Vidhi", 7500);

        System.out.println("\n--- Testing toString() ---");
        System.out.println("Account 1 toString(): " + a1);
        System.out.println("Account 3 toString(): " + a3);

        System.out.println("\n--- Testing equals() and hashCode() ---");
        System.out.println("a1.equals(a2) [same accountNumber 'AC0001']: " + a1.equals(a2));
        System.out.println("a1.hashCode() == a2.hashCode(): " + (a1.hashCode() == a2.hashCode()));
        System.out.println("a1.equals(a3) [different accountNumber]: " + a1.equals(a3));

        System.out.println("\n--- Testing Static Nested Address & clone() ---");
        Customer.Address addr = new Customer.Address("102 Shivalik Apts", "Ahmedabad", "380015");
        Customer c1 = new Customer("Vidhi Patel", "vidhi@example.com", "9876543210", addr);
        Customer clonedCustomer = c1.clone();

        System.out.println("Original customer: " + c1);
        System.out.println("Cloned customer  : " + clonedCustomer);
        System.out.println("Are c1 and clonedCustomer distinct objects? " + (c1 != clonedCustomer));
        System.out.println("Are addresses deep-copied? " + (c1.getAddress() != clonedCustomer.getAddress()));

        System.out.println("\n--- Testing instanceof Operator ---");
        Object obj1 = a1;
        Object obj2 = c1;
        System.out.println("obj1 instanceof Account : " + (obj1 instanceof Account));
        System.out.println("obj1 instanceof Customer: " + (obj1 instanceof Customer));
        System.out.println("obj2 instanceof Customer: " + (obj2 instanceof Customer));
    }
}
