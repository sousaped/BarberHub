package br.com.barberhub.service;

import br.com.barberhub.dto.ChangeMyPasswordDTO;
import br.com.barberhub.dto.UserDTO;
import br.com.barberhub.dto.UserResponseDTO;
import br.com.barberhub.dto.UserUpdateDTO;
import br.com.barberhub.entities.User;
import br.com.barberhub.enums.Role;
import br.com.barberhub.exceptions.BadRequestException;
import br.com.barberhub.exceptions.NotFoundException;
import br.com.barberhub.repository.IUserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final IUserRepository repository;

    public List<UserResponseDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(UserResponseDTO::new)
                .toList();

    }

    public UserResponseDTO createUser(UserDTO dto) throws BadRequestException {
        User user = new User();
        if (repository.findByEmail(dto.email()).isPresent()) {
            throw new BadRequestException("This user already exists with this email address");
        }

        user.setActive(true);
        user.setRole(Role.CLIENT);
        return new UserResponseDTO(repository.save(new User(dto)));

    }

    public void assignRole(Long id, Role role) {
        User user = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found!!"));

        user.setRole(role);
        this.repository.save(user);
    }

    public UserResponseDTO updateUser(Long id, UserUpdateDTO dto) {

        User user = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found!!"));

        repository.findByEmail(dto.email())
                .ifPresent(existing -> {
                    if (!existing.getId().equals(id)) {
                        throw new BadRequestException("Email already in use");
                    }
                });

        user.setEmail(dto.email());
        user.setTelephone(dto.telephone());
        user.setLastChangeDate(LocalDateTime.now());


        return new UserResponseDTO(repository.save(user));
    }

    public void changeMyPassword(Long id, ChangeMyPasswordDTO dto) {
        User user = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found!!"));

        if (!dto.currentPassword().equals(user.getPassword())) {
            throw new BadRequestException("Current password is incorrect");
        }

        if (!dto.newPassword().equals(dto.confirmNewPassword())) {
            throw new BadRequestException("Passwords don't match");
        }


        user.setPassword(dto.newPassword());
        user.setLastChangeDate(LocalDateTime.now());
        repository.save(user);

    }


    public void disableUser(Long id) {
        User user = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found!!"));
        user.disable();
        repository.save(user);

    }
}
