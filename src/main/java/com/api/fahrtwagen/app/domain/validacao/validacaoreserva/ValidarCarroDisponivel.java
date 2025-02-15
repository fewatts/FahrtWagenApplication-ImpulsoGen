package com.api.fahrtwagen.app.domain.validacao.validacaoreserva;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

import com.api.fahrtwagen.app.domain.dtos.dtoreserva.DadosCadastroReserva;
import com.api.fahrtwagen.app.domain.validacao.ValidacaoException;

@Component
public class ValidarCarroDisponivel extends ValidacaoReservaBase implements ValidadorReservas {

        @Override
        public void validar(DadosCadastroReserva dados) {
                var mesLimite = 6;
                var datasDisponiveis = acharDatasDisponiveis(dados.carro(), LocalDate.now().minusMonths(mesLimite),
                                LocalDate.now().plusMonths(mesLimite), null);

                if (datasDisponiveis.isEmpty())
                        return;

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
                                                        + periodosConcatenados.toString()
                        );
                }
        }
}
