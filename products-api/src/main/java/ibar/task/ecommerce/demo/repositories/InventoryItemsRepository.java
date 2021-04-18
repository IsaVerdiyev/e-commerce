package ibar.task.ecommerce.demo.repositories;

import ibar.task.ecommerce.demo.models.InventoryItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InventoryItemsRepository extends JpaRepository<InventoryItem, Long> {
}
