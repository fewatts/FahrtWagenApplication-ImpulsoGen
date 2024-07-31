package com.api.fahrtwagen.app.domain.dtos.dtocarro;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record DadosCadastroCarro(
        @NotBlank(message = "A marca não pode estar em branco")
        String marca,
        
        @NotBlank(message = "O modelo não pode estar em branco")
        String modelo,
        
        @NotNull(message = "O ano não pode ser nulo")
        @Positive(message = "O ano deve ser um valor positivo")
        Integer ano,
        
        @NotBlank(message = "A placa não pode estar em branco")
        String placa,
        
        @Positive(message = "O valor deve ser um valor positivo")
        Double valor,
        
        @NotNull(message = "O status de manutenção deve ser informado")
        Boolean manutencaoEmDia,
        
        @NotNull(message = "O status ativo deve ser informado")
        Boolean ativo
    ) {
}
