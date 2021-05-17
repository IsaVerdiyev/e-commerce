package ibar.task.ecommerce.demo.products.models;

import com.fasterxml.jackson.annotation.JsonIgnore;


public class DeliveryOptions {
    Long id;

    String someDeliveryOption;

    @JsonIgnore
    Product product;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSomeDeliveryOption() {
        return someDeliveryOption;
    }

    public void setSomeDeliveryOption(String someDeliveryOption) {
        this.someDeliveryOption = someDeliveryOption;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }
}
