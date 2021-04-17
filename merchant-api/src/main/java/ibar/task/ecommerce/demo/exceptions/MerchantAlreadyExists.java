package ibar.task.ecommerce.demo.exceptions;

import ibar.task.ecommerce.demo.exceptions.CommonException;
import org.springframework.http.HttpStatus;

public class MerchantAlreadyExists extends CommonException {

    public MerchantAlreadyExists(){
        super("MERCHANT EXISTS", HttpStatus.BAD_REQUEST, "merchant with this name already exists");
    }
}


