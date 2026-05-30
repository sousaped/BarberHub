package br.com.barberhub.service;

import br.com.barberhub.dto.ReviewRequestDTO;
import br.com.barberhub.dto.ReviewResponseDTO;
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
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ReviewService {

    private final IReviewRepository repository;
    private final IBarberRepository barberRepository;
    private final IAppointmentRepository appointmentRepository;
    private final IUserRepository userRepository;

    public List<ReviewResponseDTO> findAllByBarber() {
        return repository.findAll()
                .stream()
                .map(ReviewResponseDTO::new)
                .toList();
    }


    public List<ReviewResponseDTO> findByBarber(Long barberId) {
        return repository.findByBarberId(barberId)
                .stream()
                .map(ReviewResponseDTO::new)
                .toList();

    }

    public ReviewResponseDTO createReview(ReviewRequestDTO dto) {

        User user = userRepository.findById(dto.userId())
                .orElseThrow(() -> new NotFoundException("User not found"));

        Barber barber = barberRepository.findById(dto.barberId())
                .orElseThrow(() -> new NotFoundException("Barber not found"));

        if (appointmentRepository.findByUserIdAndBarberIdAndStatus(
                user.getId(),
                barber.getId(),
                AppointmentStatus.COMPLETED).isEmpty()) {
            throw new BadRequestException("User has no completed appointment with this barber");
        }

        if (repository.findByUserIdAndBarberId(user.getId(), barber.getId()).isPresent()) {
            throw new BadRequestException("User has already reviewed this barber");
        }


        Review review = new Review();
        review.setBarber(barber);
        review.setUser(user);
        review.setRating(dto.rating());
        review.setComment(dto.comment());
        review.setReviewDate(LocalDateTime.now());

        ReviewResponseDTO response = new ReviewResponseDTO(repository.save(review));

        calculateRating(dto.barberId());

        return response;
    }

    public void deleteReview(Long id) {
        Review review = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Review not found"));

        Long barberId = review.getBarber().getId();
        repository.delete(review);

        calculateRating(barberId);

    }


    public void calculateRating(Long barberId) {
        List<Review> reviews = repository.findByBarberId(barberId);

        double average = reviews.stream()
                .mapToDouble(Review::getRating)
                .average()
                .orElse(0.0);

        Barber barber = barberRepository.findById(barberId)
                .orElseThrow(() -> new NotFoundException("Barber not found!!"));

        barber.setRating(average);

        barberRepository.save(barber);


    }


}
