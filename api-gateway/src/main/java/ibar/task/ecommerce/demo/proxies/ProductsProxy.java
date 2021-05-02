package ibar.task.ecommerce.demo.proxies;

import ibar.task.ecommerce.demo.exceptions.WebClientResponseException;
import ibar.task.ecommerce.demo.products.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriBuilder;
import org.springframework.web.util.UriBuilderFactory;
import reactor.core.publisher.Mono;

import java.net.URI;

@Service
public class ProductsProxy {
    @Value("${products.url}")
    String productsUrl;

    @Autowired
    private WebClient client;

    public Mono<Object> addProduct(Product product) {
        Mono<Object> response = client.post()
                .uri(productsUrl)
                .body(Mono.just(product), Product.class)
                .exchangeToMono(resp -> {
                    switch (resp.statusCode()){
                        case OK:
                            return resp.bodyToMono(String.class);
                        default:
                            Mono<String> errorConent = resp.bodyToMono(String.class);
                            return errorConent.zipWhen(str -> Mono.error(new WebClientResponseException(resp.statusCode(), str, resp.headers().asHttpHeaders())), (str, e) -> e);
                    }
                });
                return response;
    }

    public Mono<Object> getProducts(MultiValueMap<String, String> queryParams) {
        System.out.println("in getProducts");
        Mono<Object> response = client.get()
                .uri(productsUrl, uriBuilder -> uriBuilder.queryParams(queryParams).build())
                .exchangeToMono(resp -> {
                    switch (resp.statusCode()){
                        case OK:
                            return resp.bodyToMono(String.class);
                        default:
                            Mono<String> errorConent = resp.bodyToMono(String.class);
                            return errorConent.zipWhen(str -> Mono.error(new WebClientResponseException(resp.statusCode(), str, resp.headers().asHttpHeaders())), (str, e) -> e);
                    }
                });
        return response;
    }
}
