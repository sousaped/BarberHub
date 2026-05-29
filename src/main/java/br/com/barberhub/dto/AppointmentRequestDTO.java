package br.com.barberhub.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalTime;

public record AppointmentRequestDTO(

        @NotNull(message = "Barber is required")
        Long barberId,

        @NotNull(message = "User is required")
        Long userId,

        @NotNull(message = "Service is required")
        Long serviceItemId,

        @NotNull(message = "Date is required")
        @FutureOrPresent(message = "Date must be present or future")
        LocalDate availableDate,

        @NotNull(message = "Time is required")
        LocalTime availableTime

) {}