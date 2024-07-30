package com.api.fahrtwagen.app.domain.dtos.dtoreserva;

import java.time.LocalDate;

import com.api.fahrtwagen.app.domain.model.Reserva;

public record DadosDetalhamentoReserva(
        Long idReserva,
        Long carro,
        Long cliente,
        LocalDate dataInicio,
        LocalDate dataFim,
        Double valor,
        Boolean confirmada
) {
    public DadosDetalhamentoReserva(Reserva reserva) {
        this(
            reserva.getIdReserva(),
            reserva.getCarro().getIdCarro(),
            reserva.getCliente().getIdCliente(),
            reserva.getDataInicio(),
            reserva.getDataFim(),
            reserva.getValor(),
            reserva.getConfirmada()
        );
    }
}
