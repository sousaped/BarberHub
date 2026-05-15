package br.com.barberhub.controller;

import br.com.barberhub.dto.ChangeMyPasswordDTO;
import br.com.barberhub.dto.UserDTO;
import br.com.barberhub.dto.UserResponseDTO;
import br.com.barberhub.dto.UserUpdateDTO;
import br.com.barberhub.entities.User;
import br.com.barberhub.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/user")
@RequiredArgsConstructor
@Validated
@Tag(name = "User", description = "Controller para crud de Usuários")
public class UserController {

    private final UserService service;

    @Operation(description = "Busca todos os usuários",
            summary = "Busca de usuários",
            responses = {
                    @ApiResponse(description = "OK", responseCode = "200")
            })
    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }


    @Operation(description = "Criação usuários",
            summary = "Criação usuários",
            responses = {
                    @ApiResponse(description = "CREATED", responseCode = "201")
            })
    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserDTO dto) {
        UserResponseDTO create = service.createUser(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(create);
    }


    @Operation(description = "Alteração usuários",
            summary = "Alteração usuários",
            responses = {
                    @ApiResponse(description = "OK", responseCode = "200")
            })
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long id, @Valid @RequestBody UserUpdateDTO dto) {
        UserResponseDTO update = service.updateUser(id, dto);
        return ResponseEntity.ok(update);
    }

    @Operation(description = "Trocar Senha",
            summary = "Trocando a senha do Usuario")
    @PatchMapping("/{id}/change-password")
    public ResponseEntity<Void> resetPassword(@PathVariable Long id,@Valid @RequestBody ChangeMyPasswordDTO dto) {
        this.service.changeMyPassword(id, dto);
        return ResponseEntity.noContent().build();
    }


    @Operation(description = "Exclusão usuários",
            summary = "Exclusão usuários")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        this.service.deleteUser(id);

        return ResponseEntity.noContent().build();
    }
}
