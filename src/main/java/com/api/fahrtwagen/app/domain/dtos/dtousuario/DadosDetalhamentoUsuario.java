package com.api.fahrtwagen.app.domain.dtos.dtousuario;

import com.api.fahrtwagen.app.domain.model.Usuario;

public record DadosDetalhamentoUsuario(
        Long id,
        String nome,
        String usuario
) {
    public DadosDetalhamentoUsuario(Usuario usuario){
        this(usuario.getId(), usuario.getNome(), usuario.getEmail());
    }
}
