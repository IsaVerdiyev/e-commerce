package ibar.task.ecommerce.demo.filters;

import ibar.task.ecommerce.demo.utils.RequestResponseLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.http.ReactiveHttpOutputMessage;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.HandlerFilterFunction;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.cloud.gateway.support.ServerWebExchangeUtils.*;
import static org.springframework.web.reactive.function.BodyInserters.fromPublisher;
import static org.springframework.web.reactive.function.BodyInserters.fromValue;

@Component
public class HandlerLoggingFilter implements HandlerFilterFunction<ServerResponse, ServerResponse> {
    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    RequestResponseLogger requestResponseLogger;


    @Override
    public Mono<ServerResponse> filter(ServerRequest request, HandlerFunction<ServerResponse> next) {
        requestResponseLogger.logRequest(logger, request.exchange().getRequest());
        Mono<ServerResponse> response = next.handle(request);
        return response.zipWhen(s -> {
            logger.info("after response comes ");
            logger.info("CLIENT_RESPONSE_ATTR: " + request.exchange().getAttribute(CLIENT_RESPONSE_ATTR));
            return Mono.just(s);

        }, (m1, m2) -> m1);
    }
}
