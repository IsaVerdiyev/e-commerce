package ibar.task.ecommerce.demo.proxies;

import ibar.task.ecommerce.demo.exceptions.WebClientResponseException;
import ibar.task.ecommerce.demo.models.AuthenticationInfo;
import ibar.task.ecommerce.demo.models.Merchant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class AuthenticatorProxy {
    @Value("${authenticator.url}")
    String authenticatorUrl;

    @Autowired
    private WebClient client;

    public Mono<Object> signIn(AuthenticationInfo authenticationInfo){
        Mono<Object> response = client.post()
                .uri(authenticatorUrl + "/getToken")
                .body(Mono.just(authenticationInfo), AuthenticationInfo.class)
                .exchangeToMono(resp -> {
                    System.out.println("resp.statusCode: " + resp.statusCode().toString());
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

    public Mono<Object> checkToken(String token){

        Mono<Object> response = client.post()
                .uri(authenticatorUrl + "/validateToken")
                .header("token", token)
                .exchangeToMono(resp -> {
                    switch (resp.statusCode()) {
                        case OK:
                            return Mono.just("");
                        default:
                            Mono<String> errorConent = resp.bodyToMono(String.class);
                            return errorConent.zipWhen(str -> Mono.error(new WebClientResponseException(resp.statusCode(), str, resp.headers().asHttpHeaders())), (str, e) -> e);
                    }
                });
        return response;
    }
}
