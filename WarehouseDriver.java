package practical8;

class OutOfStockException extends Exception {
    private int shortfall;
    public OutOfStockException(String item, int shortfall) {
        super("Item '" + item + "' out of stock! Shortfall: " + shortfall);
        this.shortfall = shortfall;
    }
    public int getShortfall() { return shortfall; }
}

class InvalidQuantityException extends Exception {
    public InvalidQuantityException(String msg) {
        super(msg);
    }
}

class Warehouse {
    private int stock = 20;

    public void issue(String item, int qty) throws OutOfStockException, InvalidQuantityException {
        if (qty <= 0) {
            throw new InvalidQuantityException("Quantity must be positive (received: " + qty + ")");
        }
        if (qty > stock) {
            int shortfall = qty - stock;
            throw new OutOfStockException(item, shortfall);
        }
        stock -= qty;
        System.out.println("Issued " + qty + " of " + item + ". Remaining stock: " + stock);
    }
}

public class WarehouseDriver {
    public static void main(String[] args) {
        System.out.println("=== Practical 8 - Task 2: Warehouse Stock Exceptions ===");
        Warehouse warehouse = new Warehouse();
        int[] requests = { 5, -2, 25, 10 };

        for (int req : requests) {
            try {
                System.out.println("\nRequesting " + req + " items:");
                warehouse.issue("Keyboard", req);
            } catch (OutOfStockException e) {
                System.out.println("STOCK ERROR: " + e.getMessage() + " (Shortfall = " + e.getShortfall() + ")");
            } catch (InvalidQuantityException e) {
                System.out.println("INPUT ERROR: " + e.getMessage());
            }
        }
    }
}
