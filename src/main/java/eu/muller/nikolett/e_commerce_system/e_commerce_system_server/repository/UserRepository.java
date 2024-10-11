package eu.muller.nikolett.e_commerce_system.e_commerce_system_server.repository;

import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    long countByEmail(String email);

    Optional<User> findByEmail(String email);
}
