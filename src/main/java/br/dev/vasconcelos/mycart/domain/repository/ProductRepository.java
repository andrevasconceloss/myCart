package br.dev.vasconcelos.mycart.domain.repository;

import br.dev.vasconcelos.mycart.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
