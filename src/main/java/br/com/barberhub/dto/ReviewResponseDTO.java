package br.com.barberhub.dto;

import br.com.barberhub.entities.Review;

import java.time.LocalDateTime;

public record ReviewResponseDTO(

        Long id,
        Long barberId,
        Long userId,
        Double rating,
        String comment,
        LocalDateTime reviewDate

) {

    public ReviewResponseDTO(Review review) {
        this(
                review.getId(),
                review.getBarber().getId(),
                review.getUser().getId(),
                review.getRating(),
                review.getComment(),
                review.getReviewDate()

        );
    }


}
