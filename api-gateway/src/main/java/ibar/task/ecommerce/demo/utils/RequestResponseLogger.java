package ibar.task.ecommerce.demo.utils;

import org.slf4j.Logger;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;

@Component
public class RequestResponseLogger {

    public void logRequest(Logger logger, ServerHttpRequest request){
        logger.info("requestUrl: " + request.getMethod()+ " " + request.getURI().toString());
        logger.info("headers: " + request.getHeaders().toString());
    }
}
