package com.biagab.gateway.components;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.GatewayFilterFactory;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.net.URI;

@Slf4j
@RequiredArgsConstructor
@Component
public class LoggingFilter implements GatewayFilter {

    private final LoggingFilter.Config config;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        if (config.isEnabled()) {

            log.info("Original URL: {}", exchange.getRequest().getURI());

            return chain.filter(exchange).doOnError((ex) -> {

                log.error("Error during filter chain execution: {}", ex.getMessage(), ex);

            }).then(Mono.fromRunnable(() -> {

                log.info("Redirected URL status code: {}", exchange.getResponse().getStatusCode());

            }));

        } else {

            // If filter is disabled, just continue the chain
            return chain.filter(exchange);
        }
    }

    @Component
    @Setter
    @Getter
    @ConfigurationProperties(prefix = "logging.filter")
    public static class Config {
        private boolean enabled;
    }
}


