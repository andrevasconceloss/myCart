package br.dev.vasconcelos.mycart.validation.constraintvalidation;

import br.dev.vasconcelos.mycart.domain.entity.ProductCategory;
import br.dev.vasconcelos.mycart.domain.repository.ProductCategoryRepository;
import br.dev.vasconcelos.mycart.validation.CategoryFK;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class CategoryFKValidator implements ConstraintValidator<CategoryFK, Integer> {

    @Autowired
    private ProductCategoryRepository repository;

    @Override
    public void initialize(CategoryFK constraintAnnotation) {
        constraintAnnotation.message();
    }

    @Override
    public boolean isValid(Integer id, ConstraintValidatorContext constraintValidatorContext) {
        if (id == null || id.equals("")) {
            return false;
        }

        Optional<ProductCategory> category = repository.findById(id);
        return category.isPresent();
    }
}
