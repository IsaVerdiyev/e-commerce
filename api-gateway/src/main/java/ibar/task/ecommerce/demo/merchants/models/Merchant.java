package ibar.task.ecommerce.demo.merchants.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.validation.constraints.NotBlank;

public class Merchant {

    @JsonProperty("type")
    @NotBlank(message = "type is mandatory")
    String type;

    @JsonProperty("name")
    @NotBlank(message = "name is mandatory")
    String name;

    @JsonProperty("ownerName")
    @NotBlank(message = "ownerName is mandatory")
    String ownerName;

    @JsonProperty("address")
    @NotBlank(message = "address is mandatory")
    String address;

    @JsonProperty("phoneNumber")
    @NotBlank(message = "phoneNumber is mandatory")
    String phoneNumber;

    @JsonProperty("emailAddress")
    @NotBlank(message = "emailAddress is mandatory")
    String emailAddress;

    @JsonProperty("password")
    @NotBlank(message = "password is mandatory")
    String password;

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
