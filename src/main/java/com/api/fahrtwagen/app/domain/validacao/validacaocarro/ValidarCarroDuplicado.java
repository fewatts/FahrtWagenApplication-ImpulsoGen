package com.api.fahrtwagen.app.domain.validacao.validacaocarro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.api.fahrtwagen.app.domain.dtos.dtocarro.DadosCadastroCarro;
import com.api.fahrtwagen.app.domain.repository.CarroRepository;
import com.api.fahrtwagen.app.domain.validacao.ValidacaoException;

@Component
public class ValidarCarroDuplicado implements ValidadorCarros{

    @Autowired
    private CarroRepository carroRepository;

    @Override
    public void validar(DadosCadastroCarro dados){
        if(carroRepository.existsByPlacaIgnoreCase(dados.placa())){
            throw new ValidacaoException("Esse carro já está persistido no banco de dados.");
        }
    }
}
