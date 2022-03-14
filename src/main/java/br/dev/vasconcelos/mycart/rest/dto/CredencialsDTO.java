package br.dev.vasconcelos.mycart.rest.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CredencialsDTO {
    private String email;
    private String password;
}
