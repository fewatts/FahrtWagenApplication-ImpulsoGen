package com.api.fahrtwagen.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.fahrtwagen.app.domain.dtos.dtousuario.DadosAutenticacao;
import com.api.fahrtwagen.app.domain.model.Usuario;
import com.api.fahrtwagen.app.infra.security.DadosTokenJWT;
import com.api.fahrtwagen.app.infra.security.TokenService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("auth")
public class AuthController {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationManager manager;

    @PostMapping
    public ResponseEntity<DadosTokenJWT> auth(@RequestBody @Valid DadosAutenticacao dados) {
        var autenticacaoToken = new UsernamePasswordAuthenticationToken(dados.email(), dados.senha());
        var authentication = manager.authenticate(autenticacaoToken);
        var tokenJWT = tokenService.gerarToken((Usuario) authentication.getPrincipal());
        return ResponseEntity.ok(new DadosTokenJWT(tokenJWT));
    }
}
