package com.biagab.gateway.services;


import com.biagab.gateway.models.RouteConfig;
import com.biagab.gateway.repos.RouteConfigRepository;
import com.biagab.gateway.repos.ServiceRouteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class RouteService {

    private final RouteConfigRepository repository;

    public Flux<RouteConfig> getAllRoutes() {
        return repository.findAll();
    }

    public Mono<RouteConfig> getRouteByPathAndApiKey(String path, String apiKey) {
        return repository.findByPathAndApiKey(path, apiKey);
    }
}
