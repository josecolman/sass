package com.biagab.gateway.configs;

import com.biagab.gateway.services.GatewayConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class GatewayConfigInitializer implements ApplicationListener<ApplicationReadyEvent> {

    private final GatewayConfigService gatewayConfigService;
    private final RouteLocatorBuilder builder;

    @Override
    public void onApplicationEvent(@NonNull ApplicationReadyEvent event) {
        //gatewayConfigService.configureRoutesFromDatabase(builder);
        log.info("Gateway routes configured from database {}", event);
    }
}

