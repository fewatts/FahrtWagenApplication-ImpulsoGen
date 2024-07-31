package com.api.fahrtwagen.app.domain.dtos.dtoreserva;

import java.time.LocalDate;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

public record DadosCadastroReserva(
        @NotNull Long carro,
        @NotNull Long cliente,
        @NotNull @FutureOrPresent LocalDate dataInicio,
        @NotNull @Future LocalDate dataFim
) {
}
