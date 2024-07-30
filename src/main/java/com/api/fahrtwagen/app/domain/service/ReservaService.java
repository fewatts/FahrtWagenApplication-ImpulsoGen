package com.api.fahrtwagen.app.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.fahrtwagen.app.domain.dtos.dtoreserva.DadosCadastroReserva;
import com.api.fahrtwagen.app.domain.model.Reserva;
import com.api.fahrtwagen.app.domain.repository.CarroRepository;
import com.api.fahrtwagen.app.domain.repository.ClienteRepository;
import com.api.fahrtwagen.app.domain.repository.ReservaRepository;

@Service
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private CarroRepository carroRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    public Reserva detalhar(Long id) {
        return reservaRepository.getReferenceById(id);
    }

    public Reserva cadastrar(DadosCadastroReserva dados) {
        var carro = carroRepository.getReferenceById(dados.carro());
        var cliente = clienteRepository.getReferenceById(dados.cliente());
        var reserva = new Reserva(dados, carro, cliente);
        return reservaRepository.save(reserva);
    }

    public Reserva atualizar(Long id, DadosCadastroReserva dados) {
        var reserva = reservaRepository.getReferenceById(id);
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
