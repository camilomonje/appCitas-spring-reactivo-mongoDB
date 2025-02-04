package com.springBajo8.springBajo8.web;


import com.springBajo8.springBajo8.domain.DiagnosticoDTO;
import com.springBajo8.springBajo8.domain.citasDTOReactiva;
import com.springBajo8.springBajo8.service.IcitasReactivaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.websocket.server.PathParam;
import java.time.LocalDate;

@RestController
public class citasReactivaResource {

    @Autowired
    private IcitasReactivaService icitasReactivaService;

    @PostMapping("/citasReactivas")
    @ResponseStatus(HttpStatus.CREATED)
    private Mono<citasDTOReactiva> save(@RequestBody citasDTOReactiva citasDTOReactiva) {
        return this.icitasReactivaService.save(citasDTOReactiva);
    }

    @DeleteMapping("/citasReactivas/{id}")
    private Mono<ResponseEntity<citasDTOReactiva>> delete(@PathVariable("id") String id) {
        return this.icitasReactivaService.delete(id)
                .flatMap(citasDTOReactiva -> Mono.just(ResponseEntity.ok(citasDTOReactiva)))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));

    }

    @PutMapping("/citasReactivas/{id}")
    private Mono<ResponseEntity<citasDTOReactiva>> update(@PathVariable("id") String id, @RequestBody citasDTOReactiva citasDTOReactiva) {
        return this.icitasReactivaService.update(id, citasDTOReactiva)
                .flatMap(citasDTOReactiva1 -> Mono.just(ResponseEntity.ok(citasDTOReactiva1)))
                .switchIfEmpty(Mono.just(ResponseEntity.notFound().build()));

    }

    @GetMapping("/citasReactivas/{idPaciente}/byidPaciente")
    private Flux<citasDTOReactiva> findAllByidPaciente(@PathVariable("idPaciente") String idPaciente) {
        return this.icitasReactivaService.findByIdPaciente(idPaciente);
    }

    @GetMapping(value = "/citasReactivas")
    private Flux<citasDTOReactiva> findAll() {
        return this.icitasReactivaService.findAll();
    }

    //Nuevos metodos
    // cancelar una cita de forma logica
    @PutMapping(value = "/citasReactivas/{id}/cancelarCita")
    private Mono<citasDTOReactiva> updateCancelarCita(@PathVariable("id") String id) {
        return this.icitasReactivaService.updateCancelarCita(id);
    }

    //consultar cita por fecha y hora
    @GetMapping(value = "/citasReactivas/{fechaReservaCita}/{horaReservaCita}/consultarByFechaAndHora")
    private Flux<citasDTOReactiva> findByFechaYHora(
            @PathVariable("fechaReservaCita") String fecha,
            @PathVariable("horaReservaCita") String hora) {
        System.out.println(fecha);
        return this.icitasReactivaService.findByFechaYHora(fecha, hora);
    }

    //cita por nombre y apellido de medico /citasReactivas/nombreMedico/?nombre=<nombre>&apellidos=<apellido>
    @GetMapping("/citasReactivas/nombreMedico")
    private Mono<citasDTOReactiva> findByNombreMedico(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String apellidos){
        System.out.println(nombre);
        return this.icitasReactivaService.findByNombreMedico(nombre, apellidos);
    }

    //padecimientos y tratamientos de paciente
    @GetMapping("/citasReactivas/padecimientos/{idPaciente}/byIdPaciente")
    private Flux<DiagnosticoDTO> findPadecimientosByidPaciente(@PathVariable("idPaciente") String idPaciente) {
        return this.icitasReactivaService.findPadecimientosByIdPaciente(idPaciente);
    }

}
