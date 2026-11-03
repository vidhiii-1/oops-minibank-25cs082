package practical13;

class Box<T> {
    private T item;
    public void set(T item) { this.item = item; }
    public T get() { return item; }
}

class Pair<A, B> {
    private A first;
    private B second;
    public Pair(A first, B second) {
        this.first = first;
        this.second = second;
    }
    public A getFirst() { return first; }
    public B getSecond() { return second; }
}

public class GenericContainersDriver {
    public static <E> E getFirst(E[] array) {
        if (array == null || array.length == 0) return null;
        return array[0];
    }

    public static void main(String[] args) {
        System.out.println("=== Practical 13 - Task 1: Generic Containers ===");

        Box<String> strBox = new Box<>();
        strBox.set("Hello Generics");
        System.out.println("String Box contains: " + strBox.get());

        Box<Integer> intBox = new Box<>();
        intBox.set(100);
        System.out.println("Integer Box contains: " + intBox.get());

        Pair<String, Integer> studentRank = new Pair<>("Krisha Patel", 1);
        System.out.println("Pair: Name = " + studentRank.getFirst() + " | Rank = " + studentRank.getSecond());

        String[] fruits = {"Apple", "Banana", "Cherry"};
        System.out.println("First element in array: " + getFirst(fruits));
    }
}
