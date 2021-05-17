package ibar.task.ecommerce.demo.products;

import ibar.task.ecommerce.demo.exceptions.WebClientResponseException;
import ibar.task.ecommerce.demo.products.models.Product;
import ibar.task.ecommerce.demo.proxies.AuthenticatorProxy;
import ibar.task.ecommerce.demo.proxies.ProductsProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.BodyInserters.fromValue;

@Component
public class ProductHandlers {

    @Autowired
    AuthenticatorProxy authenticatorService;

    @Autowired
    ProductsProxy productsService;

    public ProductHandlers(AuthenticatorProxy authenticatorService, ProductsProxy productsService) {
        this.authenticatorService = authenticatorService;
        this.productsService = productsService;
    }

    Mono<ServerResponse> addProduct(ServerRequest request) {
        String token = request.headers().header("token").get(0);
        Mono<Product> productMono = request.bodyToMono(Product.class);
        return productMono.zipWhen(pr -> authenticatorService.checkToken(token), (pr, to) -> pr)
                .zipWhen(p -> productsService.addProduct(p), (p1, p2) -> p2)
                .zipWhen(p -> ServerResponse.ok().body(BodyInserters.fromValue(p)), (pr, res) -> res)
                .onErrorResume(error -> {
                    if (error instanceof WebClientResponseException) {
                        WebClientResponseException webClientError = (WebClientResponseException) error;
                        return ServerResponse.status(webClientError.getHttpStatus()).headers(httpHeaders -> {
                            httpHeaders.addAll(webClientError.getHeaders());
                        }).body(fromValue(webClientError.getClientErrorContent()));
                    } else {
                        return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                    }
                });
    }

    Mono<ServerResponse> getProducts(ServerRequest request) {
        String token = request.headers().header("token").get(0);
        MultiValueMap<String, String> queryParams = request.queryParams();
        return authenticatorService.checkToken(token)
                .zipWhen(t -> productsService.getProducts(queryParams), (t, p) -> p)
                .zipWhen(p -> ServerResponse.ok().header("token", token).contentType(MediaType.APPLICATION_JSON).body(fromValue(p)), (p, res) -> res)
                .onErrorResume(error -> {
                    if (error instanceof WebClientResponseException) {
                        WebClientResponseException webClientError = (WebClientResponseException) error;
                        return ServerResponse.status(webClientError.getHttpStatus()).headers(httpHeaders -> {
                            httpHeaders.addAll(webClientError.getHeaders());
                        }).body(fromValue(webClientError.getClientErrorContent()));
                    } else {
                        System.out.println(error.getMessage());
                        return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
                    }
                });
    }
}
