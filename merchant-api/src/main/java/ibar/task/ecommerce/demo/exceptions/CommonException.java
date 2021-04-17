package ibar.task.ecommerce.demo.exceptions;

import ibar.task.ecommerce.demo.controllers.errors.ApiError;
import org.springframework.http.HttpStatus;

public class CommonException extends Exception{
    String error;
    HttpStatus statusCode;

    public CommonException(String error, HttpStatus statusCode) {
        this.statusCode = statusCode;
        this.error = error;
    }

    public CommonException(String error, HttpStatus statusCode, String message,Throwable cause) {
        super(message, cause);
        this.statusCode = statusCode;
        this.error = error;
    }

    public CommonException(String error, HttpStatus statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
        this.error = error;
    }

    public String getError() {
        return error;
    }

    public HttpStatus getStatusCode() {
        return statusCode;
    }

    public ApiError getApiError(){
        return new ApiError(statusCode, error, getMessage(), null);
    }
}
