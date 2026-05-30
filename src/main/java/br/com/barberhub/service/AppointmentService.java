package br.com.barberhub.service;

import br.com.barberhub.dto.AppointmentRequestDTO;
import br.com.barberhub.dto.AppointmentResponseDTO;
import br.com.barberhub.entities.Appointment;
import br.com.barberhub.entities.Barber;
import br.com.barberhub.entities.ServiceItem;
import br.com.barberhub.entities.User;
import br.com.barberhub.enums.AppointmentStatus;
import br.com.barberhub.exceptions.BadRequestException;
import br.com.barberhub.exceptions.NotFoundException;
import br.com.barberhub.repository.IAppointmentRepository;
import br.com.barberhub.repository.IBarberRepository;
import br.com.barberhub.repository.IServiceItem;
import br.com.barberhub.repository.IUserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class AppointmentService {

    private final IAppointmentRepository repository;
    private final IBarberRepository barberRepository;
    private final IUserRepository userRepository;
    private final IServiceItem serviceItemRepository;

    public List<AppointmentResponseDTO> findAllAppointments() {
        return repository.findAll()
                .stream()
                .map(AppointmentResponseDTO::new)
                .toList();
    }

    public List<AppointmentResponseDTO> findByBarber(Long id) {

        Barber barber = barberRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Barber not found"));

        return repository.findByBarberId(barber.getId())
                .stream()
                .map(AppointmentResponseDTO::new)
                .toList();


    }

    public List<AppointmentResponseDTO> findByUser(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found"));

        return repository.findByUserId(user.getId())
                .stream()
                .map(AppointmentResponseDTO::new)
                .toList();

    }

    public AppointmentResponseDTO createAppointment(AppointmentRequestDTO dto) {

        User user = userRepository.findById(dto.userId())
                .orElseThrow(() -> new NotFoundException("User not found"));

        Barber barber = barberRepository.findById(dto.barberId())
                .orElseThrow(() -> new NotFoundException("Barber not found"));

        ServiceItem serviceItem = serviceItemRepository.findById(dto.serviceItemId())
                .orElseThrow(() -> new NotFoundException("Service not found"));

        List<Appointment> appointments = repository.findByBarberIdAndAvailableDateAndStatus(
                dto.barberId(),
                dto.availableDate(),
                AppointmentStatus.SCHEDULED
        );

        for (Appointment existing : appointments) {
            LocalTime existingStart = existing.getAvailableTime();
            LocalTime existingEnd = existingStart.plusMinutes(
                    existing.getServiceItem().getDurationInMinutes()
            );

            LocalTime newStart = dto.availableTime();

            if (newStart.isBefore(existingEnd) && newStart.isAfter(existingStart.minusMinutes(1))) {
                throw new BadRequestException(
                        "Barber is not available. Next available time: " + existingEnd
                );
            }
        }

        if (!user.getActive()) {
            throw new BadRequestException("This user is inactive");
        }

        if (!barber.getActive()) {
            throw new BadRequestException("This barber is inactive");
        }

        if (repository.findByBarberIdAndAvailableDateAndAvailableTime(
                dto.barberId(),
                dto.availableDate(),
                dto.availableTime()).isPresent()) {
            throw new BadRequestException("Barber is not available at this time");

        }

        if (repository.findByUserIdAndAvailableDateAndAvailableTime(
                dto.userId(),
                dto.availableDate(),
                dto.availableTime()).isPresent()) {
            throw new BadRequestException("User already has an appointment at this time");
        }


        Appointment appointment = new Appointment();
        appointment.setUser(user);
        appointment.setBarber(barber);
        appointment.setServiceItem(serviceItem);
        appointment.setAvailableDate(dto.availableDate());
        appointment.setAvailableTime(dto.availableTime());
        appointment.setStatus(AppointmentStatus.SCHEDULED);


        return new AppointmentResponseDTO(repository.save(appointment));
    }


    public void cancelAppointment(Long id) {
        Appointment appointment = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Appointment not found"));

        repository.delete(appointment);
    }

    public void completeAppointment(Long id) {
        Appointment appointment = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Appointment not found"));

        if (appointment.getStatus() == AppointmentStatus.COMPLETED) {
            throw new BadRequestException("Appointment is already completed");
        }

        if (appointment.getStatus() == AppointmentStatus.CANCELLED) {
            throw new BadRequestException("Appointment is already cancelled");
        }


        appointment.completed();
        repository.save(appointment);
    }


}

