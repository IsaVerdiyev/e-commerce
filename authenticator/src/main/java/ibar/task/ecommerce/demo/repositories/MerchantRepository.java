package ibar.task.ecommerce.demo.repositories;

import ibar.task.ecommerce.demo.models.Merchant;
import org.springframework.data.repository.CrudRepository;

public interface MerchantRepository extends CrudRepository<Merchant, Long> {
}
