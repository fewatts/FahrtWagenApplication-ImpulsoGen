package com.api.fahrtwagen.app.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.api.fahrtwagen.app.domain.dtos.dtousuario.DadosCadastroUsuario;
import com.api.fahrtwagen.app.domain.dtos.dtousuario.DadosDetalhamentoUsuario;
import com.api.fahrtwagen.app.domain.model.Usuario;
import com.api.fahrtwagen.app.domain.repository.UsuarioRepository;
import com.api.fahrtwagen.app.domain.service.UsuarioService;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("usuarios")
@SecurityRequirement(name = "bearer-key")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping
    public ResponseEntity<Page<DadosDetalhamentoUsuario>> listarUsuarios(
            @PageableDefault(size = 10, sort = { "nome" }, direction = Sort.Direction.ASC) Pageable paginacao) {
        var page = usuarioRepository.findAll(paginacao).map(DadosDetalhamentoUsuario::new);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoUsuario> detalharUsuario(@PathVariable Long id) {
        var usuario = usuarioRepository.getReferenceById(id);
        return ResponseEntity.ok().body(new DadosDetalhamentoUsuario(usuario));
    }

    @PostMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoUsuario> cadastrarUsuario(@RequestBody @Valid DadosCadastroUsuario dados,
            UriComponentsBuilder uriBuilder) {
        var usuario = usuarioService.cadastrarUsuario(dados);
        var uri = uriBuilder.path("/usuarios/{id}").buildAndExpand(usuario.get().getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhamentoUsuario(usuario.get()));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DadosDetalhamentoUsuario> atualizarUsuario(@PathVariable Long id,
            @RequestBody @Valid DadosCadastroUsuario dados) {
        Optional<Usuario> usuario = usuarioRepository.findById(id);
        if (usuario.isPresent()) {
            usuarioService.atualizarUsuario(usuario.get());
        }
        return ResponseEntity.ok().body(new DadosDetalhamentoUsuario(usuario.get()));
    }

}
