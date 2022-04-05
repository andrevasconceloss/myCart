package br.dev.vasconcelos.mycart.validation;

import br.dev.vasconcelos.mycart.validation.constraintvalidation.CategoryFKValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = CategoryFKValidator.class)
public @interface CategoryFK {
    String message() default "{category_field_invalid}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
