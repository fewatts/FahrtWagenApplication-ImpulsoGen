package com.api.fahrtwagen.app.domain.validacao.validacaoreserva;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.api.fahrtwagen.app.domain.dtos.dtoreserva.DadosCadastroReserva;
import com.api.fahrtwagen.app.domain.model.Reserva;
import com.api.fahrtwagen.app.domain.repository.ReservaRepository;
import com.api.fahrtwagen.app.domain.validacao.ValidacaoException;

@Component
public class ValidarCarroDisponivel extends ValidacaoReservaBase implements ValidadorReservas {

        @Autowired
        private ReservaRepository reservaRepository;

        @Override
        public void validar(DadosCadastroReserva dados) {
                var mesLimite = 6;
                var datasDisponiveis = acharDatasDisponiveis(dados.carro(), LocalDate.now(),
                                LocalDate.now().plusMonths(mesLimite));

                if (!isPeriodoDisponivel(datasDisponiveis, dados.dataInicio(), dados.dataFim())) {
                        var periodosConcatenados = new StringBuilder();
                        var formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        var periodos = agruparPeriodos(datasDisponiveis, formatter);

                        for (int i = 0; i < periodos.size(); i++) {
                                periodosConcatenados.append(i + 1).append(": ").append(periodos.get(i));
                                if (i < periodos.size() - 1) {
                                        periodosConcatenados.append(", ");
                                }
                        }

                        throw new ValidacaoException(
                                        "O período solicitado não está disponível. Períodos disponíveis em até "
                                                        + mesLimite + " meses: "
                                                        + periodosConcatenados.toString());
                }
        }

        private List<LocalDate> acharDatasDisponiveis(Long carro, LocalDate startDate, LocalDate endDate) {
                var reservas = reservaRepository.findByCarroIdOrderByDataInicio(carro);
                List<LocalDate> availableDates = new ArrayList<>();

                for (var i = 0; i < reservas.size() - 1; i++) {
                        var currentReserva = reservas.get(i);
                        var nextReserva = reservas.get(i + 1);
                        var currentEndDate = currentReserva.getDataFim().plusDays(1);
                        var nextStartDate = nextReserva.getDataInicio().minusDays(1);

                        availableDates.addAll(acharPeriodosDeDatas(currentEndDate, nextStartDate));
                }

                Reserva lastReserva = reservas.get(reservas.size() - 1);
                if (lastReserva.getDataFim().isBefore(endDate)) {
                        availableDates.addAll(acharPeriodosDeDatas(lastReserva.getDataFim().plusDays(1), endDate));
                }

                return availableDates;
        }
}
