package com.biagab.gateway.components;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.GatewayFilterFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.function.Consumer;

@Getter
//@Component
@RequiredArgsConstructor
public class LoggingGatewayFilter implements GatewayFilterFactory<LoggingGatewayFilter.Config> {

    private final LoggingGatewayFilter.Config config;

    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            if (config.isEnabled()) {
                System.out.println("Original URL: " + exchange.getRequest().getURI());

                return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                    System.out.println("Redirected URL: " + exchange.getResponse().getStatusCode());
                }));
            } else {
                // Si el filtro está deshabilitado, continuar con la cadena
                return chain.filter(exchange);
            }
        };
    }

    public GatewayFilter applyRoute(String routeId, Consumer<Config> consumer) {
        // Implementación del segundo método applyRoute
        return apply(new Config());
    }

    @Override
    public Class<Config> getConfigClass() {
        return Config.class;
    }

    @Getter
    @Setter
    @ConfigurationProperties(prefix = "logging-filter")
    public static class Config {
        private boolean enabled;
    }
}


