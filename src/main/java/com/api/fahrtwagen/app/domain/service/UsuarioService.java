package com.api.fahrtwagen.app.domain.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.api.fahrtwagen.app.domain.dtos.dtousuario.DadosCadastroUsuario;
import com.api.fahrtwagen.app.domain.model.Usuario;
import com.api.fahrtwagen.app.domain.repository.UsuarioRepository;
import com.api.fahrtwagen.app.domain.validacao.ValidacaoException;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Optional<Usuario> cadastrarUsuario(DadosCadastroUsuario dados) {
        if (usuarioRepository.findByEmail(dados.email()) != null) {
            return Optional.empty();
        }
        var usuario = new Usuario(dados);
        usuario.setSenha(criptografarSenha(usuario.getSenha()));
        return Optional.of(usuarioRepository.save(usuario));
    }

    private String criptografarSenha(String senha) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(senha);
    }

    public void atualizarUsuario(Usuario usuario) {
        var buscaUsuario = usuarioRepository.findByEmail(usuario.getEmail());
        if (buscaUsuario != null && buscaUsuario.getId() != usuario.getId()) {
            throw new ValidacaoException("Usuário já existe");
        }
        usuario.setSenha(criptografarSenha(usuario.getSenha()));
    }
}
