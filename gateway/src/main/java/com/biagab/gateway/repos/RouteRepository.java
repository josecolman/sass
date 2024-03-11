package com.biagab.gateway.repos;

import com.biagab.gateway.models.Route;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface RouteRepository extends ReactiveMongoRepository<Route, String> {
    //Mono<ServiceRoute> findByPathAndApiKey(String path, String apiKey);
    //Flux<ServiceRoute> findByApiKey(String apiKey);
    //@Query("{'path': {$regex : ?0, $options: 'i'}, 'apiKey': ?1}")
    //@Query("{'path': {$regex : ^/api/routing/, $options: 'i'}, 'apiKey': ?1}")
    //@Query("{'path': {$regex : '^/api/routing/', $options: 'i'}, 'apiKey': ?1}")
    //Mono<ServiceRoute> findFirstByPathStartingWithAndApiKey(String path, String apiKey);
}
