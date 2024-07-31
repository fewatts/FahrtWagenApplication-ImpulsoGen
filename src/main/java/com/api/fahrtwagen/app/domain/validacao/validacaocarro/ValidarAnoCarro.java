package com.api.fahrtwagen.app.domain.validacao.validacaocarro;

import java.time.Year;

import org.springframework.stereotype.Component;

import com.api.fahrtwagen.app.domain.dtos.dtocarro.DadosCadastroCarro;
import com.api.fahrtwagen.app.domain.validacao.ValidacaoException;

@Component
public class ValidarAnoCarro {
    
    public void validar(DadosCadastroCarro dados){
        int anoAtual = Year.now().getValue();
        if (dados.ano() > anoAtual) {
            throw new ValidacaoException("O ano deve ser menor ou igual ao ano atual");
        }
    }

}
