package com.api.fahrtwagen.app.domain.model;

import java.time.LocalDate;

import com.api.fahrtwagen.app.domain.dtos.dtoreserva.DadosCadastroReserva;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "reservas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "idReserva")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_reserva")
    private Long idReserva;

    @ManyToOne
    @JoinColumn(name = "carro_id", nullable = false)
    private Carro carro;

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @Column(name = "data_inicio", nullable = false)
    private LocalDate dataInicio;

    @Column(name = "data_fim", nullable = false)
    private LocalDate dataFim;

    private Double valor;

    @Column(nullable = false)
    private Boolean confirmada;

    public Reserva(DadosCadastroReserva dados, Carro carro, Cliente cliente) {
        this.carro = carro;
        this.cliente = cliente;
        this.dataInicio = dados.dataInicio();
        this.dataFim = dados.dataFim();
        this.valor = calcularValorReserva(dados.dataInicio(), dados.dataFim(), carro.getValor());
    }

    public void atualizar(DadosCadastroReserva dados, Carro carro, Cliente cliente) {
        if (dados.carro() != null) {
            this.carro = carro;
        }
        if (dados.cliente() != null) {
            this.cliente = cliente;
        }
        if (dados.dataInicio() != null) {
            this.dataInicio = dados.dataInicio();
        }
        if (dados.dataFim() != null) {
            this.dataFim = dados.dataFim();
        }
        this.valor = calcularValorReserva(this.dataInicio, this.dataFim, carro.getValor());
    }

    private Double calcularValorReserva(LocalDate dataInicio, LocalDate dataFim, Double valor) {
        long dias = dataFim.toEpochDay() - dataInicio.toEpochDay();
        return valor * dias;
    }
}
