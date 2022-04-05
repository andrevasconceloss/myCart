package br.dev.vasconcelos.mycart.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductCategoryDTO {

    @NotEmpty(message = "{description_field_required}")
    private String description;

    private Integer parentId;

    private boolean active;
}
