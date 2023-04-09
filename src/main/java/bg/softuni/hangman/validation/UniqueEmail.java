package bg.softuni.hangman.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = UniqueEmailCheck.class)
public @interface UniqueEmail {
    String message() default "Имейлът вече съществува";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
