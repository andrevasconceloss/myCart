package br.dev.vasconcelos.mycart.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CredencialsDTO {

    @Column(length = 120, unique = true)
    @NotEmpty(message = "{email_field_required}")
    private String email;

    @NotEmpty(message = "{password_field_required}")
    private String password;
}
