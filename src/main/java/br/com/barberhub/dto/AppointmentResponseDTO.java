package br.com.barberhub.dto;

import br.com.barberhub.entities.Appointment;
import br.com.barberhub.enums.AppointmentStatus;

import java.time.LocalDate;
import java.time.LocalTime;

public record AppointmentResponseDTO(
        Long id,
        LocalDate availableDate,
        LocalTime availableTime,
        AppointmentStatus status,
        String barberName,
        String userName,
        String serviceItemName
) {
    public AppointmentResponseDTO(Appointment appointment) {
        this(
                appointment.getId(),
                appointment.getAvailableDate(),
                appointment.getAvailableTime(),
                appointment.getStatus(),
                appointment.getBarber().getName(),
                appointment.getUser().getName(),
                appointment.getServiceItem().getName().name()
        );
    }
}
