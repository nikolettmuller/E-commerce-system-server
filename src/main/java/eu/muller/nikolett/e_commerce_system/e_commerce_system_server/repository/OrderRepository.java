package eu.muller.nikolett.e_commerce_system.e_commerce_system_server.repository;

import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
}
