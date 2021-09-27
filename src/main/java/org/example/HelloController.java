package org.example;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    void callback(
            @RequestParam("access_token") String accessToken,
            @RequestParam("token_type") String tokenType
    ) {
        token.updateToken(tokenType + " " + accessToken);
    }
}
