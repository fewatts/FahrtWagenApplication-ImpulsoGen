package com.api.fahrtwagen.app.domain.dtos.dtocliente;

import com.api.fahrtwagen.app.domain.model.Cliente;

public record DadosDetalhamentoCliente(
        Long idCliente,
        String nome,
        String cpf,
        String telefone,
        String email
) {

    public DadosDetalhamentoCliente(Cliente cliente) {
        this(cliente.getIdCliente(), cliente.getNome(), cliente.getCpf(), cliente.getTelefone(), cliente.getEmail());
    }
}
