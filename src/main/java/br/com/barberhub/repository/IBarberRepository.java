package br.com.barberhub.repository;

import br.com.barberhub.entities.Barber;
import br.com.barberhub.enums.Specialty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface IBarberRepository extends JpaRepository<Barber, Long> {

    Optional<Barber> findByUserId(Long userId);

    @Query("SELECT b FROM Barber b JOIN b.specialty s WHERE s = :specialty")
    List<Barber> findBySpecialty(@Param("specialty") Specialty specialty);
}
