package ibar.task.ecommerce.demo.merchants;

import ibar.task.ecommerce.demo.filters.HandlerLoggingFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.POST;

@Configuration
public class MerchantsConfiguration {

    @Value("${merchants.url}")
    String merchantUrl;

    @Autowired
    HandlerLoggingFilter handlerLoggingFilter;

/*    @Bean
    public RouterFunction<ServerResponse> signUpHandler(AuthenticationHandlers merchantHandlers) {
        return RouterFunctions.route(POST("/e-commerce/api/v1/signUp"), merchantHandlers::signUp);
    }*/

    @Bean
    public RouterFunction<ServerResponse> signInHandler(AuthenticationHandlers merchantHandlers) {
        return RouterFunctions.route(POST("/e-commerce/api/v1/signIn"), merchantHandlers::signIn).filter(handlerLoggingFilter::filter);
    }

    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(p -> p.path("/e-commerce/api/v1/signUp")
                        .filters(rw -> rw.rewritePath("/e-commerce/api/v1/signUp", "/merchants"))
                        .uri(merchantUrl))
                .build();
    }
}
