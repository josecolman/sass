/*package com.biagab.gateway.components;

import com.biagab.gateway.configs.GatewayConfig;
import com.biagab.gateway.models.ApiRoute;
import com.biagab.gateway.models.RouteConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.event.EventListener;
import org.springframework.data.mongodb.core.mapping.event.AfterSaveEvent;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class MongoDBEventListener {

    private final GatewayConfig gatewayConfig;
    private final RouteLocatorBuilder builder;
    private final LoggingFilter loggingFilter;

    @EventListener//(classes = AfterSaveEvent.class)
    public void handleAfterSave(AfterSaveEvent<Object> event) {
        // Verifica si el evento corresponde a la colección de configuración de rutas
        if (event.getSource() instanceof RouteConfig) {

            // Recarga la configuración dinámica del enrutador
            //gatewayConfig.customRouteLocator(builder, loggingFilter);
            log.info("RouteConfig saved, reloading route locator... {}", event);

        } else if (event.getSource() instanceof ApiRoute) {

            log.info("ApiRoute saved, reloading route locator... {}", event);
        }
    }
}*/

