package ibar.task.ecommerce.demo.proxies;

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

    public Mono<String> signIn(AuthenticationInfo authenticationInfo){
        System.out.println("in signIn");
        Mono<String> response = client.post()
                .uri(authenticatorUrl + "/getToken")
                .body(Mono.just(authenticationInfo), AuthenticationInfo.class)
                .exchangeToMono(resp -> {
                    System.out.println("resp.statusCode: " + resp.statusCode().toString());
                    switch (resp.statusCode()) {

                        case OK:
                            resp.headers().asHttpHeaders().forEach((h, v) -> {
                                System.out.println("header1: " + h + "; value: " + v);
                            });
                            return Mono.just(resp.headers().header("token").get(0));
                        default:
                            return Mono.error(new RuntimeException());
                    }
                });
        return response;
    }

    public Mono<String> checkToken(String token){

        Mono<String> response = client.post()
                .uri(authenticatorUrl + "/validateToken")
                .header("token", token)
                .exchangeToMono(resp -> {
                    switch (resp.statusCode()) {
                        case OK:
                            return Mono.just(resp.headers().header("token").get(0));
                        default:
                            return Mono.error(new RuntimeException());
                    }
                });
        return response;
    }
}
