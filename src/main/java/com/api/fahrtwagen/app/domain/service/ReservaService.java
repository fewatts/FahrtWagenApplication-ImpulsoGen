package com.api.fahrtwagen.app.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.fahrtwagen.app.domain.dtos.dtoreserva.DadosCadastroReserva;
import com.api.fahrtwagen.app.domain.model.Reserva;
import com.api.fahrtwagen.app.domain.repository.CarroRepository;
import com.api.fahrtwagen.app.domain.repository.ClienteRepository;
import com.api.fahrtwagen.app.domain.repository.ReservaRepository;
import com.api.fahrtwagen.app.domain.validacao.validacaoreserva.ValidadorReservas;
import com.api.fahrtwagen.app.domain.validacao.validacaoreserva.ValidarCarroAtivo;
import com.api.fahrtwagen.app.domain.validacao.validacaoreserva.ValidarCarroDisponivelPut;
import com.api.fahrtwagen.app.domain.validacao.validacaoreserva.ValidarDataReserva;

@Service
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private CarroRepository carroRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ValidarCarroDisponivelPut validardor1;

    @Autowired
    private ValidarCarroAtivo validardor2;

    @Autowired
    private ValidarDataReserva validardor3;

    @Autowired
    private List<ValidadorReservas> validadores;

    public Reserva detalhar(Long id) {
        return reservaRepository.getReferenceById(id);
    }

    public Reserva cadastrar(DadosCadastroReserva dados) {
        validadores.forEach(v -> v.validar(dados));
        var carro = carroRepository.getReferenceById(dados.carro());
        var cliente = clienteRepository.getReferenceById(dados.cliente());
        var reserva = new Reserva(dados, carro, cliente);
        reserva.setConfirmada(true);
        return reservaRepository.save(reserva);
    }

    public Reserva atualizar(Long id, DadosCadastroReserva dados) {
        validardor1.validar(dados, id);
        validardor2.validar(dados);
        validardor3.validar(dados);
        var reserva = reservaRepository.getReferenceById(id);
        reserva.setConfirmada(true);
        var carro = carroRepository.getReferenceById(dados.carro());
        var cliente = clienteRepository.getReferenceById(dados.cliente());
        reserva.atualizar(dados, carro, cliente);
        return reservaRepository.save(reserva);
    }

    public void deletar(Long id) {
        var reserva = reservaRepository.getReferenceById(id);
        reservaRepository.delete(reserva);
    }

}
