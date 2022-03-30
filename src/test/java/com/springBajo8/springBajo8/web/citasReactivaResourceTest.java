package com.springBajo8.springBajo8.web;

import com.springBajo8.springBajo8.domain.DiagnosticoDTO;
import com.springBajo8.springBajo8.domain.citasDTOReactiva;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import static org.hamcrest.CoreMatchers.equalTo;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RequiredArgsConstructor(onConstructor_ = @Autowired)
class citasReactivaResourceTest {

    private final WebTestClient webClient;

    @Test
    @DisplayName("Obtiene cita por nombre de medico")
    void devolverCitaPorNombreMedico() throws Exception {
        webClient.get().uri(uriBuilder -> uriBuilder
                    .path("/citasReactivas/nombreMedico")
                    .queryParam("nombre", "pepe")
                    .queryParam("apellidos", "perez")
                    .build())
                .exchange()
                .expectStatus().isOk()
                .expectBody(citasDTOReactiva.class)
                .value(citas -> citas.getId(), equalTo("f475c46f-a"));
    }

    @Test
    @DisplayName("Obtiene padecimientos de paciente")
    void devolverPadecimientosPacienteId() throws Exception {
        webClient.get().uri(uriBuilder -> uriBuilder
                        .path("/citasReactivas/padecimientos/{idPaciente}/byIdPaciente")
                        .build(13))
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(DiagnosticoDTO.class)
                .value(diagnosticos -> diagnosticos.size(), equalTo(1))
                .value(diagnosticos -> diagnosticos.get(0).getIdCita(), equalTo("1ba65f36-7"));
    }

}