package com.biagab.gateway.components;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
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

@RequiredArgsConstructor
@Component
public class LoggingFilter implements /*GatewayFilterFactory<LoggingFilter.Config>, */GatewayFilter {

    private final LoggingFilter.Config config;
/*
    @Override
    public GatewayFilter apply(LoggingFilter.Config config) {
        return (exchange, chain) -> {
            if (config.isEnabled()) {
                System.out.println("Original URL: " + exchange.getRequest().getURI());

                return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                    System.out.println("Redirected URL: " + exchange.getResponse().getStatusCode());
                }));
            } else {
                // If filter is disabled, just continue the chain
                return chain.filter(exchange);
            }
        };
    }

    @Override
    public Class<Config> getConfigClass() {
        return Config.class;
    }*/

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        if (config.isEnabled()) {

            System.out.println("Original URL: " + exchange.getRequest().getURI());

            // In your logging filter's `filter` method:
            //ServerWebExchangeUtils exchangeUtils = ServerWebExchangeUtils.class.cast(exchange.getAttribute(ServerWebExchangeUtils.class.getName()));
            //URI originalUri = exchangeUtils.getOriginalRequestUrl(exchange);
            //URI rewrittenUri = exchangeUtils.getCurrentRequestUrl(exchange);


            //logger.info("Original URI: {}", originalUri);
            //logger.info("Rewritten URI: {}", rewrittenUri);



            return chain.filter(exchange).doOnError((ex) -> {

                // Manejar el error aquí
                System.err.println("Error during filter chain execution: " + ex.getMessage());

                // Acceder a la URI de redirección en caso de error
                //String redirectedUri = exchange.getRequest().getURI().toString();
                //System.out.println("Redirected URI (on error): " + redirectedUri);

            }).then(Mono.fromRunnable(() -> {


                // Acceder a la URI de redirección en caso de éxito
                //String redirectedUri = exchange.getRequest().getURI().toString();
                //System.out.println("Redirected URI (on success): " + redirectedUri);

            }));

        } else {
            // If filter is disabled, just continue the chain
            return chain.filter(exchange);
        }
    }

    @Component
    @Setter
    @Getter
    @ConfigurationProperties(prefix = "logging-filter")
    public static class Config {
        private boolean enabled;

    }
}


