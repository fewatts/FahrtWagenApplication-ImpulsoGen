package com.api.fahrtwagen.app.domain.validacao.validacaoreserva;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.api.fahrtwagen.app.domain.dtos.dtoreserva.DadosCadastroReserva;
import com.api.fahrtwagen.app.domain.repository.CarroRepository;
import com.api.fahrtwagen.app.domain.validacao.ValidacaoException;

@Component
public class ValidarCarroAtivo implements ValidadorReservas {

    @Autowired
    private CarroRepository carroRepository;

    @Override
    public void validar(DadosCadastroReserva dados) {

        var carro = carroRepository.getReferenceById(dados.carro());

       if(!carro.getAtivo()) {
            throw new ValidacaoException("O carro '" + carro.getModelo() + "' de placa '" + carro.getPlaca() + "' está inativo.");
       }
    }

}
