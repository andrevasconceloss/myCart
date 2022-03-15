package br.dev.vasconcelos.mycart.rest.dto;

import br.dev.vasconcelos.mycart.validation.UniqueContraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.UniqueElements;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInsertDTO {
    @Column(name = "username", length = 100)
    @NotEmpty(message = "{username_field_required}")
    private String name;

    @Column(name = "email", length = 120, unique = true)
    @NotEmpty(message = "{email_field_required}")
    @UniqueContraint(message = "{user_already_exists}")
    private String email;

    @Column(name = "user_password")
    @NotEmpty(message = "{password_field_required}")
    private String password;
}
