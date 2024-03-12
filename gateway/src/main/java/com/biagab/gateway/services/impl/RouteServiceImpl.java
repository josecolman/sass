package com.biagab.gateway.services.impl;

import com.biagab.gateway.configs.CacheConfig;
import com.biagab.gateway.models.Route;
import com.biagab.gateway.models.payloads.RouteRequest;
import com.biagab.gateway.models.payloads.RouteResponse;
import com.biagab.gateway.repos.RouteRepository;
import com.biagab.gateway.services.RouteRefreshService;
import com.biagab.gateway.services.RouteService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@Service
public class RouteServiceImpl implements RouteService {

    private final RouteRepository routeRepository;
    private final RouteRefreshService routeRefreshService;
    private final CacheManager cacheManager;

    @Override
    public Flux<Route> findRoutes() {
        return routeRepository.findAll();
    }

    @Cacheable(value = CacheConfig.ROUTES_CACHE, key = CacheConfig.ROUTES_KEY)
    @Override
    public Mono<Route> findRoute(String id) {
        log.info("Fetching route: {} from database...", id);
        return routeRepository.findById(id)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Route: "+id+" not found")));
    }

    @Override
    public Mono<Void> createRoute(RouteRequest data) {
        Route route = mapToRoute(new Route(), data);
        return routeRepository.save(route)
                .doOnSuccess(obj -> {
                    clearCache();
                    routeRefreshService.refreshRoutes();
                })
                .then();
    }

    private void clearCache() {
        Cache cache = cacheManager.getCache(CacheConfig.ROUTES_CACHE);
        if (cache != null)
            cache.clear();
    }

    @Override
    public Mono<Void> updateRoute(String id, RouteRequest data) {
        return routeRepository.findById(id)
                .map(apiRoute -> mapToRoute(apiRoute, data))
                .flatMap(routeRepository::save)
                .doOnSuccess(obj -> routeRefreshService.refreshRoutes())
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Route: "+id+" not found")))
                .then();
    }

    @CacheEvict(value = CacheConfig.ROUTES_CACHE, key = CacheConfig.ROUTES_KEY)
    @Override
    public Mono<Void> deleteRoute(String id) {
        return routeRepository.findById(id)
                .flatMap(apiRoute -> routeRepository.deleteById(apiRoute.getId()))
                .switchIfEmpty(Mono.empty())
                .doOnSuccess(obj -> routeRefreshService.refreshRoutes());
    }



    private Route mapToRoute(Route target, RouteRequest source) {
        target.setId(source.getId());
        target.setPath(source.getPath());
        target.setMethod(source.getMethod());
        target.setUri(source.getUri());
        target.setApiKey(source.getApiKey());
        if (source.getRewrite() != null) {
            target.setRewrite(new Route.RewritePath(
                    source.getRewrite().getPattern(),
                    source.getRewrite().getReplacement()
            ));
        }
        return target;
    }

    private RouteResponse mapToRouteResponse(Route route) {

        RouteResponse.RewritePath rewrite = null;

        if (route.getRewrite() != null) {
            rewrite = RouteResponse.RewritePath.builder()
                    .pattern(route.getRewrite().getPattern())
                    .replacement(route.getRewrite().getReplacement())
                    .build();
        }

        return RouteResponse.builder()
                .id(route.getId())
                .path(route.getPath())
                .method(route.getMethod())
                .uri(route.getUri())
                .rewrite(rewrite)
                .apiKey(route.getApiKey())
                .build();
    }
}
