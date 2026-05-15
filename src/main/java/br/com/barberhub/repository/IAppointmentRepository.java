package br.com.barberhub.repository;

import br.com.barberhub.entities.Appointment;
import br.com.barberhub.entities.Review;
import br.com.barberhub.enums.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IAppointmentRepository extends JpaRepository<Appointment, Long> {

    Optional<Appointment> findByUserIdAndBarberIdAndStatus(Long userId, Long barberId, AppointmentStatus status);
}
