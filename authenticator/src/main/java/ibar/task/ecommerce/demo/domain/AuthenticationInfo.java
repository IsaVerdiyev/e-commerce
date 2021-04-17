package ibar.task.ecommerce.demo.domain;

import javax.validation.constraints.NotBlank;

public class AuthenticationInfo {
    @NotBlank
    String merchantName;

    Boolean isRemembered;

    public String getMerchantName() {
        return merchantName;
    }

    public Boolean getIsRemembered() {
        return isRemembered;
    }
}
