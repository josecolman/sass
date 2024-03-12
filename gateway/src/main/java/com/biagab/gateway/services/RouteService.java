package com.biagab.gateway.services;

import com.biagab.gateway.models.payloads.RouteRequest;
import com.biagab.gateway.models.Route;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RouteService {

    Flux<Route> findRoutes();

    Mono<Route> findRoute(String id);

    Mono<Void> createRoute(RouteRequest data);

    Mono<Void> updateRoute(String id, RouteRequest data);

    Mono<Void> deleteRoute(String id);

}
