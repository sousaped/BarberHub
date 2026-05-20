package br.com.barberhub.repository;

import br.com.barberhub.entities.ServiceItem;
import br.com.barberhub.enums.Specialty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface IServiceItem extends JpaRepository<ServiceItem,Long> {

    Optional<ServiceItem> findById(Long id);


    Optional<ServiceItem> findByName(Specialty name);


}
