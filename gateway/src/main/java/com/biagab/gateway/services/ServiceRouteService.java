package com.biagab.gateway.services;


import com.biagab.gateway.models.RouteConfig;
import com.biagab.gateway.models.ServiceRoute;
import com.biagab.gateway.repos.RouteConfigRepository;
import com.biagab.gateway.repos.ServiceRouteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class ServiceRouteService {

    private final ServiceRouteRepository repository;

    public Flux<ServiceRoute> getAll() {
        return repository.findAll();
    }

    public Mono<ServiceRoute> getByPathAndApiKey(String path, String apiKey) {
        return repository.findByPathAndApiKey(path, apiKey);
    }
}
