package com.api.fahrtwagen.app.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import java.time.LocalDate;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import com.api.fahrtwagen.app.domain.dtos.dtoreserva.DadosCadastroReserva;
import com.api.fahrtwagen.app.domain.dtos.dtoreserva.DadosDetalhamentoReserva;
import com.api.fahrtwagen.app.domain.model.Carro;
import com.api.fahrtwagen.app.domain.model.Cliente;
import com.api.fahrtwagen.app.domain.model.Reserva;
import com.api.fahrtwagen.app.domain.repository.ReservaRepository;
import com.api.fahrtwagen.app.domain.service.ReservaService;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class ReservaControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<DadosCadastroReserva> dadosCadastroReservaJt;

    @Autowired
    private JacksonTester<DadosDetalhamentoReserva> dadosDetalhamentoReservaJt;

    @MockBean
    private ReservaService reservaService;

    @MockBean
    private ReservaRepository reservaRepository;

    @Test
    @DisplayName("POST de reservas sem corpo na requisição deveria retornar BAD_REQUEST")
    @WithMockUser
    void cadastrarReservaCenarioUm() throws Exception {
        var response = mvc.perform(post("/reservas"))
                .andReturn().getResponse();

        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatus());
    }

    @Test
    @DisplayName("POST de reservas válidas deve retornar OK")
    @WithMockUser
    void cadastrarReservaCenarioDois() throws Exception {

        var dataInicio = LocalDate.now();
        var dataFim = dataInicio.plusDays(7);

        var dados = new DadosCadastroReserva(1L, 1L, dataInicio, dataFim);

        var carro = new Carro(1L, "Ford", "Fiesta", 2020, "ABC-1234", 20000.0, true, true);
        var cliente = new Cliente(1L, "João da Silva", "12345678910", "999998888", "joao@example.com");

        var reserva = new Reserva();
        reserva.setIdReserva(1L);
        reserva.setCliente(cliente);
        reserva.setCarro(carro);
        reserva.setDataInicio(dataInicio);
        reserva.setDataFim(dataFim);

        when(reservaService.cadastrar(any(DadosCadastroReserva.class))).thenReturn(reserva);

        var response = mvc.perform(
                post("/reservas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(dadosCadastroReservaJt.write(dados).getJson()))
                .andReturn().getResponse();

        var jsonEsperado = dadosDetalhamentoReservaJt.write(
                new DadosDetalhamentoReserva(1L, 1L, 1L, dataInicio, dataFim, null, null))
                .getJson();

        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
    }
}
