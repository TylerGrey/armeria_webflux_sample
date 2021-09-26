package org.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

@Component
public class Scheduler {

    Logger logger = LoggerFactory.getLogger(Scheduler.class);
    private final Token token;

    public Scheduler(Token token) {
        this.token = token;
    }

    @Scheduled(fixedDelay = 2000)
    public void updateAccessToken() {
        WebClient.builder()
                .clientConnector(new ReactorClientHttpConnector(HttpClient.create().followRedirect(true)))
                .build()
                .get()
                .uri("http://127.0.0.1:3001/token")
                .headers(httpHeaders -> {
                    httpHeaders.add("client_id", "ASDFWJFASLDFJA");
                    httpHeaders.add("client_secret", "11232399403");
                })
                .retrieve()
                .bodyToMono(String.class)
                .onErrorResume(throwable -> {
                    logger.error("err : {}", throwable.getMessage());
                    return Mono.empty();
                })
                .retry(3)
                .subscribe(s -> {
                    if (!s.isEmpty()) {
                        token.updateToken(s);
                    }
                });
    }
}
