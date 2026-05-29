package br.com.barberhub.controller;

import br.com.barberhub.dto.*;
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
@Tag(name = "User", description = "Controller for C.R.U.D of Users")
public class UserController {

    private final UserService service;

    @Operation(description = "Returns a list of all registered users",
            summary = "List all users",
            responses = {
                    @ApiResponse(description = "OK", responseCode = "200")
            })
    @GetMapping
    public ResponseEntity<List<UserResponseDTO>> findAll() {
        return ResponseEntity.ok(service.findAll());
    }


    @Operation(description = "Creates a new user",
            summary = "Create user",
            responses = {
                    @ApiResponse(description = "Created", responseCode = "201"),
                    @ApiResponse(description = "Bad Request", responseCode = "400")
            })
    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserDTO dto) {
        UserResponseDTO create = service.createUser(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(create);
    }


    @Operation(description = "Updates email and telephone of a user",
            summary = "Update user",
            responses = {
                    @ApiResponse(description = "OK", responseCode = "200"),
                    @ApiResponse(description = "Not Found", responseCode = "404")
            })
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDTO> updateUser(@PathVariable Long id, @Valid @RequestBody UserUpdateDTO dto) {
        UserResponseDTO update = service.updateUser(id, dto);
        return ResponseEntity.ok(update);
    }

    @Operation(description = "Changes the password of a user",
            summary = "Change password",
            responses = {
                    @ApiResponse(description = "No Content", responseCode = "204"),
                    @ApiResponse(description = "Not Found", responseCode = "404")
            })
    @PatchMapping("/{id}/change-password")
    public ResponseEntity<Void> resetPassword(@PathVariable Long id, @Valid @RequestBody ChangeMyPasswordDTO dto) {
        this.service.changeMyPassword(id, dto);
        return ResponseEntity.noContent().build();
    }

    @Operation(description = "Disables a user by ID",
            summary = "Disable user",
            responses = {
                    @ApiResponse(description = "No Content", responseCode = "204"),
                    @ApiResponse(description = "Not Found", responseCode = "404")
            })
    @PatchMapping("/{id}/disable")
    public ResponseEntity<Void> disable(@PathVariable Long id) {
        service.disableUser(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            description = "Assigns a role to a user by ID",
            summary = "Assign role to user",
            responses = {
                    @ApiResponse(description = "No Content", responseCode = "204"),
                    @ApiResponse(description = "Bad Request", responseCode = "400"),
                    @ApiResponse(description = "Not Found", responseCode = "404")
            })
    @PatchMapping("{id}/assign-role")
    public ResponseEntity<Void> assignRole(@PathVariable Long id, @Valid @RequestBody
    AssignRoleDTO dto) {
        this.service.assignRole(id, dto.role());

        return ResponseEntity.noContent().build();

    }


}
