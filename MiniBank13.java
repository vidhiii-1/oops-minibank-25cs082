package practical13;

import java.lang.annotation.*;
import java.lang.reflect.Field;
import java.util.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface Id {}

class AccountEntity {
    @Id
    private String accountNumber;
    private String ownerName;
    private long balance;

    public AccountEntity(String accountNumber, String ownerName, long balance) {
        this.accountNumber = accountNumber;
        this.ownerName = ownerName;
        this.balance = balance;
    }

    public String getAccountNumber() { return accountNumber; }
    public String getOwnerName() { return ownerName; }
    public long getBalance() { return balance; }

    @Override
    public String toString() {
        return accountNumber + " | " + ownerName + " | Rs. " + balance;
    }
}

class Repository<T> {
    private Map<String, T> storage = new HashMap<>();

    public T save(T entity) {
        if (entity == null) return null;
        String idKey = extractId(entity);
        storage.put(idKey, entity);
        return entity;
    }

    @SafeVarargs
    public final void saveAll(T... items) {
        for (T item : items) {
            save(item);
        }
    }

    public T findById(String id) {
        return storage.get(id);
    }

    public List<T> findAll() {
        return new ArrayList<>(storage.values());
    }

    public boolean delete(String id) {
        return storage.remove(id) != null;
    }

    private String extractId(T entity) {
        for (Field f : entity.getClass().getDeclaredFields()) {
            if (f.isAnnotationPresent(Id.class)) {
                f.setAccessible(true);
                try {
                    Object val = f.get(entity);
                    return String.valueOf(val);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Cannot access @Id field", e);
                }
            }
        }
        throw new IllegalArgumentException("No @Id field found on entity: " + entity.getClass().getName());
    }
}

public class MiniBank {
    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println(" MiniBank - Practical 13: Generic Repository Pattern");
        System.out.println(" Student: Vidhi Patel | ID: 25CS082");
        System.out.println("==================================================");

        Repository<AccountEntity> repo = new Repository<>();
        AccountEntity a1 = new AccountEntity("AC0001", "Vidhi Patel", 12000);
        AccountEntity a2 = new AccountEntity("AC0002", "Riya Shah", 9000);
        AccountEntity a3 = new AccountEntity("AC0003", "Aarav Mehta", 4500);

        repo.saveAll(a1, a2, a3);

        System.out.println("\n--- Looking up Account by @Id reflective key 'AC0002' ---");
        AccountEntity found = repo.findById("AC0002");
        System.out.println("Found Entity: " + found);

        System.out.println("\n--- All Entities in Generic Repository ---");
        for (AccountEntity acc : repo.findAll()) {
            System.out.println("  " + acc);
        }
    }
}
