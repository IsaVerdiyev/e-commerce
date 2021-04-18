package ibar.task.ecommerce.demo.exceptions;

import org.springframework.http.HttpStatus;

public class MerchantNotFoundException extends CommonException {

    public MerchantNotFoundException(){
        super("MERCHANT NOT FOUND", HttpStatus.NOT_FOUND, "merchant with this name doesn't exist");
    }
}
