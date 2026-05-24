package br.com.barberhub.controller;

import br.com.barberhub.dto.ServiceItemRequestDTO;
import br.com.barberhub.dto.ServiceItemResponseDTO;
import br.com.barberhub.dto.ServiceItemUpdateDTO;
import br.com.barberhub.service.ServiceItemService;
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
@RequestMapping("/v1/serviceitem")
@RequiredArgsConstructor
@Validated
@Tag(name = "Service Item", description = "Controller for C.R.U.D of Service Items")
public class ServiceItemController {

    private final ServiceItemService service;

    @Operation(description = "Returns a list of all registered service items",
            summary = "List all service items",
            responses = {
                    @ApiResponse(description = "OK", responseCode = "200")
            })
    @GetMapping
    public ResponseEntity<List<ServiceItemResponseDTO>> listar() {
        return ResponseEntity.ok(service.findAll());
    }

    @Operation(description = "Returns a service item by ID",
            summary = "Find service item by ID",
            responses = {
                    @ApiResponse(description = "OK", responseCode = "200"),
                    @ApiResponse(description = "Not Found", responseCode = "404")
            })
    @GetMapping("/{id}")
    public ResponseEntity<ServiceItemResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @Operation(description = "Creates a new service item",
            summary = "Create service item",
            responses = {
                    @ApiResponse(description = "Created", responseCode = "201"),
                    @ApiResponse(description = "Bad Request", responseCode = "400")
            })
    @PostMapping("/create")
    public ResponseEntity<ServiceItemResponseDTO> createSeviceItem(@Valid @RequestBody ServiceItemRequestDTO dto, UriComponentsBuilder uriBuilder) {
        var serviceItem = service.createServiceItem(dto);
        var uri = uriBuilder.path("/{id}").buildAndExpand(serviceItem.id()).toUri();
        return ResponseEntity.created(uri).body(serviceItem);
    }

    @Operation(description = "Updates price and duration of a service item",
            summary = "Update service item",
            responses = {
                    @ApiResponse(description = "OK", responseCode = "200"),
                    @ApiResponse(description = "Not Found", responseCode = "404")
            })
    @PutMapping("/{id}")
    public ResponseEntity<ServiceItemResponseDTO> update(@PathVariable Long id, @Valid @RequestBody ServiceItemUpdateDTO dto) {
        var serviceItem = service.updateServiceItem(id, dto);
        return ResponseEntity.ok(serviceItem);
    }

    @Operation(description = "Disables a service item by ID",
            summary = "Disable service item",
            responses = {
                    @ApiResponse(description = "No Content", responseCode = "204"),
                    @ApiResponse(description = "Not Found", responseCode = "404")
            })
    @PatchMapping("/{id}/disable")
    public ResponseEntity<Void> disableServiceItem(@PathVariable Long id) {
        service.disableServiceItem(id);
        return ResponseEntity.noContent().build();
    }


}
