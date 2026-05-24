package br.com.barberhub.controller;

import br.com.barberhub.dto.AppointmentRequestDTO;
import br.com.barberhub.dto.AppointmentResponseDTO;
import br.com.barberhub.service.AppointmentService;
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
@RequestMapping("/v1/appointment")
@RequiredArgsConstructor
@Validated
@Tag(name = "Appointment", description = "Controller for management of Appointments")
public class AppointmentController {

    private final AppointmentService service;


    @Operation(description = "Returns a list of all registered appointments",
            summary = "List all appointments",
            responses = {
                    @ApiResponse(description = "OK", responseCode = "200")
            })
    @GetMapping
    public ResponseEntity<List<AppointmentResponseDTO>> findAllAppointment() {
        return ResponseEntity.ok(service.findAllAppointments());
    }

    @Operation(description = "Returns a list of appointments filtered by barber",
            summary = "Find appointments by barber",
            responses = {
                    @ApiResponse(description = "OK", responseCode = "200"),
                    @ApiResponse(description = "Not Found", responseCode = "404")
            })
    @GetMapping("/barber/{id}")
    public ResponseEntity<List<AppointmentResponseDTO>> findByBarberId(@PathVariable Long id) {
        return ResponseEntity.ok(service.findByBarber(id));

    }

    @Operation(description = "Returns a list of appointments filtered by user",
            summary = "Find appointments by user",
            responses = {
                    @ApiResponse(description = "OK", responseCode = "200"),
                    @ApiResponse(description = "Not Found", responseCode = "404")
            })
    @GetMapping("/user/{id}")
    public ResponseEntity<List<AppointmentResponseDTO>> findByUserId(@PathVariable Long id) {
        return ResponseEntity.ok(service.findByUser(id));

    }


    @Operation(description = "Creates a new appointment with availability validations",
            summary = "Create appointment",
            responses = {
                    @ApiResponse(description = "Created", responseCode = "201"),
                    @ApiResponse(description = "Bad Request", responseCode = "400")
            })
    @PostMapping("/create")
    public ResponseEntity<AppointmentResponseDTO> createAppointment(@RequestBody @Valid AppointmentRequestDTO dto, UriComponentsBuilder uriBuilder) {
        var appointment = service.createAppointment(dto);
        var uri = uriBuilder.path("/{id}").buildAndExpand(appointment.id()).toUri();
        return ResponseEntity.created(uri).body(appointment);
    }

    @Operation(description = "Cancels an appointment by ID",
            summary = "Cancel appointment",
            responses = {
                    @ApiResponse(description = "No Content", responseCode = "204"),
                    @ApiResponse(description = "Not Found", responseCode = "404"),
                    @ApiResponse(description = "Bad Request", responseCode = "400")
            })
    @PatchMapping("/{id}/cancel")
    public ResponseEntity<Void> cancelAppointment(@PathVariable Long id) {
        service.cancelAppointment(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(description = "Completes an appointment by ID",
            summary = "Complete appointment",
            responses = {
                    @ApiResponse(description = "No Content", responseCode = "204"),
                    @ApiResponse(description = "Not Found", responseCode = "404"),
                    @ApiResponse(description = "Bad Request", responseCode = "400")
            })
    @PatchMapping("/{id}/complete")
    public ResponseEntity<Void> completeAppointment(@PathVariable Long id) {
        service.completeAppointment(id);
        return ResponseEntity.noContent().build();
    }


}
