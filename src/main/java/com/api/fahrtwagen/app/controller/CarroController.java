package com.api.fahrtwagen.app.controller;

import java.util.List;

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

import com.api.fahrtwagen.app.domain.dtos.dtocarro.DadosCadastroCarro;
import com.api.fahrtwagen.app.domain.dtos.dtocarro.DadosDetalhamentoCarro;
import com.api.fahrtwagen.app.domain.model.Carro;
import com.api.fahrtwagen.app.domain.repository.CarroRepository;
import com.api.fahrtwagen.app.domain.validacao.validacaocarro.ValidadorCarros;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("carros")
public class CarroController {

    @Autowired
    private CarroRepository carroRepository;

    @Autowired
    private List<ValidadorCarros> validadores;

    @GetMapping
    public ResponseEntity<Page<DadosDetalhamentoCarro>> listar(
            @PageableDefault(size = 10, sort = { "marca" }, direction = Sort.Direction.ASC) Pageable paginacao) {
        var page = carroRepository.findAll(paginacao).map(DadosDetalhamentoCarro::new);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoCarro> detalhar(@PathVariable Long id) {
        var carro = carroRepository.getReferenceById(id);
        return ResponseEntity.ok().body(new DadosDetalhamentoCarro(carro));
    }

    @PostMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoCarro> cadastrar(@RequestBody @Valid DadosCadastroCarro dados,
            UriComponentsBuilder uriBuilder) {
        validadores.forEach(v -> v.validar(dados));
        var carro = new Carro(dados);
        carroRepository.save(carro);
        var uri = uriBuilder.path("/carros/{id}").buildAndExpand(carro.getIdCarro()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhamentoCarro(carro));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DadosDetalhamentoCarro> atualizar(@PathVariable Long id,
            @RequestBody @Valid DadosCadastroCarro dados) {
        validadores.forEach(v -> v.validar(dados));
        var carro = carroRepository.getReferenceById(id);
        carro.atualizar(dados);
        return ResponseEntity.ok().body(new DadosDetalhamentoCarro(carro));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<HttpStatus> deletar(@PathVariable Long id) {
        var carro = carroRepository.getReferenceById(id);
        carro.excluir();
        return ResponseEntity.noContent().build();
    }
}
