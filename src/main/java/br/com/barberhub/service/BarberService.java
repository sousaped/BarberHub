package br.com.barberhub.service;

import br.com.barberhub.dto.BarberRequestDTO;
import br.com.barberhub.dto.BarberResponseDTO;
import br.com.barberhub.dto.BarberUpdateDTO;
import br.com.barberhub.entities.Barber;
import br.com.barberhub.entities.User;
import br.com.barberhub.enums.Specialty;
import br.com.barberhub.exceptions.BadRequestException;
import br.com.barberhub.exceptions.NotFoundException;
import br.com.barberhub.repository.IBarberRepository;
import br.com.barberhub.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BarberService {

    private final IBarberRepository repository;
    private final IUserRepository userRepository;

    public List<BarberResponseDTO> findAll() {
        return repository.findAll()
                .stream()
                .map(BarberResponseDTO::new)
                .toList();

    }

    public BarberResponseDTO findById(Long id) {
        return repository.findById(id)
                .map(BarberResponseDTO::new)
                .orElseThrow(() -> new NotFoundException("Barber not found with id " + id));
    }

    public BarberResponseDTO createBarber(BarberRequestDTO dto) {
        User user = userRepository.findById(dto.userId())
                .orElseThrow(() -> new NotFoundException("User not found!!"));

        if (repository.findByUserId(dto.userId()).isPresent()) {
            throw new BadRequestException("This user is already a barber");
        }

        Barber barber = new Barber();

        barber.setUser(user);
        barber.setName(dto.name());
        barber.setSpecialty(dto.specialty());
        barber.setActive(true);
        barber.setRating(0.0);


        return new BarberResponseDTO(repository.save(barber));
    }

    public BarberResponseDTO updateBarber(Long id, BarberUpdateDTO dto) {

        Barber barber = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Barber not found!!"));

        if (dto.specialty() != null) {
            barber.setSpecialty(dto.specialty());
            barber.setActive(dto.active());
        }

        if (dto.active() != null) {
            barber.setActive(dto.active());
        }

        Barber updated = repository.save(barber);

        return new BarberResponseDTO(updated);
    }

    public List<BarberResponseDTO> findBySpecialty(Specialty specialty) {
        return repository.findBySpecialty(specialty)
                .stream()
                .map(BarberResponseDTO::new)
                .toList();
    }

    public void disableBarber(Long id) {
        Barber barber = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Barber not found!!"));
        barber.disable();
        repository.save(barber);
    }


}
