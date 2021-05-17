package ibar.task.ecommerce.demo.products;

import ibar.task.ecommerce.demo.filters.HandlerLoggingFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;

@Configuration
public class ProductsConfiguration {

    @Autowired
    HandlerLoggingFilter handlerLoggingFilter;

    @Bean
    public RouterFunction<ServerResponse> addProduct(ProductHandlers productHandlers) {
        return RouterFunctions.route(POST("/e-commerce/api/v1/products"), productHandlers::addProduct).filter(handlerLoggingFilter::filter);
    }

    @Bean
    public RouterFunction<ServerResponse> getProducts(ProductHandlers productHandlers) {
        return RouterFunctions.route(GET("/e-commerce/api/v1/products"), productHandlers::getProducts).filter(handlerLoggingFilter::filter);
    }
}
