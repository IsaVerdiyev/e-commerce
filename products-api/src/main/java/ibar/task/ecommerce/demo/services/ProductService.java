package ibar.task.ecommerce.demo.services;

import ibar.task.ecommerce.demo.models.DeliveryOptions;
import ibar.task.ecommerce.demo.models.Product;
import ibar.task.ecommerce.demo.repositories.DelivertyOptionsRepository;
import ibar.task.ecommerce.demo.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductService {

    @Autowired
    DelivertyOptionsRepository delivertyOptionsRepository;

    @Autowired
    ProductRepository productRepository;

    public Product addProduct(Product product){
        DeliveryOptions deliveryOptions = delivertyOptionsRepository.save(product.getDeliveryOptions());
        deliveryOptions.setProduct(product);
        product.setDeliveryOptions(deliveryOptions);
        product.getInventory().forEach(i -> i.setProduct(product));
        return productRepository.save(product);
    }
}
