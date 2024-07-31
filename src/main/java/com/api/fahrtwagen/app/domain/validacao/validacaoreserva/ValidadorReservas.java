package com.api.fahrtwagen.app.domain.validacao.validacaoreserva;

import com.api.fahrtwagen.app.domain.dtos.dtoreserva.DadosCadastroReserva;

public interface ValidadorReservas {
    public void validar(DadosCadastroReserva dados);
}
