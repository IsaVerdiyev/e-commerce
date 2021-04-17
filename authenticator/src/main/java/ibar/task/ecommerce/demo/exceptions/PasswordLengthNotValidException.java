package ibar.task.ecommerce.demo.exceptions;

import ibar.task.ecommerce.demo.exceptions.CommonException;
import org.springframework.http.HttpStatus;

public class PasswordLengthNotValidException extends CommonException {

    public PasswordLengthNotValidException(){
        super( "PASSWORD LENGTH NOT VALID", HttpStatus.BAD_REQUEST, "password length is too small");
    }
}
