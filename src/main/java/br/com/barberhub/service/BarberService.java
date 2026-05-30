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
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.stereotype.Service;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

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
        barber.setName(user.getName());
        barber.setSpecialty(dto.specialty());
        barber.setActive(true);
        barber.setRating(0.0);


        return new BarberResponseDTO(repository.save(barber));
    }

    public BarberResponseDTO updateBarber(Long id, BarberUpdateDTO dto) throws HttpMessageNotReadableException {

        Barber barber = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Barber not found!!"));

        if (dto.specialty() != null) {
            List<Specialty> current = barber.getSpecialty();
            dto.specialty().forEach(specialty -> {
                if (!current.contains(specialty)) {
                    current.add(specialty);
                }
            });
            barber.setSpecialty(current);
        }


        return new BarberResponseDTO(repository.save(barber));
    }

    public List<BarberResponseDTO> findBySpecialty(Specialty specialty) throws MethodArgumentTypeMismatchException {
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
