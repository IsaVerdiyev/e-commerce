package ibar.task.ecommerce.demo.proxies;

import com.fasterxml.jackson.core.JsonProcessingException;
import ibar.task.ecommerce.demo.models.AuthenticationInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class AuthenticatorProxy {

    @Autowired
    private WebClient client;

    @Value("${authenticator.url}")
    private String authenticatorUrl;


    public AuthenticatorProxy(WebClient client) {
        this.client = client;
    }

    public String getToken(AuthenticationInfo authenticationInfo) throws JsonProcessingException {
        Mono<ResponseEntity<String>> responseMono = client
                .post()
                .uri(authenticatorUrl)
                .body(BodyInserters.fromValue(authenticationInfo)).retrieve().toEntity(String.class);
        ResponseEntity<String> response = responseMono.block();
        return response.getHeaders().get("token").get(0);
    }
}
