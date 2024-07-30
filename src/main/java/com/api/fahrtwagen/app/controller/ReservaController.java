package com.api.fahrtwagen.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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

import com.api.fahrtwagen.app.domain.dtos.dtoreserva.DadosCadastroReserva;
import com.api.fahrtwagen.app.domain.dtos.dtoreserva.DadosDetalhamentoReserva;
import com.api.fahrtwagen.app.domain.repository.ReservaRepository;
import com.api.fahrtwagen.app.domain.service.ReservaService;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("reservas")
public class ReservaController {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private ReservaService reservaService;

    @GetMapping
    public ResponseEntity<Page<DadosDetalhamentoReserva>> listar(
            @PageableDefault(size = 10, sort = "dataInicio", direction = Sort.Direction.ASC) Pageable paginacao) {
        var page = reservaRepository.findAll(paginacao).map(DadosDetalhamentoReserva::new);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DadosDetalhamentoReserva> detalhar(@PathVariable Long id) {
        var reserva = reservaService.detalhar(id);
        return ResponseEntity.ok(new DadosDetalhamentoReserva(reserva));
    }

    @PostMapping
    @Transactional
    public ResponseEntity<DadosDetalhamentoReserva> cadastrar(@RequestBody @Valid DadosCadastroReserva dados,
            UriComponentsBuilder uriBuilder) {
        var reserva = reservaService.cadastrar(dados);
        var uri = uriBuilder.path("/reservas/{id}").buildAndExpand(reserva.getIdReserva()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhamentoReserva(reserva));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<DadosDetalhamentoReserva> atualizar(@PathVariable Long id,
            @RequestBody @Valid DadosCadastroReserva dados) {
        var reserva = reservaService.atualizar(id, dados);
        return ResponseEntity.ok(new DadosDetalhamentoReserva(reserva));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<HttpStatus> deletar(@PathVariable Long id) {
        reservaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
