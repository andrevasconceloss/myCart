package br.dev.vasconcelos.mycart.validation.constraintvalidation;

import br.dev.vasconcelos.mycart.validation.UniqueContraint;

import javax.persistence.UniqueConstraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueConstraintValidator implements ConstraintValidator<UniqueContraint, Object> {
    @Override
    public boolean isValid(Object o, ConstraintValidatorContext constraintValidatorContext) {
        return false;
    }

    @Override
    public void initialize(UniqueContraint constraintAnnotation) {
        constraintAnnotation.message();
    }
}
