package com.api.fahrtwagen.app.domain.validacao.validacaoreserva;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public abstract class ValidacaoReservaBase {

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
