package ibar.task.ecommerce.demo.merchants;

import ibar.task.ecommerce.demo.proxies.AuthenticatorProxy;
import ibar.task.ecommerce.demo.proxies.MerchantsProxy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;

@Configuration
public class MerchantsConfiguration {

    @Bean
    public RouterFunction<ServerResponse> signUpHandler(AuthenticationHandlers merchantHandlers) {
        return RouterFunctions.route(POST("/e-commerce/api/v1/signUp"), merchantHandlers::signUp);
    }

    @Bean
    public RouterFunction<ServerResponse> signInHandler(AuthenticationHandlers merchantHandlers) {
        return RouterFunctions.route(POST("/e-commerce/api/v1/signIn"), merchantHandlers::signIn);
    }
}
