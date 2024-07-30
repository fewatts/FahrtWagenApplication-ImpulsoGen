package com.api.fahrtwagen.app.domain.dtos.dtocarro;

import com.api.fahrtwagen.app.domain.model.Carro;

public record DadosDetalhamentoCarro(
    Long idCarro, 
    String marca, 
    String modelo, 
    Integer ano, 
    String placa, 
    Double valor,
    Boolean manutencaoEmDia, 
    Boolean disponivel, 
    Boolean ativo
) {

    public DadosDetalhamentoCarro(Carro carro){
        this(
            carro.getIdCarro(), 
            carro.getMarca(), 
            carro.getModelo(), 
            carro.getAno(), 
            carro.getPlaca(), 
            carro.getValor(),
            carro.getManutencaoEmDia(), 
            carro.getDisponivel(), 
            carro.getAtivo()
        );
    }
}
