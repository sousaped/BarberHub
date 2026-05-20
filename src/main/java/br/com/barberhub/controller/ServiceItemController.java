package br.com.barberhub.controller;

import br.com.barberhub.dto.ServiceItemRequestDTO;
import br.com.barberhub.dto.ServiceItemResponseDTO;
import br.com.barberhub.dto.ServiceItemUpdateDTO;
import br.com.barberhub.entities.ServiceItem;
import br.com.barberhub.service.ServiceItemService;
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
@Tag(name = "Service Item", description = "Controller para serviços do Barbeiro")
public class ServiceItemController {

    private final ServiceItemService service;

    @GetMapping
    public ResponseEntity<List<ServiceItemResponseDTO>> listar() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceItemResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<ServiceItemResponseDTO> createSeviceItem(@Valid @RequestBody ServiceItemRequestDTO dto, UriComponentsBuilder uriBuilder) {
        var serviceItem = service.createServiceItem(dto);
        var uri = uriBuilder.path("/{id}").buildAndExpand(serviceItem.id()).toUri();
        return ResponseEntity.created(uri).body(serviceItem);
    }


    @PutMapping("/{id}")
    public ResponseEntity<ServiceItemResponseDTO> update(@PathVariable Long id, @Valid @RequestBody ServiceItemUpdateDTO dto) {
        var serviceItem = service.updateServiceItem(id,dto);
        return ResponseEntity.ok(serviceItem);
    }

    @PatchMapping("/{id}/disable")
    public ResponseEntity<Void> disableServiceItem(@PathVariable Long id) {
        service.disableServiceItem(id);
        return ResponseEntity.noContent().build();
    }


}
