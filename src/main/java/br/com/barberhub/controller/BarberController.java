package br.com.barberhub.controller;

import br.com.barberhub.dto.BarberRequestDTO;
import br.com.barberhub.dto.BarberResponseDTO;
import br.com.barberhub.dto.BarberUpdateDTO;
import br.com.barberhub.enums.Specialty;
import br.com.barberhub.service.BarberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
@Tag(name = "Barber", description = "Controller for C.R.U.D of Barbers")
public class BarberController {

    private final BarberService service;

    @Operation(description = "Returns a list of all registered barbers",
            summary = "List all barbers",
            responses = {
                    @ApiResponse(description = "OK", responseCode = "200")
            })
    @GetMapping
    public ResponseEntity<List<BarberResponseDTO>> listBarbers() {
        return ResponseEntity.ok(service.findAll());
    }

    @Operation(description = "Returns a list of barbers filtered by specialty",
            summary = "Find barbers by specialty",
            responses = {
                    @ApiResponse(description = "OK", responseCode = "200")
            })
    @GetMapping("/specialty/{specialty}")
    public ResponseEntity<List<BarberResponseDTO>> listBySpecialty(@PathVariable Specialty specialty) {
        return ResponseEntity.ok(service.findBySpecialty(specialty));
    }

    @Operation(description = "Returns a barber by ID",
            summary = "Find barber by ID",
            responses = {
                    @ApiResponse(description = "OK", responseCode = "200"),
                    @ApiResponse(description = "Not Found", responseCode = "404")
            })
    @GetMapping("/{id}")
    public ResponseEntity<BarberResponseDTO> getBarberById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @Operation(description = "Creates a new barber linked to an existing user",
            summary = "Create barber",
            responses = {
                    @ApiResponse(description = "Created", responseCode = "201"),
                    @ApiResponse(description = "Bad Request", responseCode = "400")
            })
    @PostMapping("/create")
    public ResponseEntity<BarberResponseDTO> createBarber(@RequestBody @Valid BarberRequestDTO dados, UriComponentsBuilder uriBuilder) {
        var barber = service.createBarber(dados);
        var uri = uriBuilder.path("/{id}").buildAndExpand(barber.id()).toUri();
        return ResponseEntity.created(uri).body(barber);
    }


    @Operation(description = "Updates specialties and status of a barber",
            summary = "Update barber",
            responses = {
                    @ApiResponse(description = "OK", responseCode = "200"),
                    @ApiResponse(description = "Not Found", responseCode = "404")
            })
    @PutMapping("/{id}")
    public ResponseEntity<BarberResponseDTO> updateBarber(@PathVariable Long id, @RequestBody @Valid BarberUpdateDTO dto) {
        var barber = service.updateBarber(id, dto);
        return ResponseEntity.ok(barber);
    }


    @Operation(description = "Deactivates a barber by ID",
            summary = "Deactivate barber",
            responses = {
                    @ApiResponse(description = "No Content", responseCode = "204"),
                    @ApiResponse(description = "Not Found", responseCode = "404")
            })
    @PatchMapping("/{id}/disable")
    public ResponseEntity<Void> disableBarber(@PathVariable Long id) {
        service.disableBarber(id);
        return ResponseEntity.noContent().build();
    }


}
