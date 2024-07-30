package com.api.fahrtwagen.app.domain.model;

import com.api.fahrtwagen.app.domain.dtos.dtocarro.DadosCadastroCarro;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "carros")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "idCarro")
public class Carro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_carro")
    private Long idCarro;

    private String marca;

    private String modelo;

    private Integer ano;

    @Column(unique = true)
    private String placa;

    private Double valor;

    @Column(name = "manutencao_em_dia")
    private Boolean manutencaoEmDia;

    private Boolean disponivel;

    private Boolean ativo = true;

    public Carro(DadosCadastroCarro dados) {
        this.marca = dados.marca();
        this.modelo = dados.modelo();
        this.ano = dados.ano();
        this.placa = dados.placa();
        this.valor = dados.valor();
        this.manutencaoEmDia = dados.manutencaoEmDia();
        this.disponivel = dados.disponivel();
        this.ativo = dados.ativo();
    }

    public void atualizar(DadosCadastroCarro dados) {
        if (dados.marca() != null) {
            this.marca = dados.marca();
        }
        if (dados.modelo() != null) {
            this.modelo = dados.modelo();
        }
        if (dados.ano() != null) {
            this.ano = dados.ano();
        }
        if (dados.placa() != null) {
            this.placa = dados.placa();
        }
        if (dados.valor() != null) {
            this.valor = dados.valor();
        }
        if (dados.manutencaoEmDia() != null) {
            this.manutencaoEmDia = dados.manutencaoEmDia();
        }
        if (dados.disponivel() != null) {
            this.disponivel = dados.disponivel();
        }
        if (dados.ativo() != null) {
            this.ativo = dados.ativo();
        }
    }

    public void excluir() {
       this.ativo = false;
    }
}
