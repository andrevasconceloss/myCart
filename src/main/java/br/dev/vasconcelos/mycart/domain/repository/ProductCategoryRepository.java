package br.dev.vasconcelos.mycart.domain.repository;

import br.dev.vasconcelos.mycart.domain.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductCategoryRepository extends JpaRepository<ProductCategory, Integer> {
}
