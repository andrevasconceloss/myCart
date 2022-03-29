package br.dev.vasconcelos.mycart.validation.constraintvalidation;

import br.dev.vasconcelos.mycart.domain.entity.UserProfile;
import br.dev.vasconcelos.mycart.domain.repository.UserProfileRepository;
import br.dev.vasconcelos.mycart.exception.UniqueConstraintException;
import br.dev.vasconcelos.mycart.validation.UniqueContraint;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class UniqueConstraintValidator implements ConstraintValidator<UniqueContraint, String> {

    @Autowired
    private UserProfileRepository repository;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        Optional<UserProfile> user = repository.findByEmail(email);
        return (!user.isPresent());
    }

    @Override
    public void initialize(UniqueContraint constraintAnnotation) {
        constraintAnnotation.message();
    }
}
