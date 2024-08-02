package com.api.fahrtwagen.app.domain.dtos.dtousuario;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record DadosCadastroUsuario(
        @NotBlank String nome,

        @Email @NotBlank String email,

        @Pattern(regexp = "^(?=.*[A-Z])(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,}$", message = "A senha deve ter no mínimo 6 caracteres, incluindo pelo menos uma letra maiúscula e um caractere especial. A senha não pode ter sequências de números e deve variar os tipos de caracteres ao longo de seu comprimento.")
        String senha
) {}
