package bg.softuni.hangman.validation;

import bg.softuni.hangman.repository.PlayerRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class UniqueEmailCheck implements ConstraintValidator<UniqueEmail, String> {
    private final PlayerRepository playerRepository;

    @Autowired
    public UniqueEmailCheck(PlayerRepository playerRepository) {
        this.playerRepository = playerRepository;
    }

    @Override
    public void initialize(UniqueEmail constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        return this.playerRepository.findByEmail(email).isEmpty();
    }
}