package br.com.barberhub.controller;

import br.com.barberhub.dto.BarberRequestDTO;
import br.com.barberhub.dto.BarberResponseDTO;
import br.com.barberhub.dto.BarberUpdateDTO;
import br.com.barberhub.enums.Specialty;
import br.com.barberhub.service.BarberService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/v1/barber")
@RequiredArgsConstructor
@Validated
@Tag(name="Barber", description = "Controller para serviços do Barbeiro")
public class BarberController {

    private final BarberService service;


    @GetMapping
    public ResponseEntity<List<BarberResponseDTO>> listBarbers() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping
    public ResponseEntity<List<BarberResponseDTO>> ListBySpecialty(Specialty specialty) {
        return ResponseEntity.ok(service.findBySpecialty(specialty));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BarberResponseDTO> getBarberById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }


    @PostMapping("/create")
    public ResponseEntity<BarberResponseDTO> createBarber(@RequestBody @Valid BarberRequestDTO dados, UriComponentsBuilder uriBuilder ) {
        var barber = service.createBarber(dados);
        var uri = uriBuilder.path("/{id}").buildAndExpand(barber.id()).toUri();
        return ResponseEntity.created(uri).body(barber);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BarberResponseDTO> updateBarber(@RequestBody @Valid @PathVariable Long id, BarberUpdateDTO dados) {
        var barber = service.updateBarber(id,dados);
        return ResponseEntity.ok(barber);
    }

    @PatchMapping("{id}/disable")
    public ResponseEntity<Void> disableBarber(@RequestParam Long id) {
        service.disableBarber(id);
        return ResponseEntity.noContent().build();
    }





}
