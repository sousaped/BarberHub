package br.com.barberhub.dto;

import br.com.barberhub.entities.Review;

import java.time.LocalDateTime;

public record ReviewResponseDTO(

        Long id,
        Double ratting,
        String comment,
        LocalDateTime reviewDate

) {

    public ReviewResponseDTO(Review review) {
        this(
                review.getId(),
                review.getRating(),
                review.getComment(),
                review.getReviewDate()

        );
    }



}
