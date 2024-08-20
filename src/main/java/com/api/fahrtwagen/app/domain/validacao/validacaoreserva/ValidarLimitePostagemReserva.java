package com.api.fahrtwagen.app.domain.validacao.validacaoreserva;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

import com.api.fahrtwagen.app.domain.dtos.dtoreserva.DadosCadastroReserva;
import com.api.fahrtwagen.app.domain.validacao.ValidacaoException;

@Component
public class ValidarLimitePostagemReserva implements ValidadorReservas{

    @Override
    public void validar(DadosCadastroReserva dados) {
        var limiteDataDeReserva = LocalDate.now().plusMonths(6);
        if(dados.dataFim().isAfter(limiteDataDeReserva)){
            throw new ValidacaoException("Não é possível agendar reservas com mais de 6 meses contados da data atual");
        }
    }

}
