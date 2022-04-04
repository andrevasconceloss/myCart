package br.dev.vasconcelos.mycart.domain.repository;

import br.dev.vasconcelos.mycart.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;

public interface ProductRepository extends JpaRepository<Product, Integer> {

    @Modifying
    @Query( nativeQuery = true,
            value = " INSERT INTO PRODUCT ( " +
                    "   EAN, DESCRIPTION, PRICE, IMAGE" +
                    " ) VALUES ( " +
                    "   :ean, :description, :price, :image " +
                    " )"
    )
    Product save(@Param("ean") String ean,
                 @Param("description") String description,
                 @Param("price") BigDecimal price,
                 @Param("image") byte[] image);

}
