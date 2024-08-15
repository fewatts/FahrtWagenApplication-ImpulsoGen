package com.api.fahrtwagen.app.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.fahrtwagen.app.domain.dtos.dtoreserva.DadosCadastroReserva;
import com.api.fahrtwagen.app.domain.model.Carro;
import com.api.fahrtwagen.app.domain.model.Cliente;
import com.api.fahrtwagen.app.domain.model.Reserva;
import com.api.fahrtwagen.app.domain.repository.CarroRepository;
import com.api.fahrtwagen.app.domain.repository.ClienteRepository;
import com.api.fahrtwagen.app.domain.repository.ReservaRepository;
import com.api.fahrtwagen.app.domain.validacao.validacaoreserva.ValidadorReservas;
import com.api.fahrtwagen.app.domain.validacao.validacaoreserva.ValidarCarroDisponivel;
import com.api.fahrtwagen.app.domain.validacao.validacaoreserva.ValidarCarroDisponivelAtualizacao;

@Service
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private CarroRepository carroRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ValidarCarroDisponivelAtualizacao validarCarroDisponivelAtualizacao;

    @Autowired
    private List<ValidadorReservas> validadores;

    public Reserva detalhar(Long id) {
        return reservaRepository.getReferenceById(id);
    }

    public Reserva cadastrar(DadosCadastroReserva dados) {
        validarDadosComuns(dados);

        var carro = buscarCarro(dados.carro());
        var cliente = buscarCliente(dados.cliente());
        var reserva = new Reserva(dados, carro, cliente);
        reserva.setConfirmada(true);
        return reservaRepository.save(reserva);
    }

    public Reserva atualizar(Long id, DadosCadastroReserva dados) {
        validarDadosComuns(dados, ValidarCarroDisponivel.class);
        validarCarroDisponivelAtualizacao.validar(dados, id);

        var reserva = reservaRepository.getReferenceById(id);
        var carro = buscarCarro(dados.carro());
        var cliente = buscarCliente(dados.cliente());
        reserva.atualizar(dados, carro, cliente);
        reserva.setConfirmada(true);
        return reservaRepository.save(reserva);
    }

    public void deletar(Long id) {
        var reserva = reservaRepository.getReferenceById(id);
        reservaRepository.delete(reserva);
    }

    private void validarDadosComuns(DadosCadastroReserva dados) {
        validadores.forEach(validador -> validador.validar(dados));
    }

    private void validarDadosComuns(DadosCadastroReserva dados, Class<? extends ValidadorReservas> excludeValidator) {
        validadores.stream()
                .filter(validador -> !validador.getClass().equals(excludeValidator))
                .forEach(validador -> validador.validar(dados));
    }

    private Carro buscarCarro(Long idCarro) {
        return carroRepository.getReferenceById(idCarro);
    }

    private Cliente buscarCliente(Long idCliente) {
        return clienteRepository.getReferenceById(idCliente);
    }
}
