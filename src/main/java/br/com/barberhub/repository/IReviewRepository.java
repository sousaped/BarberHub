package br.com.barberhub.repository;

import br.com.barberhub.entities.Review;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface IReviewRepository extends JpaRepository<Review, Long> {

    List<Review> findByBarberId(Long barberId);

    Optional<Review> findByUserIdAndBarberId(Long userId, Long barberId);


}
