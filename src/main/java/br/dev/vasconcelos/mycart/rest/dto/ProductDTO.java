package br.dev.vasconcelos.mycart.rest.dto;

import br.dev.vasconcelos.mycart.validation.CategoryFK;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private String ean;

    @Column(length = 100)
    @NotEmpty(message = "{name_field_invalid}")
    private String name;

    @Column(precision = 20, scale = 2)
    private BigDecimal price;

    private byte[] image;

    @CategoryFK
    private Integer categoryId;

    private boolean active;
}
