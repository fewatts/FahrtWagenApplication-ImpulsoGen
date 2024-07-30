package com.api.fahrtwagen.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.api.fahrtwagen.app.domain.dtos.dtocliente.DadosCadastroCliente;
import com.api.fahrtwagen.app.domain.dtos.dtocliente.DadosDetalhamentoCliente;
import com.api.fahrtwagen.app.domain.model.Cliente;
import com.api.fahrtwagen.app.domain.repository.ClienteRepository;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("clientes")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping
    public ResponseEntity<Page<DadosDetalhamentoCliente>> listar(
            @PageableDefault(size = 10, sort = { "nome" }, direction = Sort.Direction.ASC) Pageable paginacao) {
        var page = clienteRepository.findAll(paginacao).map(DadosDetalhamentoCliente::new);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoCliente> detalhar(@PathVariable Long id) {
        var cliente = clienteRepository.getReferenceById(id);
        return ResponseEntity.ok().body(new DadosDetalhamentoCliente(cliente));
    }

    @PostMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoCliente> cadastrar(@RequestBody @Valid DadosCadastroCliente dados,
            UriComponentsBuilder uriBuilder) {
        var cliente = new Cliente(dados);
        clienteRepository.save(cliente);
        var uri = uriBuilder.path("/clientes/{id}").buildAndExpand(cliente.getIdCliente()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhamentoCliente(cliente));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DadosDetalhamentoCliente> atualizar(@PathVariable Long id,
            @RequestBody @Valid DadosCadastroCliente dados) {
        var cliente = clienteRepository.getReferenceById(id);
        cliente.atualizar(dados);
        return ResponseEntity.ok().body(new DadosDetalhamentoCliente(cliente));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<HttpStatus> deletar(@PathVariable Long id) {
        var cliente = clienteRepository.getReferenceById(id);
        clienteRepository.delete(cliente);
        return ResponseEntity.noContent().build();
    }
}
