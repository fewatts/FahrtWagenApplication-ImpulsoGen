package com.api.fahrtwagen.app.domain.validacao.validacaoreserva;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.api.fahrtwagen.app.domain.model.Reserva;
import com.api.fahrtwagen.app.domain.repository.ReservaRepository;

@Component
public abstract class ValidacaoReservaBase {

    @Autowired
    private ReservaRepository reservaRepository;

    protected List<LocalDate> acharDatasDisponiveis(Long carro, LocalDate startDate, LocalDate endDate,
            Long reservaIdParaIgnorar) {
        List<Reserva> reservas;

        if (reservaIdParaIgnorar != null) {
            reservas = reservaRepository.findByCarroIdOrderByDataInicioIgnoreReservaId(carro, reservaIdParaIgnorar);
        } else {
            reservas = reservaRepository.findByCarroIdOrderByDataInicio(carro);
        }

        if (reservas.isEmpty()) {
            return new ArrayList<>();
        }

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

    protected List<LocalDate> acharPeriodosDeDatas(LocalDate start, LocalDate end) {
        List<LocalDate> dates = new ArrayList<>();
        LocalDate date = start;
        while (!date.isAfter(end)) {
            dates.add(date);
            date = date.plusDays(1);
        }
        return dates;
    }

    protected boolean isPeriodoDisponivel(List<LocalDate> datasDisponiveis, LocalDate dataInicio, LocalDate dataFim) {
        for (LocalDate date = dataInicio; !date.isAfter(dataFim); date = date.plusDays(1)) {
            if (!datasDisponiveis.contains(date)) {
                return false;
            }
        }
        return true;
    }

    protected List<String> agruparPeriodos(List<LocalDate> datasDisponiveis, DateTimeFormatter formatter) {
        List<String> periodos = new ArrayList<>();

        if (datasDisponiveis.isEmpty()) {
            return periodos;
        }

        LocalDate start = datasDisponiveis.get(0);
        LocalDate end = start;

        for (int i = 1; i < datasDisponiveis.size(); i++) {
            LocalDate current = datasDisponiveis.get(i);
            if (current.equals(end.plusDays(1))) {
                end = current;
            } else {
                periodos.add(formatarPeriodo(start, end, formatter));
                start = current;
                end = start;
            }
        }

        periodos.add(formatarPeriodo(start, end, formatter));
        return periodos;
    }

    protected String formatarPeriodo(LocalDate start, LocalDate end, DateTimeFormatter formatter) {
        if (start.equals(end)) {
            return start.format(formatter);
        } else {
            return start.format(formatter) + " até " + end.format(formatter);
        }
    }
}
