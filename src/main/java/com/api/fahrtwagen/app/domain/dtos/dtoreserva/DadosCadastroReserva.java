package com.api.fahrtwagen.app.domain.dtos.dtoreserva;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;

public record DadosCadastroReserva(
        @NotNull Long carro,
        @NotNull Long cliente,
        @NotNull LocalDate dataInicio,
        @NotNull LocalDate dataFim,
        @NotNull Boolean confirmada
) {
}
