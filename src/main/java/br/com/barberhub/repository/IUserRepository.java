package br.com.barberhub.repository;

import br.com.barberhub.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface IUserRepository extends JpaRepository<User, Long> {


    Optional<User> findByEmail(String email);


}
