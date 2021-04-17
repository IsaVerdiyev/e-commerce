package ibar.task.ecommerce.demo.controllers;

import org.springframework.http.HttpStatus;

public class CommonException extends Exception{
    HttpStatus statusCode;
    String error;

    public CommonException(HttpStatus statusCode, String error) {
        this.statusCode = statusCode;
        this.error = error;
    }

    public CommonException(String message, HttpStatus statusCode, String error) {
        super(message);
        this.statusCode = statusCode;
        this.error = error;
    }

    public CommonException(String message, Throwable cause, HttpStatus statusCode, String error) {
        super(message, cause);
        this.statusCode = statusCode;
        this.error = error;
    }

    public CommonException(Throwable cause, HttpStatus statusCode, String error) {
        super(cause);
        this.statusCode = statusCode;
        this.error = error;
    }

    public CommonException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, HttpStatus statusCode, String error) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.statusCode = statusCode;
        this.error = error;
    }

    public ApiError getApiError(){
        return new ApiError(statusCode, error, getMessage(), null);
    }
}
