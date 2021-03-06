package ibar.task.ecommerce.demo.merchants;

import ibar.task.ecommerce.demo.exceptions.WebClientResponseException;
import ibar.task.ecommerce.demo.proxies.AuthenticatorProxy;
import ibar.task.ecommerce.demo.proxies.MerchantsProxy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;
import ibar.task.ecommerce.demo.merchants.models.*;

import static org.springframework.web.reactive.function.BodyInserters.fromPublisher;
import static org.springframework.web.reactive.function.BodyInserters.fromValue;

@Component
public class AuthenticationHandlers {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    MerchantsProxy merchantsService;

    @Autowired
    AuthenticatorProxy authenticatorService;

    public AuthenticationHandlers(MerchantsProxy merchantsService, AuthenticatorProxy authenticatorService) {
        this.merchantsService = merchantsService;
        this.authenticatorService = authenticatorService;
    }

    public Mono<ServerResponse> signUp(ServerRequest serverRequest) {
        Mono<Merchant> merchantMono = serverRequest.bodyToMono(Merchant.class);
        return merchantMono.flatMap(m -> merchantsService.signUp(m)).flatMap(m -> ServerResponse.ok().body(fromValue("")))
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

    public Mono<ServerResponse> signIn(ServerRequest serverRequest) {
        Mono<AuthenticationInfo> authenticationInfoMono = serverRequest.bodyToMono(AuthenticationInfo.class);
        return authenticationInfoMono.zipWhen(m -> merchantsService.getMerchant(m), (authMono, merchantMono) -> authMono)
                .zipWhen(a -> authenticatorService.signIn(a), (authMono, headerMono) -> headerMono)
                .zipWhen(h -> ServerResponse.ok().header("token", (String)h).body(fromValue("")), (headerMono, responseMono) -> responseMono)
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
}
