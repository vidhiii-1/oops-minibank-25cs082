package practical6;

interface Urgent {}

@FunctionalInterface
interface Notifier {
    void send(String message);
}

class EmailNotifier implements Notifier, Urgent {
    @Override
    public void send(String msg) {
        System.out.println("[EMAIL NOTIFICATION] " + msg);
    }
}

class SmsNotifier implements Notifier {
    @Override
    public void send(String msg) {
        System.out.println("[SMS ALERT] " + msg);
    }
}

public class NotificationDriver {
    public static void main(String[] args) {
        System.out.println("=== Practical 6 - Task 2: Notification Senders ===");
        Notifier[] notifiers = {
            new EmailNotifier(),
            new SmsNotifier(),
            (msg) -> System.out.println("[PUSH NOTIFICATION] " + msg)
        };

        String alertMsg = "Critical security alert: New login detected.";
        for (Notifier n : notifiers) {
            n.send(alertMsg);
            if (n instanceof Urgent) {
                System.out.println("--> High priority detected! Sending duplicate confirmation...");
                n.send(alertMsg);
            }
        }
    }
}
