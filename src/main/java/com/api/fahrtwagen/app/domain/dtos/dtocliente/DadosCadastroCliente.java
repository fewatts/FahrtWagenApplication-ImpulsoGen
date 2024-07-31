package com.api.fahrtwagen.app.domain.dtos.dtocliente;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record DadosCadastroCliente(
        @NotBlank String nome,
        @NotBlank @Pattern(regexp = "\\d{11}", message = "Exemplo: '99999999999'") String cpf,
        @NotBlank String telefone,
        @Email String email
) {
}
