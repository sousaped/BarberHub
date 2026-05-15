package br.com.barberhub.entities;

import br.com.barberhub.dto.UserDTO;
import br.com.barberhub.dto.UserResponseDTO;
import br.com.barberhub.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.webmvc.autoconfigure.WebMvcProperties;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String telephone;

    private String password;

    @Column(name = "last_change_date")
    private LocalDateTime lastChangeDate;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne
    @JoinColumn(name = "barber_id")
    private Barber barber;


    public User(UserDTO dto) {
        this.email = dto.email();
        this.telephone = dto.telephone();
        this.password = dto.password();
    }



}
