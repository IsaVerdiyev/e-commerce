package ibar.task.ecommerce.demo.products.models;

import com.fasterxml.jackson.annotation.JsonIgnore;


public class InventoryItem {

    Long id;

    String name;

    @JsonIgnore
    Product product;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
