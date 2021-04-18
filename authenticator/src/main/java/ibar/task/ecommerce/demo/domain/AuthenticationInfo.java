package ibar.task.ecommerce.demo.domain;

import javax.validation.constraints.NotBlank;

public class AuthenticationInfo {
    @NotBlank
    String merchantName;

    boolean isRemembered;

    public String getMerchantName() {
        return merchantName;
    }

    public boolean getIsRemembered() {
        return isRemembered;
    }
}
