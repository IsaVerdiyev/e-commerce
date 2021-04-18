package ibar.task.ecommerce.demo.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "delivery_options")
public class DeliveryOptions {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    String someDeliveryOption;

    @OneToOne(mappedBy = "deliveryOptions")
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
