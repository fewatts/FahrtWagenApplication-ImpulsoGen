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
public class ValidarCarroDisponivelPut {
        
    @Autowired
    private CarroRepository carroRepository;

    @Autowired
    private ReservaRepository reservaRepository;

    public void validar(DadosCadastroReserva dados, Long id) {
        var carro = carroRepository.getReferenceById(dados.carro());
        var dataInicioNovaReserva = dados.dataInicio();
        var dataFimNovaReserva = dados.dataFim();

        var reservas = reservaRepository.findByCarroId(carro.getIdCarro());

        var dataIndisponivel = reservas.stream()
                .filter(reserva -> !reserva.getIdReserva().equals(id))
                .filter(reserva -> dataInicioNovaReserva.isBefore(reserva.getDataFim())
                        && dataFimNovaReserva.isAfter(reserva.getDataInicio()))
                .map(reserva -> reserva.getDataFim())
                .max(LocalDate::compareTo)
                .orElse(null);

        if (dataIndisponivel != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            throw new ValidacaoException(
                    String.format("O carro: 'ID: %d, Modelo: %s' não se encontra disponível nas datas solicitadas. " +
                            "O carro estará disponível a partir de: %s",
                            carro.getIdCarro(),
                            carro.getModelo(),
                            dataIndisponivel.plusDays(1).format(formatter)));
        }
    }
}
