package br.com.barberhub.entities;

import br.com.barberhub.enums.Specialty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "service_item")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ServiceItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.STRING)
    private Specialty name;
    private BigDecimal price;
    private Integer durationInMinutes;


}
