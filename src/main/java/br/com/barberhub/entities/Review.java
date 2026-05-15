package br.com.barberhub.entities;

import br.com.barberhub.dto.ReviewDTO;
import br.com.barberhub.dto.ReviewResponseDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "review")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double rating;
    private LocalDateTime reviewDate;
    private String comment;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "barber_id")
    private Barber barber;


    public Review(ReviewResponseDTO dto) {
        this.id = dto.id();
        this.rating = dto.ratting();
        this.comment = dto.comment();
        this.reviewDate = dto.reviewDate();
    }
}
