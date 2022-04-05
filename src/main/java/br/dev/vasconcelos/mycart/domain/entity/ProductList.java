package br.dev.vasconcelos.mycart.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "product_list")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "list_id")
    private Integer listId;

    @Column(name = "product_id")
    private Integer productId;

    @Column(name = "quantity")
    private BigDecimal quantity;
}
