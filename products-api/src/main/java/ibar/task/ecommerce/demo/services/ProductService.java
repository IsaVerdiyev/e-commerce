package ibar.task.ecommerce.demo.services;

import ibar.task.ecommerce.demo.models.DeliveryOptions;
import ibar.task.ecommerce.demo.models.Product;
import ibar.task.ecommerce.demo.repositories.DelivertyOptionsRepository;
import ibar.task.ecommerce.demo.repositories.ProductRepository;
import ibar.task.ecommerce.demo.repositories.QueryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.*;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ProductService {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    DelivertyOptionsRepository delivertyOptionsRepository;

    @Autowired
    ProductRepository productRepository;

    @PersistenceContext
    EntityManager entityManager;



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
        QueryBuilder queryBuilder = new QueryBuilder("select p from Product p join p.inventoryItems i").groupBy("p");
        for (Map.Entry<String, String> keyValue : params.entrySet()) {
            String key = keyValue.getKey();
            switch (key) {
                case "sortByPriceAsc":
                    if (Boolean.valueOf(keyValue.getValue())) {
                        queryBuilder = queryBuilder.orderBy("p.unitPrice");
                    }
                    break;
                case "sortByPriceDesc":
                    if (Boolean.valueOf(keyValue.getValue())) {
                        queryBuilder = queryBuilder.orderBy("p.unitPrice desc");
                    }
                    break;
                case "sortByInventorySizeAsc":
                    if (Boolean.valueOf(keyValue.getValue())) {
                        queryBuilder = queryBuilder.orderBy("i.size");
                    }
                    break;
                case "sortByInventorySizeDesc":
                    if (Boolean.valueOf(keyValue.getValue())) {
                        queryBuilder = queryBuilder.orderBy("i.size desc");
                    }
                    break;
                case "inventorySize_gth":
                    queryBuilder = queryBuilder.where("i.size > " + keyValue.getValue());
                    break;
                case "merchantId":
                    queryBuilder = queryBuilder.where("p.merchantId = " + keyValue.getValue());
                    break;
            }
        }
        logger.info("resultQuery: " + queryBuilder.getQuery());
        Query jpaQuery = entityManager.createQuery(queryBuilder.getQuery()).setFirstResult(pageNumber * pageSize).setMaxResults((pageNumber + 1) * pageSize);
        return jpaQuery.getResultList();
    }
}
