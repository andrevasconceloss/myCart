package br.dev.vasconcelos.mycart.validation;

import br.dev.vasconcelos.mycart.validation.constraintvalidation.UserEmailUCValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = UserEmailUCValidator.class)
public @interface EmailUniqueConstraint {
    String message() default "Record already exists";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
