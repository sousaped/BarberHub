package br.com.barberhub.service;

import br.com.barberhub.dto.ReviewResponseDTO;
import br.com.barberhub.entities.Appointment;
import br.com.barberhub.entities.Barber;
import br.com.barberhub.entities.Review;
import br.com.barberhub.entities.User;
import br.com.barberhub.enums.AppointmentStatus;
import br.com.barberhub.exceptions.BadRequestException;
import br.com.barberhub.exceptions.NotFoundException;
import br.com.barberhub.repository.IAppointmentRepository;
import br.com.barberhub.repository.IBarberRepository;
import br.com.barberhub.repository.IReviewRepository;
import br.com.barberhub.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final IReviewRepository repository;
    private final IBarberRepository barberRepository;
    private final IAppointmentRepository appointmentRepository;
    private final IUserRepository userRepository;

    public List<ReviewResponseDTO> findByBarber(Long barberId) {
        return repository.findByBarberId(barberId)
                .stream()
                .map(ReviewResponseDTO::new)
                .toList();


    }


    public ReviewResponseDTO createAssessment(Long id, ReviewResponseDTO dto) {
        AppointmentStatus status;

        User user = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("User not found!!"));

        Barber barber = barberRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Barber not found!!"));

        if (appointmentRepository.findByUserIdAndBarberIdAndStatus(user.getId(), barber.getId(), AppointmentStatus.COMPLETED).isEmpty()) {
            throw new BadRequestException("User has no completed appointment with this barber");
        }

        Review review = new Review();
        review.setBarber(barber);
        review.setUser(user);
        review.setRating(dto.ratting());
        review.setComment(dto.comment());
        review.setReviewDate(dto.reviewDate());

        return new ReviewResponseDTO(repository.save(review));


    }

    public void deleteAssessment(Long id) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Appointment not found!!"));

        repository.deleteById(id);
    }




    public List<ReviewResponseDTO> findAllRattingByBarber(Long barberId) {
        var media = repository.findAllRattingByBarberId(barberId);


        return null;

    }



}
