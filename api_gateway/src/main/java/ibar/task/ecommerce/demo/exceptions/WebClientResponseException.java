package ibar.task.ecommerce.demo.exceptions;

import ibar.task.ecommerce.demo.errors.ApiSubError;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class WebClientResponseException extends Exception{
    HttpStatus httpStatus;
    String clientErrorContent;
    HttpHeaders headers;

    public WebClientResponseException(HttpStatus httpStatus, String clientErrorContent, HttpHeaders headers){
        this.httpStatus = httpStatus;
        this.clientErrorContent = clientErrorContent;
        this.headers = headers;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getClientErrorContent() {
        return clientErrorContent;
    }

    public void setClientErrorContent(String clientErrorContent) {
        this.clientErrorContent = clientErrorContent;
    }

    public HttpHeaders getHeaders() {
        return headers;
    }

    public void setHeaders(HttpHeaders headers) {
        this.headers = headers;
    }
}
