package com.biagab.gateway.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class GatewayConfigService {

   /* private final GatewayConfigRepository gatewayConfigRepository;

    public Flux<GatewayConfigModel> getAll() {
        return gatewayConfigRepository.findAll();
    }

    public Flux<GatewayConfigModel> getByApiKey(String apiKey) {
        return gatewayConfigRepository.findByApiKey(apiKey);
    }*/

    /*
    public void configureRoutesFromDatabase(RouteLocatorBuilder builder) {


        Flux<GatewayConfigModel> configs = gatewayConfigRepository.findAll();
        configs.subscribe(config -> {
            builder.routes()
                    .route(config.getRouteId(), r -> r
                            .predicates(predicateSpec -> config.getPredicates().forEach(predicateSpec::path))
                            .uri(config.getUri()));
        });

    }*/
}
