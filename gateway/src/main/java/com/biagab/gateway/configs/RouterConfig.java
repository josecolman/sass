package com.biagab.gateway.configs;

import com.biagab.gateway.services.RouteService;
import com.biagab.gateway.services.impl.RouteLocatorImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class RouterConfig {

    @Bean
    public RouteLocator routeLocator(RouteService routeService, RouteLocatorBuilder routeLocatorBuilder) {
        return new RouteLocatorImpl(routeService, routeLocatorBuilder);
    }
}



