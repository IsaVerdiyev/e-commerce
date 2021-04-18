package ibar.task.ecommerce.demo.repositories;

import ibar.task.ecommerce.demo.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
