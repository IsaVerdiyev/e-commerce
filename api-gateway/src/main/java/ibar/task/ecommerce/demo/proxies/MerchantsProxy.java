package ibar.task.ecommerce.demo.proxies;

import ibar.task.ecommerce.demo.errors.ApiError;
import ibar.task.ecommerce.demo.exceptions.WebClientResponseException;
import ibar.task.ecommerce.demo.models.AuthenticationInfo;
import ibar.task.ecommerce.demo.models.Merchant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class MerchantsProxy {

    @Value("${merchants.url}")
    String merchantUrl;

    @Autowired
    private WebClient client;

    public Mono<Object> signUp(Merchant merchant) {
        Mono<Object> response = client.post()
                .uri(merchantUrl)
                .body(Mono.just(merchant), Merchant.class)
                .exchangeToMono(resp -> {
                    switch (resp.statusCode()) {
                        case OK:
                            return resp.bodyToMono(String.class);
                        default:
                            Mono<String> errorConent = resp.bodyToMono(String.class);
                            return errorConent.zipWhen(str -> Mono.error(new WebClientResponseException(resp.statusCode(), str, resp.headers().asHttpHeaders())), (str, e) -> e);
                    }
                });
        return response;
    }

    public Mono<Object> getMerchant(AuthenticationInfo authenticationInfo) {

        Mono<Object> response = client.post()
                .uri(merchantUrl + "/signIn")
                .body(Mono.just(authenticationInfo), AuthenticationInfo.class)
                .exchangeToMono(resp -> {
                    switch (resp.statusCode()) {
                        case OK:
                            return Mono.just(resp.headers().header("token").get(0));
                        default:
                            Mono<String> errorConent = resp.bodyToMono(String.class);
                            return errorConent.zipWhen(str -> Mono.error(new WebClientResponseException(resp.statusCode(), str, resp.headers().asHttpHeaders())), (str, e) -> e);
                    }
                });
        return response;
    }
}
