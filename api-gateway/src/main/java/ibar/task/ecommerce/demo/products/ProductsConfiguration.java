package ibar.task.ecommerce.demo.products;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;

@Configuration
public class ProductsConfiguration {

    @Bean
    public RouterFunction<ServerResponse> addProduct(ProductHandlers productHandlers) {
        return RouterFunctions.route(POST("/e-commerce/api/v1/products"), productHandlers::addProduct);
    }

    @Bean
    public RouterFunction<ServerResponse> getProducts(ProductHandlers productHandlers) {
        return RouterFunctions.route(GET("/e-commerce/api/v1/products"), productHandlers::getProducts);
    }
}
