package com.biagab.gateway.controllers;

import com.biagab.gateway.models.Route;
import com.biagab.gateway.models.payloads.RouteRequest;
import com.biagab.gateway.services.RouteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/routes")
public class RouteController {

    private final RouteService routeService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<List<Route>> findRoutes() {
        return routeService.findRoutes().collectList().subscribeOn(Schedulers.boundedElastic());
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<?> findRoute(@PathVariable String id) {
        return routeService.findRoute(id).subscribeOn(Schedulers.boundedElastic());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<?> createRoute(@RequestBody @Validated RouteRequest routeRequest) {
        return routeService.createRoute(routeRequest).subscribeOn(Schedulers.boundedElastic());
    }

    @PutMapping(path = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<?> updateRoute(@PathVariable String id, @RequestBody @Validated RouteRequest routeRequest) {
        return routeService.updateRoute(id, routeRequest).subscribeOn(Schedulers.boundedElastic());
    }

    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<?> deleteRoute(@PathVariable String id) {
        return routeService.deleteRoute(id).subscribeOn(Schedulers.boundedElastic());
    }

}
