package br.com.barberhub.dto;

import br.com.barberhub.enums.Role;
import jakarta.validation.constraints.NotNull;

public record AssignRoleDTO(

        @NotNull(message = "Role is required")
        Role role
) {


}
