package com.api.fahrtwagen.app.domain.dtos.dtocarro;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record DadosCadastroCarro(
        @NotBlank String marca,
        @NotBlank String modelo,
        @Positive @NotNull Integer ano,
        @NotBlank String placa,
        @Positive Double valor,
        @NotNull Boolean manutencaoEmDia,
        @NotNull Boolean disponivel,
        @NotNull Boolean ativo
    ) {
}
