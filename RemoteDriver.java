package practical6;

interface Switchable {
    void on();
    void off();
    default void toggle() {
        System.out.println("Toggling device state...");
    }
}

class Light implements Switchable {
    private boolean state = false;
    @Override
    public void on() { state = true; System.out.println("Light turned ON."); }
    @Override
    public void off() { state = false; System.out.println("Light turned OFF."); }
    @Override
    public void toggle() {
        state = !state;
        System.out.println("Light toggled. State is now: " + (state ? "ON" : "OFF"));
    }
}

class Fan implements Switchable {
    private boolean state = false;
    @Override
    public void on() { state = true; System.out.println("Fan turned ON."); }
    @Override
    public void off() { state = false; System.out.println("Fan turned OFF."); }
    @Override
    public void toggle() {
        state = !state;
        System.out.println("Fan toggled. State is now: " + (state ? "ON" : "OFF"));
    }
}

@FunctionalInterface
interface PowerPolicy {
    boolean allowSwitchOn(String device, int hour);
}

public class RemoteDriver {
    public static void main(String[] args) {
        System.out.println("=== Practical 6 - Task 1: Remote Control Switchable ===");
        Switchable[] devices = { new Light(), new Fan() };
        for (Switchable dev : devices) {
            dev.toggle();
        }

        PowerPolicy policyAnon = new PowerPolicy() {
            @Override
            public boolean allowSwitchOn(String device, int hour) {
                return hour >= 6 && hour <= 23;
            }
        };

        PowerPolicy policyLambda = (device, hour) -> (hour >= 6 && hour <= 22);

        System.out.println("Anonymous class check (Fan at 14:00): " + policyAnon.allowSwitchOn("Fan", 14));
        System.out.println("Lambda expression check (Light at 23:30): " + policyLambda.allowSwitchOn("Light", 23));
    }
}
