package ibar.task.ecommerce.demo.repositories;

import ibar.task.ecommerce.demo.models.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MerchantRepository extends JpaRepository<Merchant, Long> {
    Merchant findByName(String name);
}
