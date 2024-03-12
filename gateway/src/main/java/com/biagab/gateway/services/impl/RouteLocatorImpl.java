package com.biagab.gateway.services.impl;

import com.biagab.gateway.components.LoggingFilter;
import com.biagab.gateway.models.Route;
import com.biagab.gateway.services.RouteService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.BooleanSpec;
import org.springframework.cloud.gateway.route.builder.Buildable;
import org.springframework.cloud.gateway.route.builder.PredicateSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;

@Slf4j
@AllArgsConstructor
public class RouteLocatorImpl implements RouteLocator {

    private final RouteService routeService;
    private final RouteLocatorBuilder routeLocatorBuilder;
    private final LoggingFilter loggingFilter;

    @Override
    public Flux<org.springframework.cloud.gateway.route.Route> getRoutes() {

        RouteLocatorBuilder.Builder routesBuilder = routeLocatorBuilder.routes();
        return routeService.findRoutes()
                .map(data -> routesBuilder
                        .route(
                                data.getId(),
                                predicate -> setPredicateSpec(data, predicate)

                        )
                )
                .collectList()
                .flatMapMany(builders -> routesBuilder.build().getRoutes());
    }

    private Buildable<org.springframework.cloud.gateway.route.Route> setPredicateSpec(Route data, PredicateSpec predicateSpec) {

        BooleanSpec booleanSpec = predicateSpec.path(data.getPath());

        if (data.hasRewrite())
            booleanSpec.filters(filter -> filter.rewritePath(data.getRewrite().getPattern(), data.getRewrite().getReplacement()));

        if (StringUtils.hasLength(data.getMethod()))
            booleanSpec.and().method(data.getMethod());

        if (StringUtils.hasLength(data.getApiKey()))
            booleanSpec.and().header("X-API-KEY", data.getApiKey());
        else
            booleanSpec.filters(f -> f.setStatus(HttpStatus.UNAUTHORIZED));

        booleanSpec.filters(f -> f.filter(loggingFilter));

        return booleanSpec.uri(data.getUri());
    }
}
