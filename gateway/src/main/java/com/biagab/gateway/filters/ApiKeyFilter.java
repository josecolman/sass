/*
package com.biagab.gateway.filters;

import com.biagab.gateway.models.ConfiguracionServicio;
import com.biagab.gateway.services.ConfiguracionServicioService;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.net.URI;

@RequiredArgsConstructor
@Component
public class ApiKeyFilter implements GatewayFilter, Ordered {

    private final ConfiguracionServicioService configuracionServicioService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        HttpHeaders headers = exchange.getRequest().getHeaders();
        String apiKey = headers.getFirst("X-API-Key");

        if (apiKey != null) {
            ConfiguracionServicio configuracion = configuracionServicioService.obtenerConfiguracionPorApiKey(apiKey);
            if (configuracion != null) {
                URI uri = UriComponentsBuilder.fromUriString(configuracion.getUrlServicio()).build().toUri();
                ServerHttpRequest request = exchange.getRequest().mutate().uri(uri).build();
                exchange.getAttributes().put(ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR, request.getURI());
                return chain.filter(exchange.mutate().request(request).build());
            }
        }

        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}

*/
