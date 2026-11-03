package practical7;

import java.lang.annotation.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface NotBlank {
    String message() default "Field must not be blank";
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@interface MaxLength {
    int value();
    String message() default "Exceeds allowed max length";
}

class SignupForm {
    @NotBlank
    @MaxLength(10)
    private String username;

    @NotBlank
    private String email;

    public SignupForm(String username, String email) {
        this.username = username;
        this.email = email;
    }
}

class FormValidator {
    public static List<String> validate(Object obj) {
        List<String> errors = new ArrayList<>();
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field f : fields) {
            f.setAccessible(true);
            try {
                Object val = f.get(obj);
                String strVal = (val == null) ? "" : val.toString().trim();

                if (f.isAnnotationPresent(NotBlank.class)) {
                    if (strVal.isEmpty()) {
                        NotBlank nb = f.getAnnotation(NotBlank.class);
                        errors.add(f.getName() + ": " + nb.message());
                    }
                }
                if (f.isAnnotationPresent(MaxLength.class)) {
                    MaxLength ml = f.getAnnotation(MaxLength.class);
                    if (strVal.length() > ml.value()) {
                        errors.add(f.getName() + ": " + ml.message() + " (" + ml.value() + ")");
                    }
                }
            } catch (IllegalAccessException e) {
                errors.add("Error accessing field: " + f.getName());
            }
        }
        return errors;
    }
}

public class FormDriver {
    public static void main(String[] args) {
        System.out.println("=== Practical 7 - Task 1: Form Validator Reflection ===");
        SignupForm invalidForm = new SignupForm("SuperLongUsernameOverLimit", "");
        List<String> errors = FormValidator.validate(invalidForm);
        System.out.println("Validation errors detected for form submission:");
        for (String err : errors) {
            System.out.println(" - " + err);
        }
    }
}
