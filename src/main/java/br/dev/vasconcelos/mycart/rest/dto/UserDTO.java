package br.dev.vasconcelos.mycart.rest.dto;

import br.dev.vasconcelos.mycart.validation.EmailUniqueConstraint;
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
public class UserDTO {
    @Column(length = 100)
    @NotEmpty(message = "{username_field_required}")
    private String name;

    @Column(length = 120, unique = true)
    @NotEmpty(message = "{email_field_required}")
    @EmailUniqueConstraint(message = "{user_already_exists}")
    private String email;

    @NotEmpty(message = "{password_field_required}")
    private String password;

    private boolean active;
}
