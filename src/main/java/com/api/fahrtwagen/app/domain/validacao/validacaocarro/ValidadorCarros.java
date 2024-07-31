package com.api.fahrtwagen.app.domain.validacao.validacaocarro;

import com.api.fahrtwagen.app.domain.dtos.dtocarro.DadosCadastroCarro;

public interface ValidadorCarros {
    public void validar(DadosCadastroCarro dados);
}
