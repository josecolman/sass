package com.biagab.gateway.services;

import com.biagab.gateway.models.Route;
import com.biagab.gateway.models.CreateOrUpdateApiRouteRequest;
import com.biagab.gateway.repos.RouteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class ApiRouteServiceImpl implements ApiRouteService {

    private final RouteRepository routeRepository;
    private final GatewayRouteService gatewayRouteService;

    @Override
    public Flux<Route> findApiRoutes() {
        return routeRepository.findAll();
    }

    @Override
    public Mono<Route> findApiRoute(String id) {
        return findAndValidateApiRoute(id);
    }

    @Override
    public Mono<Void> createApiRoute(CreateOrUpdateApiRouteRequest createOrUpdateApiRouteRequest) {
        Route route = setNewApiRoute(new Route(), createOrUpdateApiRouteRequest);
        return routeRepository.save(route)
                .doOnSuccess(obj -> gatewayRouteService.refreshRoutes())
                .then();
    }

    @Override
    public Mono<Void> updateApiRoute(String id,
                                     CreateOrUpdateApiRouteRequest createOrUpdateApiRouteRequest) {
        return findAndValidateApiRoute(id)
                .map(apiRoute -> setNewApiRoute(apiRoute, createOrUpdateApiRouteRequest))
                .flatMap(routeRepository::save)
                .doOnSuccess(obj -> gatewayRouteService.refreshRoutes())
                .then();
    }

    @Override
    public Mono<Void> deleteApiRoute(String id) {
        return findAndValidateApiRoute(id)
                .flatMap(apiRoute -> routeRepository.deleteById(apiRoute.getId()))
                .doOnSuccess(obj -> gatewayRouteService.refreshRoutes());
    }

    private Mono<Route> findAndValidateApiRoute(String id) {
        return routeRepository.findById(id)
                .switchIfEmpty(
                        Mono.error(
                                new RuntimeException("Route with id: '" + id + "' not found.")
                        )
                );
    }

    private Route setNewApiRoute(Route route,
                                 CreateOrUpdateApiRouteRequest createOrUpdateApiRouteRequest) {
        route.setId(createOrUpdateApiRouteRequest.getId());
        route.setPath(createOrUpdateApiRouteRequest.getPath());
        route.setMethod(createOrUpdateApiRouteRequest.getMethod());
        route.setUri(createOrUpdateApiRouteRequest.getUri());
        route.setApiKey(createOrUpdateApiRouteRequest.getApiKey());
        if (createOrUpdateApiRouteRequest.getRewrite() != null) {
            route.setRewrite(new Route.RewritePath(
                    createOrUpdateApiRouteRequest.getRewrite().getPattern(),
                    createOrUpdateApiRouteRequest.getRewrite().getReplacement()
            ));
        }
        return route;
    }
}
