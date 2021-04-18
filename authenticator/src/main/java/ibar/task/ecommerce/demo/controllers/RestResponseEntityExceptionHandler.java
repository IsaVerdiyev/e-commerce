package ibar.task.ecommerce.demo.controllers;

import ibar.task.ecommerce.demo.controllers.errors.ApiError;
import ibar.task.ecommerce.demo.controllers.errors.ApiSubError;
import ibar.task.ecommerce.demo.controllers.errors.ApiValidationError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class RestResponseEntityExceptionHandler
        extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<ApiSubError> apiSubErrorList = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            String value = (String)(((FieldError) error).getRejectedValue());
            ApiSubError subError = new ApiValidationError(ex.getObjectName(), fieldName, value, errorMessage);
            apiSubErrorList.add(subError);
        });
        ApiError apiError = new ApiError(HttpStatus.BAD_REQUEST, "VALIDATION_ERROR", null, apiSubErrorList);
        return new ResponseEntity<>(apiError, status);
    }

    @ExceptionHandler(value = {Exception.class})
    protected ResponseEntity<Object> handleMethodArgumentNotValid(Exception ex,
                                                                  WebRequest request) {
        ApiError apiError = new ApiError(HttpStatus.INTERNAL_SERVER_ERROR, "UKNOWN EXCEPTION", ex.getClass().getName() + ": " + ex.getMessage(), null);
        return new ResponseEntity<>(apiError, apiError.getStatus());
    }
}
