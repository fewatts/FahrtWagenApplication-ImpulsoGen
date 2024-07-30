package com.api.fahrtwagen.app.domain.validacao;

public class ValidacaoException extends RuntimeException {
    
    public ValidacaoException(String mensagem) {
        super(mensagem);
    }
}
