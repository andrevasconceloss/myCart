package br.dev.vasconcelos.mycart.validation.constraintvalidation;

import br.dev.vasconcelos.mycart.domain.entity.UserProfile;
import br.dev.vasconcelos.mycart.domain.repository.UserProfileRepository;
import br.dev.vasconcelos.mycart.validation.EmailUniqueConstraint;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class UserEmailUCValidator implements ConstraintValidator<EmailUniqueConstraint, String> {

    @Autowired
    private UserProfileRepository repository;

    @Override
    public void initialize(EmailUniqueConstraint constraintAnnotation) {
        constraintAnnotation.message();
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        Optional<UserProfile> user = repository.findByEmail(email);
        return (!user.isPresent());
    }
}
