package com.api.fahrtwagen.app.domain.validacao.validacaoreserva;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.api.fahrtwagen.app.domain.dtos.dtoreserva.DadosCadastroReserva;
import com.api.fahrtwagen.app.domain.repository.CarroRepository;
import com.api.fahrtwagen.app.domain.repository.ReservaRepository;
import com.api.fahrtwagen.app.domain.validacao.ValidacaoException;

@Component
public class ValidarCarroDisponivel implements ValidadorReservas{

    @Autowired
    private CarroRepository carroRepository;

    @Autowired
    private ReservaRepository reservaRepository;

    @Override
    public void validar(DadosCadastroReserva dados) {
        var carro = carroRepository.getReferenceById(dados.carro());
        var dataInicioNovaReserva = dados.dataInicio();
        var dataFimNovaReserva = dados.dataFim();

        var reservas = reservaRepository.findByCarroId(carro.getIdCarro());

        boolean carroDisponivel = true;
        LocalDate dataDisponivel = null;

        for (var reserva : reservas) {
            if (dataInicioNovaReserva.isBefore(reserva.getDataFim())
                    && dataFimNovaReserva.isAfter(reserva.getDataInicio())) {
                carroDisponivel = false;
                if (dataDisponivel == null || reserva.getDataFim().isAfter(dataDisponivel)) {
                    dataDisponivel = reserva.getDataFim();
                }
            }
        }

        if (!carroDisponivel) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            throw new ValidacaoException(
                    "O carro: 'ID: " + carro.getIdCarro() + ", Modelo: " + carro.getModelo()
                            + "' não se encontra disponível nas datas solicitadas. " +
                            "O carro estará disponível a partir de: " + dataDisponivel.plusDays(1).format(formatter));
        }
    }

}
