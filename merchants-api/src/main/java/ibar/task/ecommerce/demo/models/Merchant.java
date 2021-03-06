package ibar.task.ecommerce.demo.models;

import ibar.task.ecommerce.demo.repositories.MerchantRepository;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
public class Merchant {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;

    @NotBlank(message = "type is mandatory")
    String type;

    @NotBlank(message = "name is mandatory")
    @Column(unique = true)
    String name;

    @NotBlank(message = "ownerName is mandatory")
    String ownerName;

    @NotBlank(message = "address is mandatory")
    String address;

    @NotBlank(message = "phoneNumber is mandatory")
    String phoneNumber;

    @NotBlank(message = "emailAddress is mandatory")
    String emailAddress;

    @NotBlank(message = "password is mandatory")
    String password;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean validatePassword(String password){
        return this.password.equals(password);
    }
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
