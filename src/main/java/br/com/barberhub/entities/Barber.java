package br.com.barberhub.entities;

import br.com.barberhub.enums.Specialty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "barber")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Barber {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<Specialty> specialty;
    private Double rating;
    private Boolean active;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "barber")
    private List<Review> reviews;

    @OneToMany(mappedBy = "barber")
    private List<Appointment> appointments;

    public void desativar() {
        this.active = false;
    }


}
