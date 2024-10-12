package eu.muller.nikolett.e_commerce_system.e_commerce_system_server.repository;


import eu.muller.nikolett.e_commerce_system.e_commerce_system_server.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
}
