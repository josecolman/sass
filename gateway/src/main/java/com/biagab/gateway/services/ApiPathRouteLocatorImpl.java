package com.biagab.gateway.services;

import com.biagab.gateway.models.Route;
import lombok.AllArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.BooleanSpec;
import org.springframework.cloud.gateway.route.builder.Buildable;
import org.springframework.cloud.gateway.route.builder.PredicateSpec;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;

@AllArgsConstructor
public class ApiPathRouteLocatorImpl implements RouteLocator {

    private final ApiRouteService apiRouteService;
    private final RouteLocatorBuilder routeLocatorBuilder;

    @Override
    public Flux<org.springframework.cloud.gateway.route.Route> getRoutes() {
        RouteLocatorBuilder.Builder routesBuilder = routeLocatorBuilder.routes();
        return apiRouteService.findApiRoutes()
                .map(data -> routesBuilder.route(data.getId(), predicate -> setPredicateSpec(data, predicate)))
                .collectList()
                .flatMapMany(builders -> routesBuilder.build().getRoutes());
    }

    private Buildable<org.springframework.cloud.gateway.route.Route> setPredicateSpec(Route data, PredicateSpec predicateSpec) {

        BooleanSpec booleanSpec = predicateSpec.path(data.getPath());

        if (data.hasRewrite())
            booleanSpec.filters(filter -> filter.rewritePath(data.getRewrite().getPattern(), data.getRewrite().getReplacement()));

        if (StringUtils.hasLength(data.getMethod()))
            booleanSpec.and().method(data.getMethod());

        return booleanSpec.uri(data.getUri());
    }
}
