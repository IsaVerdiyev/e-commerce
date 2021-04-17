package ibar.task.ecommerce.demo.exceptions;

import ibar.task.ecommerce.demo.exceptions.CommonException;
import org.springframework.http.HttpStatus;

public class PasswordContentException extends CommonException {

    public PasswordContentException(){
        super( "PASSWORD CONTENT IS VALID", HttpStatus.BAD_REQUEST, "password content is not valid");
    }
}

