package com.biagab.gateway.services;

import com.biagab.gateway.models.Route;
import com.biagab.gateway.models.CreateOrUpdateApiRouteRequest;
import com.biagab.gateway.models.ServiceRoute;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ApiRouteService {

    Flux<Route> findApiRoutes();

    Mono<Route> findApiRoute(String id);

    Mono<Void> createApiRoute(CreateOrUpdateApiRouteRequest createOrUpdateApiRouteRequest);

    Mono<Void> updateApiRoute(String id, CreateOrUpdateApiRouteRequest createOrUpdateApiRouteRequest);

    Mono<Void> deleteApiRoute(String id);

}
