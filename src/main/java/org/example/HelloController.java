package org.example;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class HelloController {

    private final Token token;

    public HelloController(Token token) {
        this.token = token;
    }

    @GetMapping("/")
    String hello() {
        return token.getToken();
    }

    @GetMapping("/callback")
    Mono<String> callback(
            @RequestParam("access_token") String accessToken,
            @RequestParam("token_type") String tokenType
    ) {
        return Mono.just(tokenType + " " + accessToken);
    }
}
