package com.api.fahrtwagen.app.domain.validacao.validacaoreserva;

import org.springframework.stereotype.Component;

import com.api.fahrtwagen.app.domain.dtos.dtoreserva.DadosCadastroReserva;
import com.api.fahrtwagen.app.domain.validacao.ValidacaoException;

@Component
public class ValidarDataReserva implements ValidadorReservas{

    @Override
    public void validar(DadosCadastroReserva dados) {
        if (dados.dataInicio().isAfter(dados.dataFim())) {
            throw new ValidacaoException(
                "A data de início da reserva não pode ser posterior à data de fim."
            );
        }
    }
}
