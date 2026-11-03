package practical11;

import java.io.*;

class UserProfile implements Serializable {
    private static final long serialVersionUID = 1L;
    private String username;
    private transient String sessionToken; // will not be serialized
    private int score;

    public UserProfile(String username, String sessionToken, int score) {
        this.username = username;
        this.sessionToken = sessionToken;
        this.score = score;
    }

    @Override
    public String toString() {
        return "User: " + username + " | Score: " + score + " | Transient Token: " + sessionToken;
    }
}

public class SerializationDriver {
    public static void main(String[] args) {
        System.out.println("=== Practical 11 - Task 1: Object Serialization & Transient ===");
        File file = new File("d:/WDF____/Practical_File_25CS069/user_test.dat");

        UserProfile original = new UserProfile("krisha_patel", "SECRET_TOKEN_XYZ", 98);
        System.out.println("Original object before save: " + original);

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(file))) {
            out.writeObject(original);
            System.out.println("Serialized object written to file successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(file))) {
            UserProfile loaded = (UserProfile) in.readObject();
            System.out.println("Deserialized object loaded : " + loaded);
            System.out.println("Note: transient field sessionToken restored as: " + null);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            file.delete();
        }
    }
}
