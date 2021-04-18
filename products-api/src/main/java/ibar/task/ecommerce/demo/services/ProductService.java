package ibar.task.ecommerce.demo.services;

import ibar.task.ecommerce.demo.models.DeliveryOptions;
import ibar.task.ecommerce.demo.models.Product;
import ibar.task.ecommerce.demo.repositories.DelivertyOptionsRepository;
import ibar.task.ecommerce.demo.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Service
public class ProductService {

    @Autowired
    DelivertyOptionsRepository delivertyOptionsRepository;

    @Autowired
    ProductRepository productRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public Product addProduct(Product product) {
        DeliveryOptions deliveryOptions = delivertyOptionsRepository.save(product.getDeliveryOptions());
        deliveryOptions.setProduct(product);
        product.setDeliveryOptions(deliveryOptions);
        product.getInventoryItems().forEach(i -> i.setProduct(product));
        return productRepository.save(product);
    }

    public List<Product> getProducts(Map<String, String> params) {
        Long pageSize = Optional.ofNullable(params.get("pageSize")).map(i -> Long.parseLong(i)).orElse(productRepository.count());
        Integer pageNumber = Optional.ofNullable(params.get("pageNumber")).map(i -> Integer.parseInt(i)).orElse(0);
        Pageable page = PageRequest.of(pageNumber, pageSize.intValue());
        return getProductsByQueryParameters(params, pageNumber, pageSize.intValue());
    }

    List<Product> getProductsByQueryParameters(Map<String, String> params, Integer pageNumber, Integer pageSize) {
        String query = "select p from Product p join p.inventoryItems i group by p";
        boolean orderByAdded = false;
        for (Map.Entry<String, String> keyValue : params.entrySet()) {
            String key = keyValue.getKey();
            switch (key) {
                case "sortByPriceAsc":
                    if (Boolean.valueOf(keyValue.getValue())) {
                        if (!orderByAdded) {
                            query += " order by p.unitPrice";
                            orderByAdded = true;
                        } else {
                            query += ", p.unitPrice";
                        }
                    }
                    break;
                case "sortByPriceDesc":
                    if (Boolean.valueOf(keyValue.getValue())) {
                        if (!orderByAdded) {
                            query += " order by p.unitPrice desc";
                            orderByAdded = true;
                        } else {
                            query += ", p.unitPrice desc";
                        }
                    }
                    break;
                case "sortByInventorySizeAsc":
                    if (Boolean.valueOf(keyValue.getValue())) {
                        if (!orderByAdded) {
                            query += " order by i.size";
                            orderByAdded = true;
                        } else {
                            query += ", i.size";
                        }
                    }
                    break;
                case "sortByInventorySizeDesc":
                    if (Boolean.valueOf(keyValue.getValue())) {
                        if (!orderByAdded) {
                            query += " order by i.size desc";
                            orderByAdded = true;
                        } else {
                            query += ", i.size desc";
                        }
                    }
                    break;
            }
        }
        Query jpaQuery = entityManager.createQuery(query).setFirstResult(pageNumber * pageSize).setMaxResults((pageNumber + 1) * pageSize);
        return jpaQuery.getResultList();
    }
}
