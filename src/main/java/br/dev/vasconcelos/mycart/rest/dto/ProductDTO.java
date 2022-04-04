package br.dev.vasconcelos.mycart.rest.dto;

import br.dev.vasconcelos.mycart.validation.UniqueContraint;
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
    @Column(length = 13)
    private String ean;

    @Column(length = 100)
    @NotEmpty(message = "{ean_field_required}")
    private String description;

    @Column(precision = 20, scale = 2)
    private BigDecimal price;

    @Column
    private byte[] image;
}
