package com.biagab.visitors.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class LogginConfig implements WebFilter {

    @NonNull
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {

        log.info("Request: {} {} {}",
                exchange.getRequest().getMethod(),
                exchange.getRequest().getPath(),
                exchange.getRequest().getHeaders()
        );

        return chain.filter(exchange)
                .doOnError((ex) -> {

                    log.error("Error during filter chain execution: {}", ex.getMessage());
                })
                .then(Mono.fromRunnable(() -> {

            log.info(
                    "Response: {} {}",
                    exchange.getResponse().getStatusCode(),
                    exchange.getResponse().getHeaders())
            ;
        }));
    }
}
