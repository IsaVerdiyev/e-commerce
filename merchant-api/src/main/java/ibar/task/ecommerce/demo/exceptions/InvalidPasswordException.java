package ibar.task.ecommerce.demo.exceptions;

import org.springframework.http.HttpStatus;

public class InvalidPasswordException extends CommonException {

    public InvalidPasswordException(){
        super("INVALID PASSWORD", HttpStatus.BAD_REQUEST);
    }
}
